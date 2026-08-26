import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Runs the Electric Guitar Finder application.
 * This class loads the guitar data and will control
 * the interaction with the user.
 *
 * @author Ram Prasai
 * GitHub: https://github.com/RamPrasai/COSC120-Assignment-3
 */
public class GuitarFinder {

    private static final String filePath = "guitars.txt";
    private static GuitarRegistry guitarRegistry;

    /**
     * Starts the Electric Guitar Finder application.
     *
     * @param args command-line arguments are not required
     */
    public static void main(String[] args) {

        guitarRegistry = loadGuitars(filePath);

        System.out.println(
                "Electric Guitar Finder loaded "
                        + guitarRegistry.getNumberOfGuitars()
                        + " guitars."
        );
    }

    /**
     * Reads the guitar data file and creates a Guitar
     * object for every guitar in the file.
     *
     * @param filePath location of the guitar data file
     * @return registry containing all loaded guitars
     */
    public static GuitarRegistry loadGuitars(String filePath) {

        GuitarRegistry registry = new GuitarRegistry();

        Path path = Path.of(filePath);

        List<String> fileContents = null;

        try {

            fileContents = Files.readAllLines(path);

        } catch (IOException e) {

            System.out.println(
                    "The guitar file could not be loaded. "
                            + "Check that the file path is correct."
            );

            System.out.println("Error message: " + e.getMessage());

            System.exit(0);
        }

        // Start at 1 because line 0 contains the headings.
        for (int i = 1; i < fileContents.size(); i++) {

            String[] info = fileContents.get(i).split("\\[");

            String[] guitarInfo = info[0].split(",");

            String genresRaw = info[1].replace("],", "");
            String description = info[2].replace("]", "").strip();

            String guitarId = guitarInfo[0].strip();
            String brand = guitarInfo[1].strip();
            String model = guitarInfo[2].strip();

            GuitarType type = null;

            try {

                type = GuitarType.valueOf(
                        guitarInfo[3].strip().toUpperCase()
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Guitar type could not be read on line "
                                + (i + 1) + "."
                );

                System.exit(0);
            }

            double price = 0;

            try {

                price = Double.parseDouble(
                        guitarInfo[4].strip()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Price could not be read on line "
                                + (i + 1) + "."
                );

                System.exit(0);
            }

            int numberOfStrings = 0;

            try {

                numberOfStrings = Integer.parseInt(
                        guitarInfo[5].strip()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Number of strings could not be read on line "
                                + (i + 1) + "."
                );

                System.exit(0);
            }

            PickupType pickupType = null;

            try {

                pickupType = PickupType.valueOf(
                        guitarInfo[6].strip().toUpperCase()
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Pickup type could not be read on line "
                                + (i + 1) + "."
                );

                System.exit(0);
            }

            Handedness handedness = null;

            try {

                handedness = Handedness.valueOf(
                        guitarInfo[7].strip().toUpperCase()
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Handedness could not be read on line "
                                + (i + 1) + "."
                );

                System.exit(0);
            }

            boolean activePickups =
                    guitarInfo[8].strip().equalsIgnoreCase("true");

            Set<Genre> genres = loadGenres(
                    genresRaw,
                    i + 1
            );

            Map<Filter, Object> filterMap =
                    new LinkedHashMap<>();

            filterMap.put(Filter.BRAND, brand);
            filterMap.put(Filter.TYPE, type);
            filterMap.put(Filter.PICKUP_TYPE, pickupType);
            filterMap.put(Filter.STRINGS, numberOfStrings);
            filterMap.put(Filter.HANDEDNESS, handedness);
            filterMap.put(Filter.ACTIVE_PICKUPS, activePickups);
            filterMap.put(Filter.GENRES, genres);

            DreamGuitar dreamGuitar =
                    new DreamGuitar(filterMap);

            Guitar guitar =
                    new Guitar(
                            guitarId,
                            model,
                            description,
                            price,
                            dreamGuitar
                    );

            registry.addGuitar(guitar);
        }

        return registry;
    }

    /**
     * Converts the genre information from the text file
     * into a Set of Genre values.
     *
     * @param rawGenres genres read from the data file
     * @param lineNumber line currently being processed
     * @return set containing the guitar genres
     */
    private static Set<Genre> loadGenres(
            String rawGenres,
            int lineNumber) {

        Set<Genre> genres = new HashSet<>();

        String[] genreData = rawGenres.split(",");

        for (String genreText : genreData) {

            try {

                Genre genre = Genre.valueOf(
                        genreText.strip().toUpperCase()
                );

                genres.add(genre);

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "Genre could not be read on line "
                                + lineNumber + "."
                );

                System.exit(0);
            }
        }

        return genres;
    }
}