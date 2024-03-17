package com.example.audiobook_app;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.audiobook_app.databinding.FragmentAudioplayerBinding;
//Roko Kaulecko
public class AudioplayerFragment extends Fragment {

    private FragmentAudioplayerBinding binding;
    private MediaPlayer mediaPlayer; // Declare MediaPlayer object
    private int[] audioFiles = { /*R.raw.music, R.raw.music2, R.raw.music3*/}; // Example audio files
    private int currentTrack = 0; // Index of the current audio track
    private boolean isPlaying = false; // Flag to track audio playback state
    private SeekBar seekBar; // SeekBar for audio progress
    private Runnable updateSeekBar; // Runnable for updating SeekBar progress

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAudioplayerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize MediaPlayer with the first audio track
        mediaPlayer = MediaPlayer.create(requireContext(), audioFiles[currentTrack]);

        // Initialize SeekBar
        seekBar = binding.seekBar;

        // Set SeekBar max value to the duration of the audio track
        seekBar.setMax(mediaPlayer.getDuration());

        // Set click listener for stop button
        binding.stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    mediaPlayer.pause(); // Pause the audio playback
                } else {
                    mediaPlayer.start(); // Resume the audio playback
                }
                isPlaying = !isPlaying; // Toggle the playback state
            }
        });

        // Set click listener for skip button
        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if there are more audio tracks available
                if (currentTrack < audioFiles.length - 1) {
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
                    currentTrack--; // Move to the previous track
                    playAudio(); // Play the new audio track
                }
            }
        });

        // Update SeekBar progress while audio is playing
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setProgress(0);
                seekBar.setMax(mp.getDuration());
                mp.start();
            }
        });

        // Update SeekBar progress during audio playback
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (currentTrack < audioFiles.length - 1) {
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

        // Start audio playback
        playAudio();
    }

    private void playAudio() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(requireContext(), audioFiles[currentTrack]);
        mediaPlayer.start();

        // Update SeekBar progress
        getActivity().runOnUiThread(updateSeekBar);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Release the MediaPlayer resources
        }
        // Remove the callbacks to stop updating the SeekBar progress
        seekBar.removeCallbacks(updateSeekBar);
        binding = null;
    }
}
