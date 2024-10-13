package sengen24;

import sengen24.config.generation.SenGenConfiguration;
import sengen24.model.generation.SentenceGenerator;

import javax.swing.*;
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

        try {
            dictionaryManager.initializeDictionary();
            SentenceGenerator sentenceGenerator = new SentenceGenerator(configuration, dictionaryManager.getDictionary());
            sentenceGenerator.generateSentence();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error trying to load dictionary.", "Initialization error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }


    }


}
