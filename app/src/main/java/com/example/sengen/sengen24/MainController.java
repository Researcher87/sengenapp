package com.example.sengen.sengen24;

import com.example.sengen.sengen24.config.generation.SenGenConfiguration;
import com.example.sengen.sengen24.model.generation.SentenceGenerator;

import java.io.FileNotFoundException;

/**
 * Main controller class of the application.
 */
public class MainController {

    private DictionaryManager dictionaryManager = new DictionaryManager();

    private SenGenConfiguration configuration;

    public MainController() {
        configuration = new SenGenConfiguration();
        run();
    }

    public void run() {


    }
}
