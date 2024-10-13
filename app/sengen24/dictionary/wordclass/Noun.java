package sengen24.dictionary.wordclass;

import sengen24.dictionary.util.Genus;

import java.util.List;

/**
 * Represents a noun word in a specific language.
 */
public class Noun extends AbstractWord {

    /** The genus of the noun. */
    private Genus genus;

    /** The plural of the noun (optional, if it cannot be automatically determined). */
    private String plural;

    /** A list of possible synonyms. */
    private List<NounSynonym> synonyms;

    public Genus getGenus() {
        return genus;
    }

    public String getPlural() {
        return plural;
    }

    public List<NounSynonym> getSynonyms() {
        return synonyms;
    }
}
