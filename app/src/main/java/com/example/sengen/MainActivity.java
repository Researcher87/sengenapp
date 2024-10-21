package com.example.sengen;

import static com.example.sengen.R.*;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sengen.sengenmodel.config.FilePaths;
import com.example.sengen.sengenmodel.config.generation.SenGenConfiguration;
import com.example.sengen.sengenmodel.dictionary.DictionaryManager;
import com.example.sengen.sengenmodel.exception.InitializationException;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;

/**
 * Main activity - Controller of the Main Menu View.
 */
public class MainActivity extends AppCompatActivity {

    private DictionaryManager dictionaryManager;

    private SenGenConfiguration configuration;

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        try {
            initializeData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        tabLayout = findViewById(id.tabLayout);

        loadMainView();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up TabLayout listener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    loadMainView();
                } else if (position == 1) {
                    loadDictionaryView();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }


    private void initializeData() throws Exception {
        try {
            InputStream dictionaryInputStream = getAssets().open(FilePaths.FILE_DICT_NOUNS);
            InputStream categoriesInputStream = getAssets().open(FilePaths.FILE_DICT_CATEGORIES);

            dictionaryManager = new DictionaryManager();
            dictionaryManager.initialize(dictionaryInputStream, categoriesInputStream);
            configuration = new SenGenConfiguration();
        } catch (InitializationException | IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            throw e;
        }

        if(dictionaryManager.getDictionary() == null || dictionaryManager.getDictionary().getNouns() == null
                || dictionaryManager.getCategories() == null) {
            Toast.makeText(this, "Dictionary or categories list does not contain any entries.",
                    Toast.LENGTH_LONG).show();
            throw new Exception("Error trying to load dictionary.");
        } else {
            try {
                dictionaryManager.checkCategories();
            } catch(InitializationException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                throw new Exception("Error trying to load dictionary.");
            }
        }
    }

    private void loadMainView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, new SenGrenFragment(configuration, dictionaryManager));
        ft.commit();
    }

    private void loadDictionaryView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, new DictionaryFragment(configuration, dictionaryManager));
        ft.commit();
    }

}