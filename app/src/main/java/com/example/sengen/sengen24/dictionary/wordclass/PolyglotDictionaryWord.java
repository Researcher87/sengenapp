package com.example.sengen.sengen24.dictionary.wordclass;

import com.example.sengen.sengen24.Language;

import java.util.List;
import java.util.Optional;

/**
 * Defines a word in the dictionary, which comes with different translations.
 */
public abstract class PolyglotDictionaryWord {

    /** ID/Key of the word (the english word). */
    protected String key;

    /** A list of categories to which the word may refer. */
    protected String[] categories;

    /** The weight of the word for the random word picker. */
    protected double weight;

    /** The milestone to which the word belongs. */
    protected int milestone;

    /** Possibly some background information or notes to the word. */
    protected String background;

    public String getKey() {
        return key;
    }

    public String[] getCategories() {
        return categories;
    }

    public double getWeight() {
        return weight;
    }

    public int getMilestone() {
        return milestone;
    }

    public String getBackground() {
        return background;
    }

    /**
     * Returns a list of words for each language.
     * @return A list of words for each language.
     */
    public abstract List<? extends AbstractWord> getTranslations();

    /**
     * Returns the word of this dictionary entry for a specific language.
     * @param language The language (e.g. Spanish).
     * @return The word of the corresponding language (e.g. casa in the dictionary entry 'house').
     */
    public Optional<? extends AbstractWord> getTranslation(Language language) {
        return getTranslations().stream().filter(word -> word.getLanguage() == language).findFirst();
    }

}
