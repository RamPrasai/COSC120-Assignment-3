/**
 * Represents the music genres a guitar may be suited for.
 *
 * @author Ram Prasai
 * GitHub: https://github.com/RamPrasai/COSC120-Assignment-3
 */
public enum Genre {

    METAL,
    ROCK,
    BLUES,
    JAZZ,
    COUNTRY,
    POP,
    NA;

    /**
     * Returns a readable genre name.
     *
     * @return the genre as text
     */
    @Override
    public String toString() {
        return switch (this) {
            case METAL -> "Metal";
            case ROCK -> "Rock";
            case BLUES -> "Blues";
            case JAZZ -> "Jazz";
            case COUNTRY -> "Country";
            case POP -> "Pop";
            case NA -> "I don't mind";
        };
    }
}