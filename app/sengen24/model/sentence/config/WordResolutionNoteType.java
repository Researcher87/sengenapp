package sengen24.model.sentence.config;

/**
 * Defines a list of notes or remarks for the resolution of an instance word. Those
 * notes can be applied to control the sentence resolution (e.g. define what character or
 * sentence mark should be put between words).
 */
public enum WordResolutionNoteType {
	DESCENDANT_WITH_APPOSTROPH_NO_SPACE,  	// Elision, e.g. la + amor => l'amor
	DESCENDANT_WITHOUT_SPACE,  				// Merge two words, e.g. DA "mand" + "en" => "manden"
}
