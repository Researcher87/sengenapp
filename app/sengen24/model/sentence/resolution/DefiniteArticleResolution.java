package sengen24.model.sentence.resolution;

import java.util.Optional;

import sengen24.Language;
import sengen24.dictionary.util.Genus;
import sengen24.dictionary.wordclass.AbstractWord;
import sengen24.dictionary.wordclass.Noun;
import sengen24.exception.DictionaryEntryException;
import sengen24.model.sentence.config.WordResolutionNoteType;
import sengen24.model.sentence.word.InstanceNoun;
import sengen24.model.sentence.word.InstanceWord;
import sengen24.model.sentence.word.StaticInstanceWord;
import sengen24.model.util.InflectionHelper;

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
			case Language.DE:
				return resolveGerman();
			case Language.EN:
				return "the";
			case Language.FR:
				return resolveFrench();
			case Language.ES:
				return resolveSpanish();
			case Language.DA:
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
