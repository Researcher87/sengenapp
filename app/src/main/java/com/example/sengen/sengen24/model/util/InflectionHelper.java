package com.example.sengen.sengen24.model.util;

import com.example.sengen.sengen24.Language;

/**
 * Helper class for word inflections of all kind.
 */
public class InflectionHelper {

	/**
	 * Determines whether a word needs elision (as in French l'ami) or not.
	 * @param word The word to check.
	 * @param language The language of the word.
	 * @return True, if elision is required, otherwise false.
	 */
	public static boolean needsElision(String word, Language language) {
		switch(language) {
			case FR:
				return StringHelper.startsWithVowel(word) 
						|| SpecialWordsOfLanguage.FRENCH_NOUNS_WITH_ELISION.contains(word);
			default:
				return false;
		}
	}
	
}
