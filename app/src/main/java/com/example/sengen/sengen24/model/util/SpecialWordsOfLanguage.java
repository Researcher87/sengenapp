package com.example.sengen.sengen24.model.util;

import java.util.Arrays;
import java.util.List;

/**
 * Collections class to define special/exceptional words in a language that need a special treatment.
 */
public class SpecialWordsOfLanguage {
	
	/** French nouns with a silent "H" that require elision (though they not start with a vowel). */
	public static final List<String> FRENCH_NOUNS_WITH_ELISION = Arrays.asList("heure", "histoire", "homme", "hôpital", 
			"hôtel");


}
