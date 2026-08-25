import java.text.DecimalFormat;

/**
 * Represents one electric guitar stored in the
 * Electric Guitar Finder.
 *
 * @author Ram Prasai
 * GitHub: https://github.com/RamPrasai/COSC120-Assignment-3
 */
public class Guitar {

    private final String guitarId;
    private final String model;
    private final String description;
    private final double price;
    private final DreamGuitar dreamGuitar;

    /**
     * Creates a guitar object.
     *
     * @param guitarId unique ID of the guitar
     * @param model model name of the guitar
     * @param description description of the guitar
     * @param price price of the guitar
     * @param dreamGuitar searchable characteristics of the guitar
     */
    public Guitar(String guitarId,
                  String model,
                  String description,
                  double price,
                  DreamGuitar dreamGuitar) {

        this.guitarId = guitarId;
        this.model = model;
        this.description = description;
        this.price = price;
        this.dreamGuitar = dreamGuitar;
    }

    /**
     * Returns the unique ID of the guitar.
     *
     * @return the guitar ID
     */
    public String getGuitarId() {
        return guitarId;
    }

    /**
     * Returns the brand stored in the guitar's
     * searchable characteristics.
     *
     * @return the guitar brand
     */
    public String getBrand() {
        return (String) dreamGuitar.getFilter(Filter.BRAND);
    }

    /**
     * Returns the model name of the guitar.
     *
     * @return the guitar model
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the description of the guitar.
     *
     * @return the guitar description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the price of the guitar.
     *
     * @return the guitar price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the searchable characteristics
     * belonging to this guitar.
     *
     * @return the DreamGuitar object
     */
    public DreamGuitar getDreamGuitar() {
        return dreamGuitar;
    }

    /**
     * Returns formatted information about this guitar.
     *
     * @return guitar information for display
     */
    public String getGuitarInformation() {

        DecimalFormat df = new DecimalFormat("0.00");

        String output = "\n*******************************************";

        output += "\n" + getBrand() + " " + model
                + " (" + guitarId + ")";

        output += "\n" + description;

        output += dreamGuitar.getInfo();

        output += "\nPrice: $" + df.format(price);

        return output;
    }
}