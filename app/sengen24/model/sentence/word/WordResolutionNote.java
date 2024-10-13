package sengen24.model.sentence.word;

import java.util.ArrayList;
import java.util.List;

import sengen24.Language;
import sengen24.model.sentence.config.WordResolutionNoteType;

/**
 * Container class for word resolution note types of a word, referring to a specific language. For each
 * language, a list of those notes can be attached to tell the sentence resolution process how exactly
 * words are to be printed (or connected with each other, i.e., which sentence marks or characters should
 * be put in between).
 */
public class WordResolutionNote {
	
	/** The language to which the word resolution notes refer. */
	private Language language;
	
	/** List of notes referring to the instance word. */
	private List<WordResolutionNoteType> wordResolutionNotes;
	
	/**
	 * Constructor - Creates an empty list of word resolution notes for the specific language.
	 * @param language The language to which the notes refer.
	 */
	public WordResolutionNote(Language language) {
		this.language = language;
		this.wordResolutionNotes = new ArrayList<WordResolutionNoteType>();
	}

	/**
	 * Adds a word resolution note type to the list.
	 * @param wordResolutionNoteType The word resolution note type to add.
	 */
	public void addWordResolutionNote(WordResolutionNoteType wordResolutionNoteType) {
		wordResolutionNotes.add(wordResolutionNoteType);
	}
	
	public Language getLanguage() {
		return language;
	}

	public List<WordResolutionNoteType> getWordResolutionNotes() {
		return wordResolutionNotes;
	}	

}
