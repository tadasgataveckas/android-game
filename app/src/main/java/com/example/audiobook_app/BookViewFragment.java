package com.example.audiobook_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.audiobook_app.databinding.FragmentBookViewBinding;
//Roko Kaulecko
public class BookViewFragment extends Fragment {

    private FragmentBookViewBinding binding;

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
            //int drawableResourceId = getResources().getIdentifier(picAddress, "drawable", getContext().getPackageName());
            //Glide.with(getContext()).load(drawableResourceId).into(binding.bookCover);
        }

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(BookViewFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}