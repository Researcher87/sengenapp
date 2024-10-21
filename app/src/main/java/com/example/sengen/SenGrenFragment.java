package com.example.sengen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sengen.sengenmodel.config.FilePaths;
import com.example.sengen.sengenmodel.config.Language;
import com.example.sengen.sengenmodel.config.generation.SenGenConfiguration;
import com.example.sengen.sengenmodel.dictionary.DictionaryManager;
import com.example.sengen.sengenmodel.exception.InitializationException;
import com.example.sengen.sengenmodel.exception.NoWordMatchException;
import com.example.sengen.sengenmodel.generation.model.NounPhraseGenerator;
import com.example.sengen.sengenmodel.generation.structure.sentence.PolyglotSentence;
import com.example.sengen.sengenmodel.generation.structure.sentence.Sentence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;


public class SenGrenFragment extends Fragment {

    private DictionaryManager dictionaryManager;

    private SenGenConfiguration configuration;

    private PolyglotSentence generatedSentence;

    private View senGenView;

    public SenGrenFragment(SenGenConfiguration configuration, DictionaryManager dictionaryManager) {
        this.configuration = configuration;
        this.dictionaryManager = dictionaryManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        senGenView = inflater.inflate(R.layout.fragment_sen_gren, container, false);

        createMainMenu();
        createNewSentence();

        return senGenView;
    }

    private void createMainMenu() {
        Button buttonNew = senGenView.findViewById(R.id.btn_new);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewSentence();
            }
        });

        Button buttonSolve = senGenView.findViewById(R.id.btn_solve);
        buttonSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView solution1 = senGenView.findViewById(R.id.textview_solution1);
                solution1.setText(generatedSentence.resolve().get(Language.EN));

                TextView solution2 = senGenView.findViewById(R.id.textview_solution2);
                solution2.setText(generatedSentence.resolve().get(Language.FR));

                TextView solution3 = senGenView.findViewById(R.id.textview_solution3);
                solution3.setText(generatedSentence.resolve().get(Language.ES));

                TextView solution4 = senGenView.findViewById(R.id.textview_solution4);
                solution4.setText(generatedSentence.resolve().get(Language.DA));
            }
        });

        Button buttonCheck = senGenView.findViewById(R.id.btn_check);
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (generatedSentence == null) {
                    return;
                }

                checkAnswer();
            }
        });
    }


    private void checkAnswer() {
        TextView input1 = senGenView.findViewById(R.id.edittext_target1);
        if(isCorrectAnswer(input1.getText().toString(), Language.EN)) {
            input1.setBackgroundColor(getContext().getColor(R.color.answer_correct));
        } else {
            input1.setBackgroundColor(getContext().getColor(R.color.answer_wrong));
        }

        TextView input2 = senGenView.findViewById(R.id.edittext_target2);
        if(isCorrectAnswer(input2.getText().toString(), Language.FR)) {
            input2.setBackgroundColor(getContext().getColor(R.color.answer_correct));
        } else {
            input2.setBackgroundColor(getContext().getColor(R.color.answer_wrong));
        }

        TextView input3 = senGenView.findViewById(R.id.edittext_target3);
        if(isCorrectAnswer(input3.getText().toString(), Language.ES)) {
            input3.setBackgroundColor(getContext().getColor(R.color.answer_correct));
        } else {
            input3.setBackgroundColor(getContext().getColor(R.color.answer_wrong));
        }

        TextView input4 = senGenView.findViewById(R.id.edittext_target4);
        if(isCorrectAnswer(input4.getText().toString(), Language.DA)) {
            input4.setBackgroundColor(getContext().getColor(R.color.answer_correct));
        } else {
            input4.setBackgroundColor(getContext().getColor(R.color.answer_wrong));
        }
    }


    private void createNewSentence() {
        resetForm();

        NounPhraseGenerator nounPhraseGenerator = new NounPhraseGenerator(configuration, dictionaryManager.getDictionary());
        try {
            TextView sourceText = senGenView.findViewById(R.id.textview_source);
            generatedSentence = nounPhraseGenerator.generateNounPhrase();
            sourceText.setText(generatedSentence.resolve().get(Language.DE));
        } catch (NoWordMatchException e) {
            e.printStackTrace();
        }
    }

    private void resetForm() {
        int inputfieldColor = getContext().getColor(R.color.answer_blank);

        TextView input1 = senGenView.findViewById(R.id.edittext_target1);
        input1.setText("");
        input1.setBackgroundColor(inputfieldColor);

        TextView input2 = senGenView.findViewById(R.id.edittext_target2);
        input2.setText("");
        input2.setBackgroundColor(inputfieldColor);

        TextView input3 = senGenView.findViewById(R.id.edittext_target3);
        input3.setText("");
        input3.setBackgroundColor(inputfieldColor);

        TextView input4 = senGenView.findViewById(R.id.edittext_target4);
        input4.setText("");
        input4.setBackgroundColor(inputfieldColor);

        TextView solution1 = senGenView.findViewById(R.id.textview_solution1);
        solution1.setText("");

        TextView solution2 = senGenView.findViewById(R.id.textview_solution2);
        solution2.setText("");

        TextView solution3 = senGenView.findViewById(R.id.textview_solution3);
        solution3.setText("");

        TextView solution4 = senGenView.findViewById(R.id.textview_solution4);
        solution4.setText("");
    }

    private boolean isCorrectAnswer(String input, Language language) {
        Optional<Sentence> sentence = generatedSentence.getSentence(language);
        if(!sentence.isPresent()) {
            return false;
        }

        String resolved = sentence.get().resolve();
        if(resolved.trim().equals(input.trim())) {
            return true;
        } else {
            return false;
        }
    }
}