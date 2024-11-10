package com.jewel.ergon.domain;

/**
 * Enum for representing various languages with their ISO language codes.
 */
public enum Language {

    // Define each language as an enum constant with its ISO code
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    GERMAN("de"),
    CHINESE("zh"),
    JAPANESE("ja"),
    ARABIC("ar"),
    RUSSIAN("ru"),
    PORTUGUESE("pt"),
    ITALIAN("it"),
    HINDI("hi"),
    KOREAN("ko"),
    DUTCH("nl"),
    SWEDISH("sv"),
    POLISH("pl"),
    TURKISH("tr"),
    THAI("th"),
    HEBREW("he"),
    INDONESIAN("id"),
    GREEK("el");

    // Field to store ISO code for each language
    private final String isoCode;

    /**
     * Private constructor to assign ISO code to each language constant.
     *
     * @param isoCode The ISO language code.
     */
    Language(String isoCode) {
        this.isoCode = isoCode;
    }

    /**
     * Getter method to retrieve ISO code.
     *
     * @return The ISO language code.
     */
    public String getIsoCode() {
        return isoCode;
    }

    /**
     * Method to retrieve a Language enum based on an ISO code.
     *
     * @param code The ISO code to look up.
     * @return The corresponding Language, or null if not found.
     */
    public static Language fromIsoCode(String code) {
        for (Language language : Language.values()) {
            if (language.getIsoCode().equalsIgnoreCase(code)) {
                return language;
            }
        }
        return null;
    }
}
