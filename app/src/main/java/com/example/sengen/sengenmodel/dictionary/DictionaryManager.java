package com.example.sengen.sengenmodel.dictionary;

import com.example.sengen.sengenmodel.dictionary.wordclass.PolyglotDictionaryNoun;
import com.example.sengen.sengenmodel.exception.InitializationException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The dictionary manager loads and provides the dictionary for the sentence generator.
 */
public class DictionaryManager {

    /** Reference to the dictionary. */
    private Dictionary dictionary;

    private List<Category> categories;

    private Set<String> categoryLookupList;

    public void initialize(InputStream dictionaryInputStream, InputStream categoriesInputStream)
            throws InitializationException {
        initializeCategories(categoriesInputStream);
        initializeDictionary(dictionaryInputStream);
    }

    /**
     * Initializes the dictionary.
     * @param is InputStream of the file to load.
     * @throws FileNotFoundException
     */
    public void initializeCategories(InputStream is) throws InitializationException {
        Gson gson = new Gson();

        try {
            JsonReader reader = new JsonReader(new InputStreamReader(is));
            final Type CATEGORY = new TypeToken<List<Category>>() {
            }.getType();

            categories = gson.fromJson(reader, CATEGORY);

            categoryLookupList = new HashSet<>();
            for(Category category : categories) {
                categoryLookupList.add(category.getCategory().trim());
            }
        } catch(Exception e) {
            throw new InitializationException("Error trying to parse categories file using GSON parser: "
                    + e.getMessage() , e);
        }
    }

    /**
     * Initializes the dictionary.
     * @param is InputStream of the file to load.
     * @throws FileNotFoundException
     */
    public void initializeDictionary(InputStream is) throws InitializationException {
        Gson gson = new Gson();

        try {
            JsonReader reader = new JsonReader(new InputStreamReader(is));
            final Type NOUN = new TypeToken<List<PolyglotDictionaryNoun>>() {
            }.getType();
            List<PolyglotDictionaryNoun> nouns = gson.fromJson(reader, NOUN);

            dictionary = new Dictionary(nouns);
        } catch(Exception e) {
            throw new InitializationException("Error trying to parse dictionary file using GSON parser.", e);
        }
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Checks that each dictionary entry has a valid category (i.e. any of the defined categories in the category list).
     */
    public void checkCategories() throws InitializationException {
        for(PolyglotDictionaryNoun noun : dictionary.getNouns()) {
            String[] nounCategories = noun.getCategories();
            for(String nounCategory : nounCategories) {
                if(!existsCategory(nounCategory)) {
                    throw new InitializationException("Noun <" + noun.getKey() + "> has invalid category: "
                            + nounCategory);
                }
            }
        }
    }

    public boolean existsCategory(String category) {
        return categoryLookupList.contains(category);
    }

}
