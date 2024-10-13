package com.example.sengen.sengen24.model.sentence;

import com.example.sengen.sengen24.Language;
import com.example.sengen.sengen24.config.ResolutionConfig;
import com.example.sengen.sengen24.exception.DictionaryEntryException;
import com.example.sengen.sengen24.model.sentence.config.WordResolutionNoteType;
import com.example.sengen.sengen24.model.sentence.word.InstanceWord;

import java.util.ArrayList;
import java.util.List;

/**
 * A sentence fragment is a part of generated sentence of a specific language.
 */
public class SentenceFragment {

	private Language language;
	
    private List<InstanceWord> wordList;

    /**
     * Constructor creating an empty fragment.
     * @param language The language of the fragment.
     */
	public SentenceFragment(Language language) {
		this.language = language;
		wordList = new ArrayList<InstanceWord>();
	}
	
    /**
     * Constructor creating a fragment with an initial instance word..
     * @param language The language of the fragment.
     * @param instanceWord The initial word to set.
     */
	public SentenceFragment(Language language, InstanceWord instanceWord) {
		this.language = language;
		wordList = new ArrayList<InstanceWord>();
		wordList.add(instanceWord);
	}
	
	public Language getLanguage() {
		return language;
	}

	public List<InstanceWord> getWordList() {
		return wordList;
	}
	
	/**
	 * Adds a word at the end of the word list in the fragment.
	 * @param word The word to add.
	 */
	public void appendFragment(InstanceWord word) {
		wordList.add(word);
	}
	
	/**
	 * Puts a word at the beginning of the fragment.
	 * @param word The word to add.
	 */
	public void prependFragment(InstanceWord word) {
		wordList.add(0, word);
	}
	
	public String resolve() {
		StringBuffer sb = new StringBuffer();
		for(InstanceWord instanceWord : wordList) {
			
			try {
				sb.append(instanceWord.resolve(language));
			} catch (DictionaryEntryException | IllegalArgumentException e) {
				sb.append(ResolutionConfig.RESOLUTION_ERROR_LABEL);
				e.printStackTrace();
			}
			
			if(instanceWord.containsWordResolutionNote(WordResolutionNoteType.DESCENDANT_WITHOUT_SPACE, language)) {
				continue;
			} 
			if(instanceWord.containsWordResolutionNote(WordResolutionNoteType.DESCENDANT_WITH_APPOSTROPH_NO_SPACE, language)) {
				sb.append(ResolutionConfig.APPOSTROPHE);
				continue;
			}
			
			sb.append(ResolutionConfig.SPACE);
		}
		return sb.toString().trim();
	}

}
