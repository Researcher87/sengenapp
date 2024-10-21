package com.example.sengen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    private GestureDetector gestureDetector;

    public DictionaryFragment(SenGenConfiguration configuration, DictionaryManager dictionaryManager) {
        this.configuration = configuration;
        this.dictionaryManager = dictionaryManager;
        dictionaryIndex = 0;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dictionaryView = inflater.inflate(R.layout.fragment_dictionary, container, false);

        gestureDetector = new GestureDetector(getContext(), new SwipeGestureListener());
        dictionaryView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

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
               handleClickPrevious();
            }
        });

        Button buttonNext = dictionaryView.findViewById(R.id.btn_dict_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickNext();
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


    private void handleClickPrevious() {
        if(dictionaryIndex > 0) {
            dictionaryIndex -= 1;
            showNewWord();
        }
    }

    private void handleClickNext() {
        if(dictionaryIndex < wordList.size()-1) {
            dictionaryIndex += 1;
            showNewWord();
        }
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
            tv_translation_de.setText(germanWord.get().getDictionaryTranslation());
        }

        Optional<? extends AbstractWord> englishWord = word.getTranslation(Language.EN);
        TextView tv_translation_en = dictionaryView.findViewById(R.id.textview_dict_en);
        if(englishWord.isPresent()) {
            tv_translation_en.setText(englishWord.get().getDictionaryTranslation());
        }

        Optional<? extends AbstractWord> frenchWord = word.getTranslation(Language.FR);
        TextView tv_translation_fr = dictionaryView.findViewById(R.id.textview_dict_fr);
        if(frenchWord.isPresent()) {
            tv_translation_fr.setText(frenchWord.get().getDictionaryTranslation());
        }

        Optional<? extends AbstractWord> spanishWord = word.getTranslation(Language.ES);
        TextView tv_translation_es = dictionaryView.findViewById(R.id.textview_dict_es);
        if(spanishWord.isPresent()) {
            tv_translation_es.setText(spanishWord.get().getDictionaryTranslation());
        }

        Optional<? extends AbstractWord> danishWord = word.getTranslation(Language.DA);
        TextView tv_translation_da = dictionaryView.findViewById(R.id.textview_dict_da);
        if(danishWord.isPresent()) {
            tv_translation_da.setText(danishWord.get().getDictionaryTranslation());
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


    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;  // Minimale Distanz für den Swipe
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;  // Minimale Geschwindigkeit für den Swipe

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            Toast.makeText(dictionaryView.getContext(), "abc", Toast.LENGTH_LONG).show();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    return true;
                }
            }
            return false;
        }

        public void onSwipeRight() {
            handleClickNext();
        }

        public void onSwipeLeft() {
            handleClickPrevious();
        }
    }


}