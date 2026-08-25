/**
 * Represents the pickup types available on electric guitars.
 *
 * @author Ram Prasai
 * GitHub: https://github.com/RamPrasai/COSC120-Assignment-3
 */
public enum PickupType {

    HUMBUCKER,
    SINGLE_COIL,
    P90,
    MIXED,
    NA;

    /**
     * Returns a readable name for the pickup type.
     *
     * @return the pickup type as text
     */
    public String toString() {
        return switch (this) {
            case HUMBUCKER -> "Humbucker";
            case SINGLE_COIL -> "Single Coil";
            case P90 -> "P90";
            case MIXED -> "Mixed";
            case NA -> "I don't mind";
        };
    }
}