package com.example.sengen.sengenmodel.dictionary.wordclass;

import java.util.List;

/**
 * Collection of translations of a certain noun.
 */
public class PolyglotDictionaryNoun extends PolyglotDictionaryWord {

    /** A list of nouns in the different languages. */
    private List<Noun> translations;

    @Override
    public List<? extends AbstractWord> getTranslations() {
        return translations;
    }
}
