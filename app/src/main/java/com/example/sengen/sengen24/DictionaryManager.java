package com.example.sengen.sengen24;

import com.example.sengen.sengen24.config.FilePaths;
import com.example.sengen.sengen24.dictionary.Dictionary;
import com.example.sengen.sengen24.dictionary.wordclass.PolyglotDictionaryNoun;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * The dictionary manager loads and provides the dictionary for the sentence generator.
 */
public class DictionaryManager {

    /** Reference to the dictionary. */
    private Dictionary dictionary;

    /**
     * Initializes the dictionary.
     * @param is InputStream of the file to load.
     * @throws FileNotFoundException
     */
    public void initializeDictionary(InputStream is) throws FileNotFoundException {
        Gson gson = new Gson();
        final String dictionaryPath = FilePaths.FILE_DICT_NOUNS;

        JsonReader reader = new JsonReader(new InputStreamReader(is));
        final Type NOUN = new TypeToken<List<PolyglotDictionaryNoun>>() {
        }.getType();
        List<PolyglotDictionaryNoun> nouns = gson.fromJson(reader, NOUN);

        dictionary = new Dictionary(nouns);
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

}
