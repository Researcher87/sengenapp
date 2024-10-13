package com.example.sengen;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.sengen.sengen24.DictionaryManager;
import com.example.sengen.sengen24.Language;
import com.example.sengen.sengen24.config.FilePaths;
import com.example.sengen.sengen24.dictionary.wordclass.AbstractWord;
import com.example.sengen.sengen24.dictionary.wordclass.Noun;
import com.example.sengen.sengen24.dictionary.wordclass.NounSynonym;
import com.example.sengen.sengen24.dictionary.wordclass.PolyglotDictionaryNoun;

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
            String path = "./src/main/assets/" + FilePaths.FILE_DICT_NOUNS;
            InputStream is = Files.newInputStream(Paths.get(path));
            dictionaryManager = new DictionaryManager();
            dictionaryManager.initializeDictionary(is);
        } catch (IOException e) {
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

        List<NounSynonym> synonymList = ((Noun) german.get()).getSynonyms();
        assertNotNull(synonymList);
        assertEquals(synonymList.size(), 1);

        String synonym = synonymList.get(0).getLexeme();
        assertEquals(synonym, "Raum");
    }

}