package com.example.audiobook_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.util.Pair;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.audiobook_app.Activity.MainActivity;
import com.example.audiobook_app.Domain.Book;
import com.example.audiobook_app.Domain.BookWithChapters;
import com.example.audiobook_app.Domain.Chapter;
import com.example.audiobook_app.Domain.FavoriteChapter;
import com.example.audiobook_app.Domain.ReadingProgress;
import com.example.audiobook_app.Domain.ReadingProgressWithChapters;
import com.example.audiobook_app.Domain.TimeFormatter;
import com.example.audiobook_app.databinding.FragmentAudioplayerBinding;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Roko Kaulecko
public class AudioplayerFragment extends Fragment {

    private FragmentAudioplayerBinding binding;
    private MediaPlayer mediaPlayer; // Declare MediaPlayer object
    //private int[] audioFiles = { R.raw.music, R.raw.music2, R.raw.music3}; // Example audio files
    private int currentTrack = 1; // Index of the current audio track
    private boolean isPlaying = false; // Flag to track audio playback state
    private SeekBar seekBar; // SeekBar for audio progress
    private Runnable updateSeekBar; // Runnable for updating SeekBar progress
    private TextView lastListenedFileTextView;
    private TextView lastListenedTimestampTextView;
    private TextView currentTimeTextView;
    private TextView remainingTimeTextView;
    private static final String _preferencesName = "MyFavourites";
    private boolean isFavourite = false;
   // private HashMap<Integer, String> rawFileNames = new HashMap<>();

    private String currentFileName;
    private int currentResourceId;

    // Initialize the mapping in your constructor or initialization method

//    private void initializeRawFileNames() {
//        rawFileNames.put(R.raw.music, "Chapter 1");
//        rawFileNames.put(R.raw.music2, "Chapter 2");
//        rawFileNames.put(R.raw.music3, "Chapter 3");
//        // Add more mappings as needed
//    }

    List<Chapter> chapters;
    FavoriteChapter favChapter;
    MainActivity mainActivity;

    Book book;
    ReadingProgress readingProgress;

    Uri directoryUri;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        binding = FragmentAudioplayerBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int chapterIndex = bundle.getInt("index");
            long bookID  = bundle.getLong("bookId");

            BookWithChapters bookWithChapters = mainActivity.getDb().bookDAO().getBookWithChapters((int) bookID);

            book = bookWithChapters.user;
            chapters = bookWithChapters.chapters;
            currentTrack = chapterIndex;

            ReadingProgressWithChapters historyWithChapters = mainActivity.getDb().readingProgressDAO().getReadingProgress((int) bookID);
            readingProgress = historyWithChapters.readingProgress;
            readingProgress.chapterProgresses = historyWithChapters.chapters;
        }

        directoryUri = mainActivity.GetDirectoryUri();

        //region histrory and favorites
        View view = binding.getRoot();
        Button btnFavourite = view.findViewById(R.id.buttonFavourite);
        lastListenedFileTextView = view.findViewById(R.id.lastListenedFile);
        lastListenedTimestampTextView = view.findViewById(R.id.lastListenedTimestamp);
//        Pair<String, Long> lastListened = getLastListened(getContext());
//        lastListenedFileTextView.setText(lastListened.first);
//        lastListenedTimestampTextView.setText(String.valueOf(lastListened.second));
        currentTimeTextView = view.findViewById(R.id.currentTime);
        remainingTimeTextView = view.findViewById(R.id.remainingTime);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(_preferencesName,Context.MODE_PRIVATE);
        btnFavourite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isFavourite =!isFavourite;
                String currentFileName = chapters.get(currentTrack).getTitle();
                if (isFavourite) {

                    addToFavourites(currentFileName + " " + mediaPlayer.getCurrentPosition());
                    Toast toast = new Toast(getContext());
                    toast.setText("Added" + currentFileName);
                    toast.show();
                }
                else{
                removeFromFavourites(currentFileName);
                Toast toast = new Toast(getContext());
                toast.setText("removed" + currentFileName);
                toast.show();
                }
            }
        });

