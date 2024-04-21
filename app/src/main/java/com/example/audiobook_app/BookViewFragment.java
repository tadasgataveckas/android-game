package com.example.audiobook_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.audiobook_app.Activity.MainActivity;
import com.example.audiobook_app.Adapter.ChapterAdapter;
import com.example.audiobook_app.Adapter.ClickListener;
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

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment using view binding
        binding = FragmentBookViewBinding.inflate(inflater, container, false);

        // Get the book data from the arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("title");
            String author = bundle.getString("author");
            String picAddress = bundle.getString("picAddress");

            // Set the book data to the views
            binding.pavadinimas.setText(title);
            binding.autorius.setText(author);
            int drawableResourceId = getResources().getIdentifier(picAddress, "drawable", getContext().getPackageName());
            Glide.with(getContext()).load(drawableResourceId).into(binding.bookCover);

            // Set the chapter list
            recyclerView = binding.chapterList;
            initChapterList();

            
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

    private void initChapterList() {

        chapterAdapter = new ChapterAdapter(getChapters());
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

    //Creates a mock-up list of chapters
    //TODO: Replace with actual chapter list
    private List<Chapter> getChapters() {
        List<Chapter> chapters = new ArrayList<>();
        Chapter chapterTest = new Chapter("1", "Test", "");
        for (int i = 0; i < 10; i++) {
            chapters.add(chapterTest);
        }
        return chapters;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioplayerFragment fragment = new AudioplayerFragment();

                // Replace the current fragment with the new one
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.bookViewFragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        callback.remove();
        binding = null;
    }

}