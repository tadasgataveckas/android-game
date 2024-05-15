package com.example.audiobook_app;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.audiobook_app.Activity.AppActivity;
import com.example.audiobook_app.Activity.MainActivity;
import com.example.audiobook_app.Adapter.ChapterAdapter;
import com.example.audiobook_app.Adapter.ClickListener;
import com.example.audiobook_app.Domain.AppDatabase;
import com.example.audiobook_app.Domain.Book;
import com.example.audiobook_app.Domain.BookWithChapters;
import com.example.audiobook_app.Domain.Chapter;
import com.example.audiobook_app.databinding.FragmentBookViewBinding;

import java.util.ArrayList;
import java.util.List;

//Roko Kaulecko
public class BookViewFragment extends Fragment {

    private FragmentBookViewBinding binding;

    private OnBackPressedCallback callback;
    private RecyclerView recyclerView;
    private ChapterAdapter chapterAdapter;
    List<Chapter> chapters;

    Book book;

    MainActivity mainActivity;
    boolean ifBookFoundInDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
    }



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment using view binding
        binding = FragmentBookViewBinding.inflate(inflater, container, false);
        // Get the book data from the arguments
        Bundle bundle = getArguments();
        long bookID = 0;
        if (bundle != null)
        {
            bookID = bundle.getLong("id");
            ifBookFoundInDb = bundle.getBoolean("ifBookFoundInDb");
            getBookData(bookID, ifBookFoundInDb);
        }


        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.findViewById(R.id.frameLayoutNavigation).setVisibility(View.VISIBLE);
                    mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.VISIBLE);
                    mainActivity.findViewById(R.id.bookViewFragmentContainer).setVisibility(View.GONE);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return binding.getRoot();
    }

    /**
     * Get the book data from the database or the main activity
     * @param bookID The ID of the book
     * @param ifBookFoundInDb If the book is found in the database
     */
    private void getBookData(long bookID, boolean ifBookFoundInDb) {
        if(bookID == -1)
        {
            return;
        }
        if(ifBookFoundInDb) {
            BookWithChapters bookWithChapters = mainActivity.getDb().bookDAO().getBookWithChapters((int) bookID);

            book = bookWithChapters.user;
            chapters = bookWithChapters.chapters;
        }
        else{
            book = mainActivity.books.get((int)bookID);
            chapters = mainActivity.books.get((int)bookID).getChapters();
        }

        // Set the chapter list
        recyclerView = binding.chapterList;
        initChapterList(chapters);
        // Set the book data to the views
        binding.pavadinimas.setText(book.getTitle());
        binding.autorius.setText(book.getAuthor());
        int drawableResourceId = getResources().getIdentifier(book.getPicAddress(), "drawable", getContext().getPackageName());
        Glide.with(getContext()).load(drawableResourceId).into(binding.bookCover);
    }

    private void initChapterList(List<Chapter> chapters) {

        chapterAdapter = new ChapterAdapter(chapters);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ((ChapterAdapter) chapterAdapter).setOnItemClickListener(new ClickListener<Chapter>(){
            @Override
            public void onItemClick(Chapter data) {

                // Create a new instance of the fragment
                AudioplayerFragment fragment = new AudioplayerFragment();

                // Create a bundle to pass the book data
                Bundle bundle = new Bundle();
                bundle.putString("title", data.getTitle());
                bundle.putString("number", data.getNumber());
                bundle.putString("audioAddress", data.getAudioAddress());
               // bundle.putParcelableArrayList("chapters", (ArrayList) chapters);
                fragment.setArguments(bundle);



                // Replace the current fragment with the new one
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.bookViewFragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerView.setAdapter(chapterAdapter);
    }

//    //Creates a mock-up list of chapters
//
//    private List<Chapter> getChapters() {
//        List<Chapter> chapters = new ArrayList<>();
//        Chapter chapterTest = new Chapter("1", "Test", "");
//        for (int i = 0; i < 10; i++) {
//            chapters.add(chapterTest);
//        }
//        return chapters;
//    }
private long downloadId;
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(!ifBookFoundInDb) {
            binding.buttonFirst.setText("Download");
        }


        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioplayerFragment fragment = new AudioplayerFragment();


                if(!ifBookFoundInDb) {
                    mainActivity.BookToDatabase(book); //stores in DB and downloads the chapters

                    ifBookFoundInDb = true;
                    binding.buttonFirst.setText("Play");
                }
                else{

                    //TODO: Get the last read chapter
                    Chapter data = chapters.get(0);

                    // Create a bundle to pass the book data
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", data.getChapterId());
                    bundle.putLong("bookId", data.getBookId());

//                bundle.putString("title", data.getTitle());
//                bundle.putString("number", data.getNumber());
//                bundle.putString("audioAddress", data.getAudioAddress());
//                bundle.putParcelableArrayList("chapters", (ArrayList) chapters);
                    fragment.setArguments(bundle);

                    // Replace the current fragment with the new one
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.bookViewFragmentContainer, fragment)
                            .addToBackStack(null)
                            .commit();

                }


                            }
        });
    }


//TODO: Implementuoti reakcija i siuntimo progresa ir pabaiga

//    @Override
//    public void onResume() {
//        super.onResume();
//        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//        requireActivity().registerReceiver(onDownloadComplete, filter);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        requireActivity().unregisterReceiver(onDownloadComplete);
//    }
//
//    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//            if (downloadId == id) {
//                Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();
//                // Resume BookViewFragment actions here
//            }
//        }
//    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        callback.remove();
        binding = null;
    }

}