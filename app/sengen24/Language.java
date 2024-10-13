package sengen24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Defines the languages that are supported.
 */
public enum Language {
    EN, DE, FR, ES, DA;

    /**
     * Returns a language by its key.
     * @param key The language key.
     * @return The corresponding language.
     */
    public static Language getLanguage(int key) {
        switch (key) {
            case 0: return DE;
            case 1: return EN;
            case 2: return FR;
            case 3: return ES;
            case 4: return DA;
            default: return DE;
        }
    }
    
    /**
     * Returns all languages.
     * @return A list of all language types of this enumeration.
     */
    public static List<Language> getAllLanguages() {
    	return Arrays.asList(Language.values());
    }
    
    /**
     * Returns all languages with the exception of an arbitrary number of specified languages.
     * @param excludedLanguages An arbitrary number of languages to be excluded from the final list.
     * @return A list of all language types of this enumeration, minus the ones being excluded.
     */
    public static List<Language> getAllLanguagesExcept(Language... excludedLanguages) {
    	List<Language> resultList = new ArrayList<Language>(getAllLanguages());
    	List<Language> excludedList = Arrays.asList(excludedLanguages);
    	resultList.removeAll(excludedList);
    	return resultList;
    }
}
