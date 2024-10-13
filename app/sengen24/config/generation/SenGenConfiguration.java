package sengen24.config.generation;

/**
 * Base class to configure the sentence generation.
 */
public class SenGenConfiguration {

    /** Basic configuration of the sentence generation process. */
    private BaseConfiguration baseConfiguration;

    /**
     * Empty Constructor to generate the default configuration.
     */
    public SenGenConfiguration() {
        baseConfiguration = new BaseConfiguration();
    }

    public BaseConfiguration getBaseConfiguration() {
        return baseConfiguration;
    }
}
