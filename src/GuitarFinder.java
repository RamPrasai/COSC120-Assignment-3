import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.*;

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
    private static final String appName = "Electric Guitar Finder";
    private static GuitarRegistry guitarRegistry;

    /**
     * Starts the Electric Guitar Finder application.
     *
     * @param args command-line arguments are not required
     */
    public static void main(String[] args) {

        guitarRegistry = loadGuitars(filePath);

        DreamGuitar dreamGuitar = getFilters();

        processSearchResults(dreamGuitar);

        System.exit(0);
    }


    /**
     * Gets the customer's guitar preferences using
     * JOptionPane dialogs.
     *
     * @return a DreamGuitar containing the customer's choices
     */

    public static DreamGuitar getFilters() {

        Map<Filter, Object> filterMap = new LinkedHashMap<>();

        GuitarType type = (GuitarType) JOptionPane.showInputDialog(
                null,
                "Which type of guitar would you like?",
                appName,
                JOptionPane.QUESTION_MESSAGE,
                null,
                GuitarType.values(),
                GuitarType.SOLID_BODY
        );

        if (type == null) {
            System.exit(0);
        }

        if (type != GuitarType.NA) {
            filterMap.put(Filter.TYPE, type);
        }

        Object[] allBrands =
                guitarRegistry.getAllFilterValues(Filter.BRAND).toArray();

        Object brand = JOptionPane.showInputDialog(
                null,
                "Which brand would you prefer?",
                appName,
                JOptionPane.QUESTION_MESSAGE,
                null,
                allBrands,
                allBrands[0]
        );

        if (brand == null) {
            System.exit(0);
        }

        if (!brand.equals("I don't mind")) {
            filterMap.put(Filter.BRAND, brand);
        }

        PickupType pickupType =
                (PickupType) JOptionPane.showInputDialog(
                        null,
                        "Which pickup type would you prefer?",
                        appName,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        PickupType.values(),
                        PickupType.HUMBUCKER
                );

        if (pickupType == null) {
            System.exit(0);
        }

        if (pickupType != PickupType.NA) {
            filterMap.put(Filter.PICKUP_TYPE, pickupType);
        }

        Object[] stringOptions =
                guitarRegistry.getAllFilterValues(Filter.STRINGS).toArray();

        Object numberOfStrings = JOptionPane.showInputDialog(
                null,
                "How many strings would you prefer?",
                appName,
                JOptionPane.QUESTION_MESSAGE,
                null,
                stringOptions,
                stringOptions[0]
        );

        if (numberOfStrings == null) {
            System.exit(0);
        }

        if (!numberOfStrings.equals("I don't mind")) {
            filterMap.put(Filter.STRINGS, numberOfStrings);
        }

        Handedness handedness =
                (Handedness) JOptionPane.showInputDialog(
                        null,
                        "Which handedness do you require?",
                        appName,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        Handedness.values(),
                        Handedness.RIGHT_HANDED
                );

        if (handedness == null) {
            System.exit(0);
        }

        if (handedness != Handedness.NA) {
            filterMap.put(Filter.HANDEDNESS, handedness);
        }

        String[] activeOptions = {
                "Yes",
                "No",
                "I don't mind"
        };

        int activeChoice = JOptionPane.showOptionDialog(
                null,
                "Would you like active pickups?",
                appName,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                activeOptions,
                activeOptions[0]
        );

        if (activeChoice == -1) {
            System.exit(0);
        }

        if (activeChoice == 0) {
            filterMap.put(Filter.ACTIVE_PICKUPS, true);
        } else if (activeChoice == 1) {
            filterMap.put(Filter.ACTIVE_PICKUPS, false);
        }

        Set<Genre> preferredGenres = new HashSet<>();

        int addAnotherGenre = 0;

        while (addAnotherGenre == 0) {

            Genre genre = (Genre) JOptionPane.showInputDialog(
                    null,
                    "Which music genre would you like the guitar to suit?",
                    appName,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    Genre.values(),
                    Genre.ROCK
            );

            if (genre == null) {
                System.exit(0);
            }

            if (genre == Genre.NA) {
                preferredGenres.clear();
                break;
            }

            preferredGenres.add(genre);

            addAnotherGenre = JOptionPane.showConfirmDialog(
                    null,
                    "Would you like to add another genre?",
                    appName,
                    JOptionPane.YES_NO_OPTION
            );

            if (addAnotherGenre == -1) {
                System.exit(0);
            }
        }

        if (!preferredGenres.isEmpty()) {
            filterMap.put(Filter.GENRES, preferredGenres);
        }

        double minPrice = -1;

        while (minPrice < 0) {

            String input = JOptionPane.showInputDialog(
                    null,
                    "Enter your minimum price:",
                    appName,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) {
                System.exit(0);
            }

            try {

                minPrice = Double.parseDouble(input);

                if (minPrice < 0) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Minimum price cannot be negative.",
                            appName,
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        null,
                        "Please enter a valid number.",
                        appName,
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

        double maxPrice = -1;

        while (maxPrice < minPrice) {

            String input = JOptionPane.showInputDialog(
                    null,
                    "Enter your maximum price:",
                    appName,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) {
                System.exit(0);
            }

            try {

                maxPrice = Double.parseDouble(input);

                if (maxPrice < minPrice) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Maximum price must be at least $"
                                    + minPrice + ".",
                            appName,
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        null,
                        "Please enter a valid number.",
                        appName,
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

        return new DreamGuitar(
                filterMap,
                minPrice,
                maxPrice
        );
    }

    /**
     * Searches for guitars that match the customer's choices
     * and allows the customer to select one of the matches.
     *
     * @param dreamGuitar the customer's preferred guitar
     */

    public static void processSearchResults(DreamGuitar dreamGuitar) {

        List<Guitar> matchingGuitars =
                guitarRegistry.findMatch(dreamGuitar);

        if (matchingGuitars.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "Unfortunately, no guitars matched your search.",
                    appName,
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        Map<String, Guitar> guitarOptions =
                new LinkedHashMap<>();

        for (Guitar guitar : matchingGuitars) {

            String option =
                    guitar.getBrand()
                            + " "
                            + guitar.getModel()
                            + " ("
                            + guitar.getGuitarId()
                            + ") - $"
                            + String.format("%.2f", guitar.getPrice());

            guitarOptions.put(option, guitar);
        }

        Object[] options =
                guitarOptions.keySet().toArray();

        String choice =
                (String) JOptionPane.showInputDialog(
                        null,
                        "We found "
                                + matchingGuitars.size()
                                + " matching guitars.\n"
                                + "Please select one:",
                        appName,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

        if (choice == null) {
            System.exit(0);
        }

        Guitar selectedGuitar =
                guitarOptions.get(choice);

        JOptionPane.showMessageDialog(
                null,
                "You selected:"
                        + selectedGuitar.getGuitarInformation()
                        + "\n\nPlease enter your contact details to make an enquiry.",
                appName,
                JOptionPane.INFORMATION_MESSAGE
        );

        Customer customer = getCustomerDetails();

        writeEnquiryToFile(customer, selectedGuitar);

        JOptionPane.showMessageDialog(
                null,
                "Thank you, " + customer.name()
                        + "!\nYour guitar enquiry has been saved."
                        + "\nWe will contact you using the details provided.",
                appName,
                JOptionPane.INFORMATION_MESSAGE
        );
    }



    /**
     * Gets and validates the customer's contact details.
     *
     * @return a Customer containing the validated details
     */

    public static Customer getCustomerDetails() {

        String name = "";

        while (!name.matches("[a-zA-Z '-]{2,}")) {

            name = JOptionPane.showInputDialog(
                    null,
                    "Please enter your full name:",
                    appName,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (name == null) {
                System.exit(0);
            }

            name = name.strip();

            if (!name.matches("[a-zA-Z '-]{2,}")) {

                JOptionPane.showMessageDialog(
                        null,
                        "Please enter a valid name.",
                        appName,
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

        String phoneNumber = "";

        while (!phoneNumber.matches("0\\d{9}")) {

            phoneNumber = JOptionPane.showInputDialog(
                    null,
                    "Please enter your 10-digit phone number:",
                    appName,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (phoneNumber == null) {
                System.exit(0);
            }

            phoneNumber = phoneNumber.strip();

            if (!phoneNumber.matches("0\\d{9}")) {

                JOptionPane.showMessageDialog(
                        null,
                        "Please enter a valid 10-digit phone number.",
                        appName,
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

        String emailAddress = "";

        while (!emailAddress.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

            emailAddress = JOptionPane.showInputDialog(
                    null,
                    "Please enter your email address:",
                    appName,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (emailAddress == null) {
                System.exit(0);
            }

            emailAddress = emailAddress.strip();

            if (!emailAddress.matches(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

                JOptionPane.showMessageDialog(
                        null,
                        "Please enter a valid email address.",
                        appName,
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

        return new Customer(
                name,
                phoneNumber,
                emailAddress
        );
    }

    /**
     * Writes the customer's guitar enquiry to a text file.
     *
     * @param customer the customer making the enquiry
     * @param guitar the guitar selected by the customer
     */
    public static void writeEnquiryToFile(
            Customer customer,
            Guitar guitar) {

        String enquiryFilePath =
                customer.name().replace(" ", "_")
                        + "_guitar_enquiry.txt";

        Path path = Path.of(enquiryFilePath);

        String enquiry =
                "Customer: " + customer.name()
                        + "\nPhone number: " + customer.phoneNumber()
                        + "\nEmail address: " + customer.emailAddress()
                        + "\n\nGuitar enquiry:"
                        + "\nGuitar ID: " + guitar.getGuitarId()
                        + "\nBrand: " + guitar.getBrand()
                        + "\nModel: " + guitar.getModel()
                        + "\nPrice: $" + String.format("%.2f", guitar.getPrice());

        try {

            Files.writeString(path, enquiry);

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "The enquiry file could not be created.",
                    appName,
                    JOptionPane.ERROR_MESSAGE
            );

            System.out.println(
                    "Error writing enquiry file: "
                            + e.getMessage()
            );

            System.exit(0);
        }
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