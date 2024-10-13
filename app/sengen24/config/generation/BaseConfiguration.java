package sengen24.config.generation;

import sengen24.Language;

/**
 * Provides basic parameters used for sentence generation.
 */
public class BaseConfiguration {

    /** The source language to be used. */
    private Language sourceLanguage;

    /** The target languages to be used (for practicing and testing). */
    private Language[] targetLanguages;

    /** The minimum level a word must have to be regarded by the sentence generator. */
    private int minimumLanguageLevel;

    /** The maximum level a word must have to be regarded by the sentence generator. */
    private int maximumLanguageLevel;

    /**
     * Empty constructor generating the default configuration.
     */
    public BaseConfiguration() {
        sourceLanguage = Language.DE;
        targetLanguages = new Language[] {Language.EN, Language.FR, Language.ES, Language.DA};
        minimumLanguageLevel = 1;
        maximumLanguageLevel = 100;
    }

    public Language getSourceLanguage() {
        return sourceLanguage;
    }

    public Language[] getTargetLanguages() {
        return targetLanguages;
    }

    public int getMinimumLanguageLevel() {
        return minimumLanguageLevel;
    }

    public int getMaximumLanguageLevel() {
        return maximumLanguageLevel;
    }

}
