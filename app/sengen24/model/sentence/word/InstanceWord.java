package sengen24.model.sentence.word;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sengen24.Language;
import sengen24.dictionary.wordclass.AbstractWord;
import sengen24.dictionary.wordclass.PolyglotDictionaryWord;
import sengen24.exception.DictionaryEntryException;
import sengen24.model.sentence.config.WordResolutionNoteType;

/**
 * An instance word is any word from the dictionary, or any static word, that has been picked for the generated sentence in a
 * specific language.
 *
 * Compared to the dictionary word it comes with an additional configuration, e.g. a noun used in plural.
 */
public abstract class InstanceWord {

    /** An instance of the word of a specific language. */
    protected PolyglotDictionaryWord dictionaryWord;
    
    /** List of Word resolution notes for each language. */
    private List<WordResolutionNote> wordResolutionNoteList;
    
    /**
     * Empty constructor, used for static instance words.
     */
    public InstanceWord() {
    	wordResolutionNoteList = new ArrayList<WordResolutionNote>();
    }

    public InstanceWord(PolyglotDictionaryWord instanceWord) {
    	wordResolutionNoteList = new ArrayList<WordResolutionNote>();
        this.dictionaryWord = instanceWord;
    }

    public PolyglotDictionaryWord getDictionaryWord() {
        return dictionaryWord;
    }
    
    /**
     * Adds a word resolution note to this word for a specific language.
     * @param resolutionNoteType The word resolution note type to set.
     * @param language The language to which the note refers.
     */
    public void addWordResolutionNote(WordResolutionNoteType resolutionNoteType, Language language) {
    	Optional<WordResolutionNote> wordResolutionNote = getWordResolutionNotes(language);
    	if(!wordResolutionNote.isPresent()) {
    		WordResolutionNote newWordResolutionNote = new WordResolutionNote(language);
    		newWordResolutionNote.addWordResolutionNote(resolutionNoteType);
    		wordResolutionNoteList.add(newWordResolutionNote);
    	} else {
    		wordResolutionNote.get().addWordResolutionNote(resolutionNoteType);
    	}
    }
    
    /**
     * Returns the word resolution type notes of a specific language.
     * @param language The language to which the word resolution note types refer.
     * @return The word resolution notes object as optional.
     */
    public Optional<WordResolutionNote> getWordResolutionNotes(Language language) {
    	return wordResolutionNoteList.stream().filter(
    			note -> note.getLanguage() == language).findFirst();
    }
    
    /**
     * Specifies whether a word resolution note type exists for this word instance for a specific language.
     * @param type The word resolution note type to check.
     * @param language The language to which is refers.
     * @return True, if this type exists for the given language, otherwise false.
     */
    public boolean containsWordResolutionNote(WordResolutionNoteType type, Language language) {
    	Optional<WordResolutionNote> wordResolutionNote = getWordResolutionNotes(language);
    	if(!wordResolutionNote.isPresent()) {
    		return false;
    	} else {
    		return wordResolutionNote.get().getWordResolutionNotes().contains(type);
    	}
    }
    
    /**
     * Resolves the instance word to a word to be printed in a sentence.
     * @param language The language to use.
     * @return The resolved word (e.g. 'houses' in a plural noun instance of 'house').
     */
    public abstract String resolve(Language language) throws DictionaryEntryException ;
    
}
