package rb.project.activefox.utilserivces.message;

public enum Language {
    /**
     * Vi language.
     */
    vi,
    /**
     * En language.
     */
    en;

//    @JsonCreator
    public static Language fromValue(String value) {
        return "en".equals(value) ? en : vi;
    }
}
