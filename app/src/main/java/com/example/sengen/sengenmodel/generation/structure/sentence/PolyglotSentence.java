package com.example.sengen.sengenmodel.generation.structure.sentence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.example.sengen.sengenmodel.config.Language;

/**
 * A polyglot sentence contains a list of generated, language-equivalent sentences. For each language there exists exactly one sentence in the list.
 */
public class PolyglotSentence {
	
	/** A list of sentences for all languages provided in SenGen24. */
	private List<Sentence> polyglotSentenceList;
	
    public PolyglotSentence() {
		this.polyglotSentenceList = new ArrayList<Sentence>();
	}

	/**
     * Returns the sentence for a specific language.
     * @param language The language (e.g. Spanish).
     * @return The sentence referring to the specified language.
     */
    public Optional<Sentence> getSentence(Language language) {
        return polyglotSentenceList.stream().filter(sen -> sen.getLanguage() == language).findFirst();
    }
    
    /**
     * Given a polyglot sentence fragment object, this method adds each fragment to the sentence of the corresponding language.
     * @param polyglotSentenceFragment A list of fragments for each language.
     */
	public void addAllFragments(PolyglotSentenceFragment polyglotSentenceFragment) {
		List<SentenceFragment> fragments = polyglotSentenceFragment.getPolyglotFragmentList();
		for(SentenceFragment fragment : fragments) {
			Optional<Sentence> sentenceOfLanguage = getSentence(fragment.getLanguage());
			if(sentenceOfLanguage.isPresent()) {
				sentenceOfLanguage.get().appendSentence(fragment);  // Add to the end of the existing sentence
			} else {
				Sentence sentence = new Sentence(fragment.getLanguage(), fragment);
				polyglotSentenceList.add(sentence);
			}
		}
	}
	
	/**
	 * Resolves all sentences of each language. Note that no synonyms are regarded, i.e., only the base translation
	 * will be returned.
	 * @return A map containing each resolved sentence for each language.
	 */
	public Map<Language, String>  resolve() {
		Map<Language, String> resolvedSentence = new HashMap<Language, String>();
		for(Sentence sentence : polyglotSentenceList) {
			Language language = sentence.getLanguage();
			String sentenceString = sentence.resolve();
			resolvedSentence.put(language, sentenceString);
		}
		return resolvedSentence;
	}

	/**
	 * Prints all resolved sentences.
	 */
	public void print() {
		Map<Language, String> resolvedSentence = resolve();
		for(Entry<Language, String> entry : resolvedSentence.entrySet()) {
			System.out.println(entry.getKey().toString() + ": " + entry.getValue());
		}
	}

}
