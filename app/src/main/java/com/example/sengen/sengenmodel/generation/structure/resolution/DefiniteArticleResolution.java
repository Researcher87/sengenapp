package com.example.sengen.sengenmodel.generation.structure.resolution;

import com.example.sengen.sengenmodel.config.Language;
import com.example.sengen.sengenmodel.dictionary.util.Genus;
import com.example.sengen.sengenmodel.dictionary.wordclass.AbstractWord;
import com.example.sengen.sengenmodel.exception.DictionaryEntryException;
import com.example.sengen.sengenmodel.generation.util.InflectionHelper;

import java.util.Optional;

import com.example.sengen.sengenmodel.dictionary.wordclass.Noun;
import com.example.sengen.sengenmodel.generation.structure.config.WordResolutionNoteType;
import com.example.sengen.sengenmodel.generation.structure.word.InstanceNoun;
import com.example.sengen.sengenmodel.generation.structure.word.InstanceWord;
import com.example.sengen.sengenmodel.generation.structure.word.StaticInstanceWord;

/**
 * Helper class to resolve definite articles.
 */
public class DefiniteArticleResolution {
	
	/** The instance word which is handled (the definite article). */
	private StaticInstanceWord instanceWord;

	/** The noun to which the article refers. */
	private InstanceNoun referenceNoun;
	
	/** The following word of the article. */
	private InstanceWord followingWord;
	
	public DefiniteArticleResolution(StaticInstanceWord instanceWord, InstanceNoun referenceNoun, 
			InstanceWord followingWord) {
		this.instanceWord = instanceWord;
		this.referenceNoun = referenceNoun;
		this.followingWord = followingWord;
	}

	public String resolve(Language language) throws DictionaryEntryException {
		if(referenceNoun == null || referenceNoun.getDictionaryNoun() == null) {
			throw new IllegalArgumentException("Reference noun or dictionary noun is not set.");
		}
		
		switch(language) {
			case DE:
				return resolveGerman();
			case EN:
				return "the";
			case FR:
				return resolveFrench();
			case ES:
				return resolveSpanish();
			case DA:
				return resolveDanish();
			default:
				throw new IllegalArgumentException();
		}
	}
	
	private String resolveGerman() throws DictionaryEntryException {		
		if(referenceNoun.isPlural()) {
			return "die";
		} else {
			Optional<? extends AbstractWord> word = referenceNoun.getDictionaryNoun().getTranslation(Language.DE);
			if(word.isPresent()) {
				Genus genus = ((Noun) word.get()).getGenus();
				switch(genus) {
				case M:
					return "der";
				case F:
					return "die";
				case N:
					return "das";
				default:
					throw new DictionaryEntryException("Missing genus for German noun " + word.get().getLexeme());
				}
			}
			throw new DictionaryEntryException("Missing German translation for noun " + word.get().getLexeme());
		}
	}
	
	private String resolveFrench() throws DictionaryEntryException {
		if(followingWord == null) {
			throw new IllegalArgumentException("Following word is not defined, but required for sentence "
					+ "resolution.");
		}
		
		if(referenceNoun.isPlural()) {
			return "les";
		} else {
			Optional<? extends AbstractWord> word = referenceNoun.getDictionaryWord().getTranslation(Language.FR);
			if(word.isPresent()) {
				String lexeme = word.get().getLexeme();
				
				// Check if Elision is necessary
				if(InflectionHelper.needsElision(lexeme, Language.FR)) {
					instanceWord.addWordResolutionNote(WordResolutionNoteType.DESCENDANT_WITH_APPOSTROPH_NO_SPACE, 
							Language.FR);
					return "l";
				}
				
				Genus genus = ((Noun) word.get()).getGenus();
				switch(genus) {
				case M:
					return "le";
				case F:
					return "la";
				default:
					throw new DictionaryEntryException("Missing genus for French noun " + word.get().getLexeme());
				}
			}
		}
		
		throw new IllegalArgumentException();
	}
	
	private String resolveSpanish() throws DictionaryEntryException {
		if(followingWord == null) {
			throw new IllegalArgumentException("Following word is not defined, but required for sentence "
					+ "resolution.");
		}
		
		Optional<? extends AbstractWord> word = referenceNoun.getDictionaryWord().getTranslation(Language.ES);
		if(!word.isPresent()) {
			throw new DictionaryEntryException("Missing Spanish translation for noun " + word.get().getLexeme());
		}
		
		Genus genus = ((Noun) word.get()).getGenus();
			
		if(referenceNoun.isPlural()) {
			switch(genus) {
			case M:
				return "los";
			case F:
				return "las";
			default:
				throw new DictionaryEntryException("Missing genus for Spanish noun " + word.get().getLexeme());
			}	
		} else {
			switch(genus) {
			case M:
				return "el";
			case F:
				return "la";
			default:
				throw new DictionaryEntryException("Missing genus for Spanish noun " + word.get().getLexeme());
			}
		}
		
	}
	
	private String resolveDanish() throws DictionaryEntryException {		
		if(followingWord == null) {
			throw new IllegalArgumentException("Following word is not defined, but required for sentence "
					+ "resolution.");
		}
		
		Optional<? extends AbstractWord> word = referenceNoun.getDictionaryWord().getTranslation(Language.DA);
		if(!word.isPresent()) {
			throw new DictionaryEntryException("Missing Danish translation for noun " + word.get().getLexeme());
		}
		
		String lexeme = word.get().getLexeme();
		Genus genus = ((Noun) word.get()).getGenus();
		
		referenceNoun.addWordResolutionNote(WordResolutionNoteType.DESCENDANT_WITHOUT_SPACE, Language.DA);
		
		if(!referenceNoun.isPlural()) {			
			switch(genus) {
				case U: 
					return lexeme.endsWith("e") ? "n" : "en";
				case N: 
					return lexeme.endsWith("e") ? "t" : "et";
				default:
					throw new DictionaryEntryException("Missing genus for Danish noun " + word.get().getLexeme());
			}
		} else {
			return lexeme.endsWith("e") || lexeme.endsWith("r")  ? "ne" : "ene";
		}
	}
	
}
