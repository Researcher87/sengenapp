package sengen24.model.sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sengen24.Language;
import sengen24.dictionary.wordclass.AbstractWord;

/**
 * A sentence is a list of instance fragments of a specific language.
 */
public class Sentence {

	/** The language of the sentence. */
	private Language language;
	
	/** A list of sentence fragments. */
	private List<SentenceFragment> fragments;

	/**
	 * Constructor - Creates a new sentence object with an empty fragment list.
	 * @param language The language of the sentence.
	 */
	public Sentence(Language language) {
		this.language = language;
		this.fragments = new ArrayList<SentenceFragment>();
	}

	
	/**
	 * Constructor to create a sentence of a specific language with an initial fragment to add.
	 * @param language The language of the sentence.
	 * @param fragment The fragment to add.
	 */
	public Sentence(Language language, SentenceFragment fragment) {
		this.language = language;
		this.fragments = new ArrayList<SentenceFragment>();
		fragments.add(fragment);
	}


	/**
	 * Adds another fragment to the sentence object.
	 * @param sentenceFragment A fragment to add at the end of the current sentence.
	 */
	public void appendSentence(SentenceFragment sentenceFragment) {
		fragments.add(sentenceFragment);
	}
	
	public Language getLanguage() {
		return language;
	}

	public List<SentenceFragment> getFragments() {
		return fragments;
	}
	
	public String resolve() {
		StringBuffer sb = new StringBuffer();
		for(SentenceFragment fragment : fragments) {
			sb.append(fragment.resolve());
		}
		
		return sb.toString();
	}


}
