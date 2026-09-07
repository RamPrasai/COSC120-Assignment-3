/**
 * Represents the guitar characteristics that can
 * be used when searching for a guitar.
 *
 * @author Ram Prasai
 * GitHub: https://github.com/RamPrasai/COSC120-Assignment-3
 */
public enum Filter {

    TYPE,
    BRAND,
    PICKUP_TYPE,
    STRINGS,
    HANDEDNESS,
    ACTIVE_PICKUPS,
    GENRES;

    /**
     * Returns a readable name for each search filter.
     *
     * @return the filter name as text
     */
    @Override
    public String toString() {
        return switch (this) {
            case TYPE -> "Guitar type";
            case BRAND -> "Brand";
            case PICKUP_TYPE -> "Pickup type";
            case STRINGS -> "Number of strings";
            case HANDEDNESS -> "Handedness";
            case ACTIVE_PICKUPS -> "Active pickups";
            case GENRES -> "Genres";
        };
    }
}