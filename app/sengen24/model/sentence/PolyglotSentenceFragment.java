package sengen24.model.sentence;

import sengen24.Language;
import sengen24.dictionary.wordclass.AbstractWord;
import sengen24.model.sentence.word.InstanceWord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * A polyglot sentence fragment is a fragment (list of words) as part of a sentence for each language.
 */
public class PolyglotSentenceFragment {

    /** The list of words that form the fragment. */
    private List<SentenceFragment> polyglotFragmentList;

    /**
     * Empty constructor -- Creates an empty fragment list.
     */
    public PolyglotSentenceFragment() {
    	polyglotFragmentList = new ArrayList<>();
    }
    
    /**
     * Constructor to create a polyglot fragment instance based on one word. It will be added to a separate fragment
     * for each language.
     * @word The word to add to each fragment.
     */
    public PolyglotSentenceFragment(InstanceWord instanceWord) {
    	polyglotFragmentList = new ArrayList<>();
    	
    	List<Language> languages = Language.getAllLanguages();
    	for( Language language : languages) {
    		SentenceFragment sentenceFragment = new SentenceFragment(language);
    		sentenceFragment.appendFragment(instanceWord);
    		polyglotFragmentList.add(sentenceFragment);
    	}
    }
    
    /**
     * Appends the fragment of a specific language by one word.
     * @param instanceWord The word to add to the fragment.
     * @param language The language of the fragment to be appended.
     */
    public void appendFragmentByWord(InstanceWord instanceWord, Language language) {
    	appendAllFragmentsByWord(instanceWord, Arrays.asList(language));
    }
    
    /**
     * Prepends the fragment of a specific language by one word.
     * @param instanceWord The word to add at the beginning of the fragment.
     * @param language The language of the fragment to be prepended.
     */
    public void prependFragmentByWord(InstanceWord instanceWord, Language language) {
    	prependAllFragmentsByWord(instanceWord, Arrays.asList(language));
    }

    /**
     * Adds a word at the end of the fragment for each language.
     * @param word The word to add at the end of the fragment for each language.
     * @param languages Specifies the fragments to which the word should be added.
     */
    public void appendAllFragmentsByWord(InstanceWord instanceWord, List<Language> languages) {  
    	for( Language language : languages) {
    		Optional<SentenceFragment> sentenceFragment = getSentenceFragment(language);
    		if(sentenceFragment.isPresent()) {
    			sentenceFragment.get().appendFragment(instanceWord);
    		} else {  // There is no fragment for this language yet
    			SentenceFragment newFragment = new SentenceFragment(language, instanceWord);
    			polyglotFragmentList.add(0, newFragment);
    		}
    	}
    }
    
    /**
     * Adds a word at the beginning of the fragment for each language.
     * @param polyglotDictionaryWord A word from the dictionary.
     * @param languages Specifies the fragments to which the word should be added.
     */
    public void prependAllFragmentsByWord(InstanceWord instanceWord, List<Language> languages) {
    	for( Language language : languages) {
    		Optional<SentenceFragment> sentenceFragment = getSentenceFragment(language);
    		if(sentenceFragment.isPresent()) {
    			sentenceFragment.get().prependFragment(instanceWord);
    		} else {  // There is no fragment for this language yet
    			SentenceFragment newFragment = new SentenceFragment(language, instanceWord);
    			polyglotFragmentList.add(newFragment);
    		}
    	}
    }
    
    /**
     * Returns the sentence for a specific language.
     * @param language The language (e.g. Spanish).
     * @return The sentence referring to the specified language.
     */
    public Optional<SentenceFragment> getSentenceFragment(Language language) {
        return polyglotFragmentList.stream().filter(fragm -> fragm.getLanguage() == language).findFirst();
    }

	public List<SentenceFragment> getPolyglotFragmentList() {
		return polyglotFragmentList;
	}
	
	
	public void print() {
		for( SentenceFragment sentenceFragment : polyglotFragmentList) {
			System.out.println(sentenceFragment.getLanguage() + ": " + sentenceFragment.resolve());
		}
	}
    
}
