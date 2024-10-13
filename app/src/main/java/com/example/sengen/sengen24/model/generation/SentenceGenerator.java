package com.example.sengen.sengen24.model.generation;

import com.example.sengen.sengen24.config.generation.SenGenConfiguration;
import com.example.sengen.sengen24.dictionary.Dictionary;
import com.example.sengen.sengen24.exception.NoWordMatchException;

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
