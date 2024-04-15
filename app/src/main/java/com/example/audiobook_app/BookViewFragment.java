package com.example.audiobook_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.audiobook_app.databinding.FragmentBookViewBinding;
//Roko Kaulecko
public class BookViewFragment extends Fragment {

    private FragmentBookViewBinding binding;

    private OnBackPressedCallback callback;

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