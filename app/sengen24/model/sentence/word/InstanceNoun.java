package sengen24.model.sentence.word;

import java.util.Optional;

import sengen24.Language;
import sengen24.dictionary.wordclass.AbstractWord;
import sengen24.dictionary.wordclass.Noun;
import sengen24.dictionary.wordclass.PolyglotDictionaryNoun;

/**
 * An instance noun is a noun from the dictionary used as part of the generated sentence.
 */
public class InstanceNoun extends InstanceWord {

    /** Specifies whether the word should be used in plural. */
    private boolean plural;

    public InstanceNoun(PolyglotDictionaryNoun dictionaryNoun, boolean plural) {
        super(dictionaryNoun);
        this.plural = plural;
    }

    public PolyglotDictionaryNoun getDictionaryNoun() {
        return (PolyglotDictionaryNoun) dictionaryWord;
    }

    public boolean isPlural() {
        return plural;
    }

	@Override
	public String resolve(Language language) {
		Optional<? extends AbstractWord> translation = dictionaryWord.getTranslation(language);
		if(translation.isPresent()) {
			Noun noun = (Noun) translation.get();
			if(isPlural()) {
				return noun.getPlural() != null ? noun.getPlural() : "n/a";
			} else {
				return noun.getLexeme();
			}
		}
		
		return "n/a";
	}
    
    
}
