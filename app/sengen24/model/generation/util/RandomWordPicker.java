package sengen24.model.generation.util;

import sengen24.config.generation.SenGenConfiguration;
import sengen24.dictionary.wordclass.PolyglotDictionaryWord;
import sengen24.exception.NoWordMatchException;

import java.util.List;

/**
 * Helper class to randomly pick a word from a list of given words, based on each word's individual weight.
 */
public class RandomWordPicker {

    private SenGenConfiguration senGenConfiguration;

    public RandomWordPicker(SenGenConfiguration senGenConfiguration) {
        this.senGenConfiguration = senGenConfiguration;
    }

    /**
     * Given a list of words, this method picks a random element based on the given word weights and returns it.
     * @param list A list of words.
     * @return A randomly picked word, based on the word weights.
     */
    public PolyglotDictionaryWord getRandomObjectFromList(List<? extends PolyglotDictionaryWord> list) throws NoWordMatchException {
        int weightSum = 0;   // Defines the maximum value (total sum) across all words to pick

        for(PolyglotDictionaryWord word : list) {
            double weight = word.getWeight();
            weightSum += weight;
        }

        double randomWeightValue = weightSum * RandomNumberGenerator.getInstance().makeRandomDouble();

        weightSum = 0;
        int correspondingIndex;

        for(correspondingIndex = 0; correspondingIndex < list.size(); correspondingIndex++) {
            double weight = list.get(correspondingIndex).getWeight();

            if(weight == 0) {
                continue;   // Never regard words that have a weight of 0 (= disabled)
            }

            weightSum += weight;
            if(weightSum >= randomWeightValue) {
                break;
            }
        }

        PolyglotDictionaryWord randomWordCandidate = list.get(correspondingIndex);
        boolean keepOnSearching = doesWordMatchSetLanguageLevel(randomWordCandidate) == false;

        /*
         * In case that the language level does not fit, we simply take the next word from the list until we find a relevant one.
         * Note: We may start looking from the beginning of the word list again, if we can't find any relevant one in the remaining list.
         */
        if(keepOnSearching) {
            int newIndex = correspondingIndex+1;
            boolean foundAlternativeWord = false;
            int attempts = 0;
            while( newIndex != correspondingIndex) { // Go through the list, possibly as long as we are at the current index again
                if(newIndex < list.size()) {
                    PolyglotDictionaryWord nextWord = list.get(newIndex);
                    if(doesWordMatchSetLanguageLevel(nextWord)) {
                        correspondingIndex = newIndex;
                        foundAlternativeWord = true;
                        break;
                    }
                } else {
                    newIndex = 0; // Continue at the beginning of the list
                }
                attempts++;
                if(attempts == list.size()) {   // We tried every word in the list -> none matches the language level
                    throw new NoWordMatchException();
                }
            }
            if(!foundAlternativeWord) {  // No relevant word found -- the settings are too strict.
                throw new NoWordMatchException("No matching word found.");
            }
        }

        return list.get(correspondingIndex);
    }

    /**
     * Checks whether a given word is within the language level defined in the application levels. If the language level is 0,
     * the word will always be picked.
     * @param word The word to check.
     * @return True, if the given word is in within the specified language level, otherwise false.
     */
    private boolean doesWordMatchSetLanguageLevel(PolyglotDictionaryWord word) {
        int maxLevel = senGenConfiguration.getBaseConfiguration().getMaximumLanguageLevel();
        return word.getMilestone() == 0 || word.getMilestone() <= maxLevel;
    }

}
