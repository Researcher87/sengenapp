package com.example.sengen.sengenmodel.generation.util;

/**
 * Helper class for string-related operations.
 */
public class StringHelper {
		
	/**
	 * Determines, whether a word starts with a vowel.
	 * @param word The word to check.
	 * @return True, if the word starts with a vowel, otherwise false.
	 */
	public static boolean startsWithVowel(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }

        char firstChar = Character.toLowerCase(word.charAt(0));

        return firstChar == 'a' || firstChar == 'á' || firstChar == 'à' || firstChar == 'â' || firstChar == 'ä' ||
				firstChar == 'e' || firstChar == 'é' || firstChar == 'è' || firstChar == 'ê' ||
				firstChar == 'i' || firstChar == 'í' || firstChar == 'ì' || firstChar == 'î' ||
				firstChar == 'o' || firstChar == 'ó' || firstChar == 'ò' || firstChar == 'ô' || firstChar == 'ö' ||
				firstChar == 'u' || firstChar == 'ú' || firstChar == 'ù' || firstChar == 'û' || firstChar == 'ü';
	}

}
