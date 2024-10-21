package com.example.sengen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sengen.sengenmodel.config.Language;
import com.example.sengen.sengenmodel.config.generation.SenGenConfiguration;
import com.example.sengen.sengenmodel.dictionary.DictionaryManager;
import com.example.sengen.sengenmodel.dictionary.wordclass.AbstractWord;
import com.example.sengen.sengenmodel.dictionary.wordclass.PolyglotDictionaryNoun;
import com.example.sengen.sengenmodel.dictionary.wordclass.PolyglotDictionaryWord;

import java.util.List;
import java.util.Optional;

public class DictionaryFragment extends Fragment {

    private SenGenConfiguration configuration;

    private DictionaryManager dictionaryManager;

    private List<PolyglotDictionaryNoun> wordList;

    private View dictionaryView;

    private int dictionaryIndex;

    public DictionaryFragment(SenGenConfiguration configuration, DictionaryManager dictionaryManager) {
        this.configuration = configuration;
        this.dictionaryManager = dictionaryManager;
        dictionaryIndex = 0;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dictionaryView = inflater.inflate(R.layout.fragment_dictionary, container, false);

        createMenu();
        return dictionaryView;
    }


    private void createMenu() {
        wordList = dictionaryManager.getDictionary().getNouns();

        showNewWord();

        Button buttonPrevious = dictionaryView.findViewById(R.id.btn_dict_previous);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dictionaryIndex > 0) {
                    dictionaryIndex -= 1;
                    showNewWord();
                }
            }
        });

        Button buttonNext = dictionaryView.findViewById(R.id.btn_dict_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dictionaryIndex < wordList.size()-1) {
                    dictionaryIndex += 1;
                    showNewWord();
                }
            }
        });

        Button buttonShowEntry = dictionaryView.findViewById(R.id.btn_dict_showentry);
        buttonShowEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTranslations();
            }
        });
    }


    private void showNewWord() {
        resetTranslation();

        PolyglotDictionaryWord word = wordList.get(dictionaryIndex);
        TextView textview = dictionaryView.findViewById(R.id.textview_dict_entry);
        textview.setText(word.getKey());
    }


    private void showTranslations() {
        PolyglotDictionaryWord word = wordList.get(dictionaryIndex);

        Optional<? extends AbstractWord> germanWord = word.getTranslation(Language.DE);
        TextView tv_translation_de = dictionaryView.findViewById(R.id.textview_dict_de);
        if(germanWord.isPresent()) {
            tv_translation_de.setText(germanWord.get().getLexeme());
        }

        Optional<? extends AbstractWord> englishWord = word.getTranslation(Language.EN);
        TextView tv_translation_en = dictionaryView.findViewById(R.id.textview_dict_en);
        if(englishWord.isPresent()) {
            tv_translation_en.setText(englishWord.get().getLexeme());
        }

        Optional<? extends AbstractWord> frenchWord = word.getTranslation(Language.FR);
        TextView tv_translation_fr = dictionaryView.findViewById(R.id.textview_dict_fr);
        if(frenchWord.isPresent()) {
            tv_translation_fr.setText(frenchWord.get().getLexeme());
        }

        Optional<? extends AbstractWord> spanishWord = word.getTranslation(Language.ES);
        TextView tv_translation_es = dictionaryView.findViewById(R.id.textview_dict_es);
        if(spanishWord.isPresent()) {
            tv_translation_es.setText(spanishWord.get().getLexeme());
        }

        Optional<? extends AbstractWord> danishWord = word.getTranslation(Language.DA);
        TextView tv_translation_da = dictionaryView.findViewById(R.id.textview_dict_da);
        if(danishWord.isPresent()) {
            tv_translation_da.setText(danishWord.get().getLexeme());
        }
    }


    private void resetTranslation() {
        TextView tv_translation_en = dictionaryView.findViewById(R.id.textview_dict_en);
        tv_translation_en.setText("");

        TextView tv_translation_de = dictionaryView.findViewById(R.id.textview_dict_de);
        tv_translation_de.setText("");

        TextView tv_translation_fr = dictionaryView.findViewById(R.id.textview_dict_fr);
        tv_translation_fr.setText("");

        TextView tv_translation_es = dictionaryView.findViewById(R.id.textview_dict_es);
        tv_translation_es.setText("");

        TextView tv_translation_da = dictionaryView.findViewById(R.id.textview_dict_da);
        tv_translation_da.setText("");
    }

}