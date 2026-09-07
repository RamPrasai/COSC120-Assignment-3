/**
 * Represents whether a guitar is made for a
 * right-handed or left-handed player.
 *
 * @author Ram Prasai
 * GitHub: https://github.com/RamPrasai/COSC120-Assignment-3
 */
public enum Handedness {

    RIGHT_HANDED,
    LEFT_HANDED,
    NA;

    /**
     * Returns a readable handedness value.
     *
     * @return the handedness as text
     */
    @Override
    public String toString() {
        return switch (this) {
            case RIGHT_HANDED -> "Right Handed";
            case LEFT_HANDED -> "Left Handed";
            case NA -> "I don't mind";
        };
    }
}