//endregion

        return view;
    }

    private Uri GetAudioUri(Uri directoryUri){
        return DocumentsContract.buildDocumentUriUsingTree(directoryUri,
                DocumentsContract.getTreeDocumentId(directoryUri) + "/" + chapters.get(currentTrack).getAudioAddress());
    }

    private void PreparePlayer()
    {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mainActivity, GetAudioUri(directoryUri));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        PreparePlayer();

        // Initialize SeekBar
        seekBar = binding.seekBar;
        // Set SeekBar max value to the duration of the audio track
        seekBar.setMax(mediaPlayer.getDuration());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition(); // In milliseconds
                    int mRemainingPosition = mediaPlayer.getDuration(); // In milliseconds

                    // Convert the positions from milliseconds to minutes and seconds
                    String currentTime = TimeFormatter.formatTime(mCurrentPosition);
                    String remainingTime = TimeFormatter.formatTime(mRemainingPosition);

                    // Update the TextViews
                    currentTimeTextView.setText(currentTime);
                    remainingTimeTextView.setText(remainingTime);

                    // Update the SeekBar
                    //seekBar.setProgress(mCurrentPosition / 1000);
                }
                handler.postDelayed(this, 1000);
            }
        }, 0);

        //region Buttons

        // Set click listener for stop button
        binding.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    mediaPlayer.pause(); // Pause the audio playback
                    binding.stop.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    mediaPlayer.start(); // Resume the audio playback
                    binding.stop.setImageResource(android.R.drawable.ic_media_pause);
                }
                isPlaying = !isPlaying; // Toggle the playback state
            }
        });

        // Set click listener for skip button
        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if there are more audio tracks available
                if (currentTrack < chapters.size() - 1) {
                    // Save the current audio file and its timestamp before moving to the next track
                    //saveLastListened(getContext(), getResources().getResourceEntryName(getAudioFileId(currentTrack)), mediaPlayer.getCurrentPosition());

                    Pair<String, Long> lastListened = getLastListened(getContext());
                    lastListenedFileTextView.setText("Last Listened File: " + lastListened.first);
                    lastListenedTimestampTextView.setText("Last Stopped At: " + TimeFormatter.formatTime(lastListened.second));

                    currentTrack++; // Move to the next track
                    playAudio(); // Play the new audio track
                }
            }
        });

        // Set click listener for back button
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if it's possible to go back to the previous audio track
                if (currentTrack > 0) {
                    //saveLastListened(getContext(), getResources().getResourceEntryName(getAudioFileId(currentTrack)), mediaPlayer.getCurrentPosition());
                    Pair<String, Long> lastListened = getLastListened(getContext());
                    lastListenedFileTextView.setText("Last Listened File: " + lastListened.first);

                    lastListenedTimestampTextView.setText("Last Stopped At: " + TimeFormatter.formatTime(lastListened.second));

                    currentTrack--; // Move to the previous track
                    playAudio(); // Play the new audio track
                }
            }
        });
        //endregion

        //region SeekBar

        // Update SeekBar progress while audio is playing
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setProgress(readingProgress.chapterProgresses.get(currentTrack).lastReadTimestamp);
                seekBar.setMax(mp.getDuration());
                mp.start();
            }
        });

        // Update SeekBar progress during audio playback
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (currentTrack < chapters.size() - 1) {
                    currentTrack++;
                    playAudio(); // Play the next audio track
                }
            }
        });

        // Set SeekBar change listener to update audio playback position
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}


        });


        // Initialize Runnable for updating SeekBar progress
        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                }
                // Update SeekBar progress every second
                seekBar.postDelayed(this, 1000);
            }
        };
        //endregion

        // Start audio playback
        playAudio();

        //TODO change later
        //setFavoriteChapterTime();
        if(favChapter != null)
        {
           // mediaPlayer.seekTo(favChapter.getTimestamp());
        }

    }


    private void saveAudioProgress() {
        int chapterProgressesID = readingProgress.chapterProgresses.get(currentTrack).id;
        int newTimestamp = mediaPlayer.getCurrentPosition();
        mainActivity.getDb().chapterProgressDAO().update(chapterProgressesID, newTimestamp);
    }

    private void playAudio() {
        saveAudioProgress();
        mediaPlayer.stop();
        mediaPlayer.release();

        PreparePlayer();

        mediaPlayer.start();

        // Update SeekBar progress
        getActivity().runOnUiThread(updateSeekBar);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            saveAudioProgress();
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            saveAudioProgress();
            mediaPlayer.release(); // Release the MediaPlayer resources
        }
        // Remove the callbacks to stop updating the SeekBar progress
        seekBar.removeCallbacks(updateSeekBar);
        binding = null;
    }

    //region History
    /**
     * Save the last listened audio file and its timestamp
     * @param context Application context
     * @param fileName Name of the audio file
     * @param timestamp Timestamp of the last listened audio file
     */
    public void saveLastListened(Context context, String fileName, long timestamp) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LastListened", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FileName", fileName);
        editor.putLong("Timestamp", timestamp);
        editor.apply();
    }

    /**
     * Get the last listened audio file and its timestamp
     * @param context Application context
     * @return Pair containing the name of the audio file and its timestamp
     */
    public Pair<String, Long> getLastListened(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LastListened", Context.MODE_PRIVATE);
        String fileName = sharedPreferences.getString("FileName", "");
        long timestamp = sharedPreferences.getLong("Timestamp", 0);
        return new Pair<>(fileName, timestamp);
    }
    //endregion

    //region Favorites
    private void addToFavourites(String bookTitle){

        //TODO irasyti Timestampa, dabartini chapter, chpateriai booko list, currentTrack i FavoritesChapter
        //TODO persiusti FavoritesChapter -> MyFavourites
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyFavourites", Context.MODE_PRIVATE);
        Set<String> favouriteSet = sharedPreferences.getStringSet("favouriteBooks", new HashSet<>());

        //FavoritesChapter favoritesChapter = new FavoritesChapter(mediaPlayer.getCurrentPosition(), chapters.get(currentTrack), chapters, currentTrack);


        //TODO saveLastListened(getContext(), getResources().getResourceEntryName(getAudioFileId(currentTrack)), mediaPlayer.getCurrentPosition());
        //TODO mediaPlayer.seekTo();

        favouriteSet.add(bookTitle);
        sharedPreferences.edit().putStringSet("favouriteBooks", favouriteSet).apply();
    }

    private void removeFromFavourites(String bookTitle){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyFavourites", Context.MODE_PRIVATE);
        Set<String> favouriteSet = sharedPreferences.getStringSet("favouriteBooks", new HashSet<>());
        favouriteSet.remove(bookTitle);
        sharedPreferences.edit().putStringSet("favouriteBooks", favouriteSet).apply();
    }

    //TODO paleist kai kviecia metoda
    private Set<String> getFavouriteBooks() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyFavourites", Context.MODE_PRIVATE);

        return sharedPreferences.getStringSet("favouriteBooks", new HashSet<>());
    }

    public void setFavoriteChapter() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyFavourites", Context.MODE_PRIVATE);


       // mediaPlayer.seekTo(favChapter.getTimestamp());
    }
//endregion

}
