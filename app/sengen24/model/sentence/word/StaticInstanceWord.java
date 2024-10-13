package sengen24.model.sentence.word;

import sengen24.Language;
import sengen24.dictionary.wordclass.AbstractWord;
import sengen24.exception.DictionaryEntryException;
import sengen24.model.sentence.config.StaticWordType;
import sengen24.model.sentence.resolution.DefiniteArticleResolution;

/**
 * Defines a static (non-declinable) word.
 */
public class StaticInstanceWord extends InstanceWord {

	/** The word type (e.g. definite article). */
	private StaticWordType staticWordType;
	
	/** Possibly, a noun to which this static word refers to (e.g. the noun to an article). */
	private InstanceNoun referenceNoun;
	
	/** Possibly, a word following the article (like an adjective). */
	private InstanceWord followinWordAfterArticle;

	public StaticInstanceWord(StaticWordType staticWordType) {
		this.staticWordType = staticWordType;
	}

	public StaticInstanceWord(StaticWordType staticWordType, InstanceNoun referenceNoun) {
		this.staticWordType = staticWordType;
		this.referenceNoun = referenceNoun;
	}
	
	public StaticInstanceWord(StaticWordType staticWordType, InstanceNoun referenceNoun,
			InstanceWord followinWordAfterArticle) {
		this.staticWordType = staticWordType;
		this.referenceNoun = referenceNoun;
		this.followinWordAfterArticle = followinWordAfterArticle;
	}

	public StaticWordType getStaticWordType() {
		return staticWordType;
	}

	@Override
	public String resolve(Language language) throws DictionaryEntryException, IllegalArgumentException {
		DefiniteArticleResolution definiteArticleResolution = new DefiniteArticleResolution(this, referenceNoun, 
				followinWordAfterArticle);
		return definiteArticleResolution.resolve(language);
	}
	
}
