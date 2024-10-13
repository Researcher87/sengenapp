package sengen24.dictionary.wordclass;

import sengen24.dictionary.util.Genus;

/**
 * Expresses a synonym (alternative word) to a dictionary entry.
 */
public class NounSynonym {

    /** The word. */
    private String lexeme;

    /** The genus of the noun. */
    private Genus genus;

    /** The plural of the noun (optional, if it cannot be automatically determined). */
    private String plural;

    public String getLexeme() {
        return lexeme;
    }

    public Genus getGenus() {
        return genus;
    }

    public String getPlural() {
        return plural;
    }
}
