package com.example.sengen;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.sengen.sengenmodel.dictionary.DictionaryManager;
import com.example.sengen.sengenmodel.config.Language;
import com.example.sengen.sengenmodel.config.FilePaths;
import com.example.sengen.sengenmodel.dictionary.wordclass.AbstractWord;
import com.example.sengen.sengenmodel.dictionary.wordclass.Noun;
import com.example.sengen.sengenmodel.dictionary.wordclass.PolyglotDictionaryNoun;
import com.example.sengen.sengenmodel.exception.InitializationException;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests related to the dictionary loading.
 */
public class DictionaryTest {

    private static DictionaryManager dictionaryManager;

    @BeforeClass
    public static void initialize() {
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
    public void testDictionaryLoading() {
        PolyglotDictionaryNoun nounRoom = dictionaryManager.getDictionary().getNoun("room");
        assertNotNull(nounRoom);

        // Test that for each language there is a translation.
        for(Language language : Language.getAllLanguages()) {
            Optional<? extends AbstractWord> translation = nounRoom.getTranslation(language);
            assertTrue(translation.isPresent());
        }

        // Test that the German synonym "Raum" exists.
        Optional<? extends AbstractWord> german = nounRoom.getTranslation(Language.DE);
        assertTrue(german.isPresent());

        List<Noun> synonymList = ((Noun) german.get()).getSynonyms();
        assertNotNull(synonymList);
        assertEquals(synonymList.size(), 1);

        String synonym = synonymList.get(0).getLexeme();
        assertEquals(synonym, "Raum");
    }

}