package com.example.sengen.sengenmodel.generation.model;

import com.example.sengen.sengenmodel.config.generation.SenGenConfiguration;
import com.example.sengen.sengenmodel.config.Language;
import com.example.sengen.sengenmodel.dictionary.Dictionary;
import com.example.sengen.sengenmodel.dictionary.wordclass.PolyglotDictionaryNoun;
import com.example.sengen.sengenmodel.exception.NoWordMatchException;
import com.example.sengen.sengenmodel.generation.model.util.RandomWordPicker;
import com.example.sengen.sengenmodel.generation.structure.sentence.PolyglotSentence;
import com.example.sengen.sengenmodel.generation.structure.sentence.PolyglotSentenceFragment;
import com.example.sengen.sengenmodel.generation.structure.config.StaticWordType;
import com.example.sengen.sengenmodel.generation.structure.config.WordResolutionNoteType;
import com.example.sengen.sengenmodel.generation.structure.word.InstanceNoun;
import com.example.sengen.sengenmodel.generation.structure.word.StaticInstanceWord;

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

    public PolyglotSentence generateNounPhrase() throws NoWordMatchException {
    	PolyglotSentence sentence = generatePhraseWithDefiniteArticle();
    	return sentence;
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
