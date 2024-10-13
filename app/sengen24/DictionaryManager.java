package sengen24;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import sengen24.config.FilePaths;
import sengen24.dictionary.Dictionary;
import sengen24.dictionary.wordclass.PolyglotDictionaryNoun;
import sengen24.dictionary.wordclass.PolyglotDictionaryWord;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
     * @throws FileNotFoundException
     */
    public void initializeDictionary() throws FileNotFoundException {
        Gson gson = new Gson();
        final String dictionaryPath = FilePaths.FILE_DICT_NOUNS;

        JsonReader reader = new JsonReader(new FileReader(dictionaryPath));
        final Type NOUN = new TypeToken<List<PolyglotDictionaryNoun>>() {
        }.getType();
        List<PolyglotDictionaryNoun> nouns = gson.fromJson(reader, NOUN);

        dictionary = new Dictionary(nouns);
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

}
