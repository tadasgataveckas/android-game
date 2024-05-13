package com.example.audiobook_app.Activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.media3.database.StandaloneDatabaseProvider;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.datasource.HttpDataSource;
import androidx.media3.datasource.cache.Cache;
import androidx.media3.datasource.cache.NoOpCacheEvictor;
import androidx.media3.datasource.cache.SimpleCache;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.audiobook_app.AudioplayerFragment;
import com.example.audiobook_app.Domain.AppDatabase;
import com.example.audiobook_app.Domain.Book;
import com.example.audiobook_app.Domain.BookGenerator;
import com.example.audiobook_app.Domain.Chapter;
import com.example.audiobook_app.Domain.DownloadHandler;
import com.example.audiobook_app.HomeFragment;
import com.example.audiobook_app.ProfileFragment;
import com.example.audiobook_app.R;
import com.example.audiobook_app.SettingsFragment;
import com.example.audiobook_app.databinding.ActivityMainBinding;

import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private static final String _preferencesName = "MyFavourites";

    private AppDatabase db; //permanent database for books
    public List<Book> books; //temporary storage for books


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences(_preferencesName, Context.MODE_PRIVATE);


        replaceFragment(new HomeFragment());
        initBottomNavigation();
        db = AppActivity.getDatabase(this);

        BookGenerator bookGenerator = new BookGenerator();
        books = bookGenerator.getBooks(this);


    }

    private void initBottomNavigation() {
        binding.bottomNavigationView.setSelectedItemId(R.id.homeNavMenu); // Set homeNavMenu as selected
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.homeNavMenu) {
                replaceFragment(new HomeFragment());
            }
            else if (item.getItemId() == R.id.profileNavMenu) {
                replaceFragment(new ProfileFragment());
            }
            else if (item.getItemId() == R.id.settingsNavMenu){
                replaceFragment(new SettingsFragment());
            }
            else if (item.getItemId() == R.id.playerNavMenu){
                replaceFragment(new AudioplayerFragment());
            }
            return true;
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutNavigation,fragment);
        fragmentTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.search_bar_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //imp later
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //imp later
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }


    public void BookToDatabase(Book book)
    {
        if (book == null || book.getChapters() == null) {
            return;
        }
        DownloadBook(book);

        long bookId = db.bookDAO().insert(book); // this should return the id of the inserted book
        List<Chapter> chapters = book.getChapters();

        for (Chapter chapter : chapters) {
            chapter.setBookId((int)bookId);
            db.chapterDAO().insert(chapter);
        }
    }

    private void DownloadBook(Book book) {
        DownloadHandler downloadHandler = new DownloadHandler(this);
        List<Chapter> chapters = book.getChapters();

        for (Chapter chapter : chapters) {
            String oldURL = chapter.getAudioAddress();
            String fileName =  book.getTitle().replace(" ", "_") + "-" + chapter.getNumber();
            String newURL = downloadHandler.getDownloadPath(fileName);
            long downloadId = downloadHandler.downloadChapter(oldURL, fileName);
            chapter.setAudioAddress(newURL);
        }


    }

    public AppDatabase getDb()
    {
        return db;
    }

    public long findBookInDb(Book data) {
        List<Book> books = db.bookDAO().getAllBooks();
        for (Book book : books) {
            if (book.getTitle().equals(data.getTitle())) {
                return book.getId();
            }
        }
        return -1;
    }


}