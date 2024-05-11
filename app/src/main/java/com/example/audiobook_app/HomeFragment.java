package com.example.audiobook_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.audiobook_app.Activity.AppActivity;
import com.example.audiobook_app.Activity.MainActivity;
import com.example.audiobook_app.Adapter.BooksAdapter;
import com.example.audiobook_app.Adapter.ClickListener;
import com.example.audiobook_app.Domain.AppDatabase;
import com.example.audiobook_app.Domain.Book;
import com.example.audiobook_app.Domain.BookGenerator;
import com.example.audiobook_app.Domain.BookWithChapters;
import com.example.audiobook_app.Domain.Chapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private RecyclerView.Adapter adapterBookList;
    private RecyclerView recyclerViewBooks;
    private RecyclerView recyclerViewBooksVertical;

    MainActivity mainActivity;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView
        recyclerViewBooks = view.findViewById(R.id.view1);
        recyclerViewBooksVertical = view.findViewById(R.id.view2);
        initRecyclerView();
        initRecyclerViewVertical();

        return view;
    }


    private void initRecyclerView() {
        ArrayList<Book> items = new ArrayList<>();
//        items.add(new Book("Soul", "Olivia Wilson", "@drawable/b1"));
//        items.add(new Book("Harry Potter", "J.K. Rowling", "@drawable/b2"));
//        items.add(new Book("A Million To One", "Tony Faggioli", "@drawable/b3"));
//        items.add(new Book("Educated", "Tara Westover", "@drawable/b4"));

        items.addAll(mainActivity.books);


//        recyclerViewBooks =findViewById(R.id.view1);

        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterBookList = new BooksAdapter((ArrayList<Book>) mainActivity.books);
        HandleBookClick(adapterBookList);


        recyclerViewBooks.setAdapter(adapterBookList);
    }

    private void initRecyclerViewVertical() {
        ArrayList<Book> items = new ArrayList<>();
        items.add(new Book("Soul", "Olivia Wilson", "","@drawable/b1"));
        items.add(new Book("Harry Potter", "J.K. Rowling", "","@drawable/b2"));
        items.add(new Book("A Million To One", "Tony Faggioli", "","@drawable/b3"));
        items.add(new Book("Educated", "Tara Westover", "","@drawable/b4"));

        items.addAll(mainActivity.books);

//        recyclerViewBooks =findViewById(R.id.view1);
        recyclerViewBooksVertical.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapterBookList = new BooksAdapter(items);
        HandleBookClick(adapterBookList);


        recyclerViewBooksVertical.setAdapter(adapterBookList);
    }

    private void HandleBookClick(RecyclerView.Adapter adapterBookList) {
        ((BooksAdapter) adapterBookList).setOnItemClickListener(new ClickListener<Book>(){
            @Override
            public void onItemClick(Book data) {

                // Create a new instance of the fragment
                BookViewFragment fragment = new BookViewFragment();

                // Create a bundle to pass the book data
                Bundle bundle = new Bundle();
                bundle.putString("title", data.getTitle());
                bundle.putString("author", data.getAuthor());
                bundle.putString("picAddress", data.getPicAddress());
                //bundle.putParcelableArrayList("chapters", data.getChapters());




                long index = mainActivity.findBookInDb(data);
                if(index != -1)
                {
                    bundle.putLong("id", index);
                    bundle.putBoolean("ifBookFoundInDb", true);
                }
                else
                {
                    index = ((BooksAdapter) adapterBookList).getBookIndex(data);
                    bundle.putLong("id", index);
                    bundle.putBoolean("ifBookFoundInDb", false);
                }
                fragment.setArguments(bundle);
                //send id instead of the whole book object

                // Get MainActivity and hide frameLayoutNavigation and bottomNavigationView
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.findViewById(R.id.frameLayoutNavigation).setVisibility(View.GONE);
                    mainActivity.findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
                    mainActivity.findViewById(R.id.bookViewFragmentContainer).setVisibility(View.VISIBLE);
                }

                // Replace bookViewFragmentContainer with the new fragment
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.bookViewFragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}