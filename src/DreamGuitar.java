import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Stores the characteristics a customer wants in an electric guitar.
 * It is also used to store the searchable characteristics of
 * guitars in the database.
 *
 * @author Ram Prasai
 * GitHub: https://github.com/RamPrasai/COSC120-Assignment-3
 */
public class DreamGuitar {

    private final Map<Filter, Object> filterMap;
    private final double minPrice;
    private final double maxPrice;

    /**
     * Creates a DreamGuitar for a customer's search.
     *
     * @param filterMap the selected search criteria
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     */
    public DreamGuitar(Map<Filter, Object> filterMap,
                       double minPrice,
                       double maxPrice) {

        this.filterMap = new LinkedHashMap<>(filterMap);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    /**
     * Creates a DreamGuitar for the characteristics
     * of a guitar stored in the database.
     *
     * @param filterMap the guitar characteristics
     */
    public DreamGuitar(Map<Filter, Object> filterMap) {

        this.filterMap = new LinkedHashMap<>(filterMap);
        this.minPrice = -1;
        this.maxPrice = -1;
    }

    /**
     * Returns a copy of the filter map.
     *
     * @return a copy of the stored filters
     */
    public Map<Filter, Object> getAllFilters() {
        return new LinkedHashMap<>(filterMap);
    }

    /**
     * Gets the value stored for one filter.
     *
     * @param key the filter to look for
     * @return the value stored for that filter
     */
    public Object getFilter(Filter key) {
        return getAllFilters().get(key);
    }

    /**
     * Returns the minimum price.
     *
     * @return minimum price
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * Returns the maximum price.
     *
     * @return maximum price
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * Creates readable information about the guitar characteristics.
     *
     * @return formatted filter information
     */
    public String getInfo() {

        StringBuilder description = new StringBuilder();

        for (Filter key : filterMap.keySet()) {

            if (getFilter(key) instanceof Collection<?>) {

                description.append("\n").append(key).append(":");

                for (Object value :
                        ((Collection<?>) getFilter(key)).toArray()) {

                    description.append("\n --> ").append(value);
                }

            } else {

                description.append("\n")
                        .append(key)
                        .append(": ")
                        .append(getFilter(key));
            }
        }

        return description.toString();
    }

    /**
     * Checks whether this guitar matches the customer's search.
     *
     * @param dreamGuitar the customer's search requirements
     * @return true if the guitar matches, otherwise false
     */
    public boolean matches(DreamGuitar dreamGuitar) {

        for (Filter key : dreamGuitar.getAllFilters().keySet()) {

            if (!this.getAllFilters().containsKey(key)) {
                return false;
            }

            // A collection such as genres only needs one common value.
            if (getFilter(key) instanceof Collection<?>
                    && dreamGuitar.getFilter(key) instanceof Collection<?>) {

                Set<Object> commonValues =
                        new HashSet<>(
                                (Collection<?>) dreamGuitar.getFilter(key));

                commonValues.retainAll(
                        (Collection<?>) getFilter(key));

                if (commonValues.isEmpty()) {
                    return false;
                }

            } else {

                if (!this.getFilter(key)
                        .equals(dreamGuitar.getFilter(key))) {

                    return false;
                }
            }
        }

        return true;
    }
}