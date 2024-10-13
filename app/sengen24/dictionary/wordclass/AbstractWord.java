package sengen24.dictionary.wordclass;

import sengen24.Language;

/**
 * Root class for all word classes, representing the core of any word.
 */
public abstract class AbstractWord {

    /** The language to which the word refers. */
    protected Language language;

    /** The spelling of the word. */
    protected String lexeme;

    /** Optionally, a list of alternative spellings. */
    protected String[] altSpellings;

    /** Optionally, word annotations like pluraletantum, mass noun etc. */
    protected String[] annotations;

    public Language getLanguage() {
        return language;
    }

    public String getLexeme() {
        return lexeme;
    }

    public String[] getAltSpellings() {
        return altSpellings;
    }

    public String[] getAnnotations() {
        return annotations;
    }
}
