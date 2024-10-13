package sengen24.model.generation;

import sengen24.Language;
import sengen24.config.generation.SenGenConfiguration;
import sengen24.dictionary.Dictionary;
import sengen24.dictionary.wordclass.PolyglotDictionaryNoun;
import sengen24.exception.NoWordMatchException;
import sengen24.model.generation.util.RandomWordPicker;
import sengen24.model.sentence.PolyglotSentence;
import sengen24.model.sentence.PolyglotSentenceFragment;
import sengen24.model.sentence.config.StaticWordType;
import sengen24.model.sentence.config.WordResolutionNoteType;
import sengen24.model.sentence.word.InstanceNoun;
import sengen24.model.sentence.word.StaticInstanceWord;

import java.util.List;

/**
 * Class to generate noun phrases (NP).
 */
public class NounPhraseGenerator {

    private SenGenConfiguration configuration;
    private Dictionary dictionary;

    public NounPhraseGenerator(SenGenConfiguration configuration, Dictionary dictionary) {
        this.configuration = configuration;
        this.dictionary = dictionary;
    }

    public void generateNounPhrase() throws NoWordMatchException {
    	PolyglotSentence sentence = generatePhraseWithDefiniteArticle();
    	sentence.print();
    }
    
    /**
     * Create a Noun phrase with a definite article as determiner.
     * @return An NP phrase using a definite article as determiner.
     * @throws NoWordMatchException Caused if the configuration is to string so that no
     * sentence cannot be produced.
     */
    public PolyglotSentence generatePhraseWithDefiniteArticle() throws NoWordMatchException {
        RandomWordPicker randomWordPicker = new RandomWordPicker(configuration);
        int minLevel = configuration.getBaseConfiguration().getMinimumLanguageLevel();
        int maxLevel = configuration.getBaseConfiguration().getMaximumLanguageLevel();
        List<PolyglotDictionaryNoun> nouns = dictionary.getNouns(minLevel, maxLevel);
        PolyglotDictionaryNoun noun = (PolyglotDictionaryNoun) randomWordPicker.getRandomObjectFromList(nouns);

        return generatePhraseWithDefiniteArticle(noun);
    }
    
    
    public PolyglotSentence generatePhraseWithDefiniteArticle(PolyglotDictionaryNoun noun) throws NoWordMatchException {
        InstanceNoun instanceNoun = new InstanceNoun(noun, false);
        instanceNoun.addWordResolutionNote(WordResolutionNoteType.DESCENDANT_WITHOUT_SPACE, Language.DA);
        PolyglotSentenceFragment polyglotInstanceFragment = new PolyglotSentenceFragment(instanceNoun);
        
        // ToDo: Right now, we pass instance noun as following word. If we use an adjective, this might be the following word.
        StaticInstanceWord defArticle = new StaticInstanceWord(StaticWordType.DEFINITE_ARTICLE, instanceNoun, 
        		instanceNoun);
        polyglotInstanceFragment.prependAllFragmentsByWord(defArticle, Language.getAllLanguagesExcept(Language.DA));
        polyglotInstanceFragment.appendFragmentByWord(defArticle, Language.DA);
        
        PolyglotSentence polyglotSentence = new PolyglotSentence();
        polyglotSentence.addAllFragments(polyglotInstanceFragment);
        return polyglotSentence;
    }

}
