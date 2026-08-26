import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Stores the guitars available in the application
 * and provides methods for searching them.
 *
 * @author Ram Prasai
 * GitHub: https://github.com/RamPrasai/COSC120-Assignment-3
 */
public class GuitarRegistry {

    private final Set<Guitar> guitars = new HashSet<>();

    /**
     * Adds a guitar to the registry.
     *
     * @param guitar the guitar to add
     */
    public void addGuitar(Guitar guitar) {
        guitars.add(guitar);
    }
    /**
     * Returns the number of guitars stored in the registry.
     *
     * @return the number of guitars
     */
    public int getNumberOfGuitars() {
        return guitars.size();
    }

    /**
     * Finds all available values for a search filter.
     *
     * @param filter the filter being searched
     * @return the available filter values
     */
    public Set<Object> getAllFilterValues(Filter filter) {

        Set<Object> allValues = new LinkedHashSet<>();

        for (Guitar guitar : guitars) {

            if (guitar.getDreamGuitar()
                    .getAllFilters()
                    .containsKey(filter)) {

                Object value =
                        guitar.getDreamGuitar().getFilter(filter);

                if (value instanceof Collection<?>) {

                    allValues.addAll((Collection<?>) value);

                } else {

                    allValues.add(value);
                }
            }
        }

        allValues.add("I don't mind");

        return allValues;
    }

    /**
     * Searches the registry for guitars that match
     * the customer's requirements.
     *
     * @param dreamGuitar the customer's search criteria
     * @return a list of matching guitars
     */
    public List<Guitar> findMatch(DreamGuitar dreamGuitar) {

        List<Guitar> matchingGuitars = new ArrayList<>();

        for (Guitar guitar : guitars) {

            if (!guitar.getDreamGuitar().matches(dreamGuitar)) {
                continue;
            }

            if (guitar.getPrice() < dreamGuitar.getMinPrice()
                    || guitar.getPrice() > dreamGuitar.getMaxPrice()) {

                continue;
            }

            matchingGuitars.add(guitar);
        }

        return matchingGuitars;
    }
}