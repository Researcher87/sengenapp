package com.example.sengen;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.example.sengen.sengenmodel.dictionary.DictionaryManager;
import com.example.sengen.sengenmodel.config.Language;
import com.example.sengen.sengenmodel.config.FilePaths;
import com.example.sengen.sengenmodel.config.generation.SenGenConfiguration;
import com.example.sengen.sengenmodel.dictionary.wordclass.PolyglotDictionaryNoun;
import com.example.sengen.sengenmodel.exception.InitializationException;
import com.example.sengen.sengenmodel.generation.model.NounPhraseGenerator;
import com.example.sengen.sengenmodel.generation.structure.sentence.PolyglotSentence;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the noun phrase generation.
 */
public class NounPhraseGeneratorTest {

    private static DictionaryManager dictionaryManager;
    private static SenGenConfiguration configuration;
	
    @BeforeClass
    public static void initialize() {
    	configuration = new SenGenConfiguration();

		try {
			String pathDictionary = "./src/main/assets/" + FilePaths.FILE_DICT_NOUNS;
			String pathCategories = "./src/main/assets/" + FilePaths.FILE_DICT_CATEGORIES;
			InputStream isDictionary = Files.newInputStream(Paths.get(pathDictionary));
			InputStream isCategories = Files.newInputStream(Paths.get(pathCategories));
			dictionaryManager = new DictionaryManager();
			dictionaryManager.initialize(isDictionary, isCategories);
		} catch (InitializationException | IOException e) {
			e.printStackTrace();
		}
    }
    
	@Test
	public void testDefiniteArticleNP() {
		NounPhraseGenerator nounPhraseGenerator = new NounPhraseGenerator(configuration, dictionaryManager.getDictionary());
		
		try {
			PolyglotDictionaryNoun nounDoor = dictionaryManager.getDictionary().getNoun("door");
			PolyglotSentence sentenceDoor = nounPhraseGenerator.generatePhraseWithDefiniteArticle(nounDoor);
			Map<Language, String> resolvedSentencesDoor = sentenceDoor.resolve();

			PolyglotDictionaryNoun nounSchool = dictionaryManager.getDictionary().getNoun("school");
			PolyglotSentence sentenceSchool = nounPhraseGenerator.generatePhraseWithDefiniteArticle(nounSchool);
			Map<Language, String> resolvedSentencesSchool = sentenceSchool.resolve();

			PolyglotDictionaryNoun nounHospital = dictionaryManager.getDictionary().getNoun("hospital");
			PolyglotSentence sentenceHospital = nounPhraseGenerator.generatePhraseWithDefiniteArticle(nounHospital);
			Map<Language, String> resolvedSentencesHospital = sentenceHospital.resolve();

			assertEquals(resolvedSentencesDoor.get(Language.DE), "die Tür");
			assertEquals(resolvedSentencesSchool.get(Language.DE), "die Schule");
			assertEquals(resolvedSentencesHospital.get(Language.DE), "das Krankenhaus");

			assertEquals(resolvedSentencesDoor.get(Language.EN), "the door");
			assertEquals(resolvedSentencesSchool.get(Language.EN), "the school");
			assertEquals(resolvedSentencesHospital.get(Language.EN), "the hospital");

			assertEquals(resolvedSentencesDoor.get(Language.FR), "la porte");
			assertEquals(resolvedSentencesSchool.get(Language.FR), "l'école");
			assertEquals(resolvedSentencesHospital.get(Language.FR), "l'hôpital");

			assertEquals(resolvedSentencesDoor.get(Language.ES), "la puerta");
			assertEquals(resolvedSentencesSchool.get(Language.ES), "la escuela");
			assertEquals(resolvedSentencesHospital.get(Language.ES), "el hospital");

			assertEquals(resolvedSentencesDoor.get(Language.DA), "døren");
			assertEquals(resolvedSentencesSchool.get(Language.DA), "skolen");
			assertEquals(resolvedSentencesHospital.get(Language.DA), "sygehuset");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}