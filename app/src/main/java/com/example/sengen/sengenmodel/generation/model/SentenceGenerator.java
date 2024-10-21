package com.example.sengen.sengenmodel.generation.model;

import com.example.sengen.sengenmodel.config.generation.SenGenConfiguration;
import com.example.sengen.sengenmodel.dictionary.Dictionary;
import com.example.sengen.sengenmodel.exception.NoWordMatchException;

/**
 * Base class to generate polyglot sentences.
 */
public class SentenceGenerator {

    private SenGenConfiguration configuration;
    private Dictionary dictionary;

    public SentenceGenerator(SenGenConfiguration configuration, Dictionary dictionary) {
        this.configuration = configuration;
        this.dictionary = dictionary;
    }
	
    public void generateSentence() {

    	NounPhraseGenerator nounPhraseGenerator = new NounPhraseGenerator(configuration, dictionary);
    	try {
			nounPhraseGenerator.generateNounPhrase();
		} catch (NoWordMatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

}
