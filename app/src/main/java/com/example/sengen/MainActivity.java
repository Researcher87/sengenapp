package com.example.sengen;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sengen.sengen24.DictionaryManager;
import com.example.sengen.sengen24.Language;
import com.example.sengen.sengen24.config.FilePaths;
import com.example.sengen.sengen24.config.generation.SenGenConfiguration;
import com.example.sengen.sengen24.exception.NoWordMatchException;
import com.example.sengen.sengen24.model.generation.NounPhraseGenerator;
import com.example.sengen.sengen24.model.generation.SentenceGenerator;
import com.example.sengen.sengen24.model.sentence.PolyglotSentence;
import com.example.sengen.sengen24.model.sentence.Sentence;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Main activity - Controller of the Main Menu View.
 */
public class MainActivity extends AppCompatActivity {

    private DictionaryManager dictionaryManager;

    private SenGenConfiguration configuration;

    private PolyglotSentence generatedSentence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            InputStream is = getAssets().open(FilePaths.FILE_DICT_NOUNS);
            dictionaryManager = new DictionaryManager();
            dictionaryManager.initializeDictionary(is);
            configuration = new SenGenConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createMainMenu();

    }


    private void createMainMenu() {
        Button buttonNew = findViewById(R.id.btn_new);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NounPhraseGenerator nounPhraseGenerator = new NounPhraseGenerator(configuration, dictionaryManager.getDictionary());
                try {
                    TextView sourceText = findViewById(R.id.textview_source);
                    generatedSentence = nounPhraseGenerator.generateNounPhrase();
                    sourceText.setText(generatedSentence.resolve().get(Language.DE));
                } catch (NoWordMatchException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        Button buttonSolve = findViewById(R.id.btn_solve);
        buttonSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView solution1 = findViewById(R.id.textview_solution1);
                solution1.setText(generatedSentence.resolve().get(Language.EN));

                TextView solution2 = findViewById(R.id.textview_solution2);
                solution2.setText(generatedSentence.resolve().get(Language.FR));

                TextView solution3 = findViewById(R.id.textview_solution3);
                solution3.setText(generatedSentence.resolve().get(Language.ES));

                TextView solution4 = findViewById(R.id.textview_solution4);
                solution4.setText(generatedSentence.resolve().get(Language.DA));
            }
        });
    }

}