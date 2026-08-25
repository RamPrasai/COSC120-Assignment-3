/**
 * Represents the different types of electric guitars
 * available in the Electric Guitar Finder.
 *
 * @author Ram Prasai
 * GitHub: https://github.com/RamPrasai/COSC120-Assignment-3
 */
public enum GuitarType {

    SOLID_BODY,
    SEMI_HOLLOW,
    HOLLOW_BODY,
    NA;

    /**
     * Returns a readable name for the guitar type.
     *
     * @return the guitar type as text
     */
    public String toString() {
        return switch (this) {
            case SOLID_BODY -> "Solid Body";
            case SEMI_HOLLOW -> "Semi-Hollow";
            case HOLLOW_BODY -> "Hollow Body";
            case NA -> "I don't mind";
        };
    }
}