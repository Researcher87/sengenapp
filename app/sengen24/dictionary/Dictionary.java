package sengen24.dictionary;

import sengen24.dictionary.wordclass.PolyglotDictionaryNoun;

import java.util.List;
import java.util.Optional;

/**
 * Container for all dictionaries (whole vocabulary).
 */
public class Dictionary {

    /** The list of nouns from the nouns dictionary. */
    private List<PolyglotDictionaryNoun> nouns;

    public Dictionary(List<PolyglotDictionaryNoun> nouns) {
        this.nouns = nouns;
    }

    /**
     * Returns all nouns from the dictionary.
     * @return All nouns from the dictionary.
     */
    public List<PolyglotDictionaryNoun> getNouns() {
        return nouns;
    }

    /**
     * Returns all nouns from the dictionary within a specific language level range.
     * @param minLevel The minimum level.
     * @param maxLevel The maximum level.
     * @return All nouns which are in the specified range.
     */
    public List<PolyglotDictionaryNoun> getNouns(int minLevel, int maxLevel) {
        return nouns.stream().filter((noun) -> noun.getMilestone() >= minLevel && noun.getMilestone() <= maxLevel).toList();
    }
    
    /**
     * Returns a noun dictionary entry by its key.
     * @param key The key of the dictionary entry (name).
     * @return The dictionary entry.
     */
    public PolyglotDictionaryNoun getNoun(String key) {
        Optional<PolyglotDictionaryNoun> result = nouns.stream().filter(
        		(noun) -> noun.getKey().equalsIgnoreCase(key)).findFirst();
        if(result.isPresent()) {
        	return result.get();
        } else {
        	throw new IllegalArgumentException("No noun found in dictionary with key = " + key);
        }
    }

}
