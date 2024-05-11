package com.example.audiobook_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class ProfileFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Set<String> favouriteSet;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        listView = view.findViewById(R.id.favouriteBooks);
        favouriteSet = getFavoritesFromSharedPreferences();
        ArrayList<String> favouriteList = new ArrayList<>(favouriteSet);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,favouriteList);
        listView.setAdapter(adapter);
        //TODO sukurti chapter adapter ir i ji ideti onClick() FavoritesChapter (ChapterAdapter ima tik chapterius tai galima pakeist tik onclick())
        //TODO sukurti FavoritesChapterAdapter ir i ji ideti onClick() FavoritesChapter
        //TODO onclick to audioPlayerFragment
        return view;
    }

    private Set<String> getFavoritesFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyFavourites", Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet("favouriteBooks", new HashSet<>());
    }
}