/**
 * Represents a customer making an enquiry about a guitar.
 *
 * @param name the customer's full name
 * @param phoneNumber the customer's 10-digit phone number
 * @param emailAddress the customer's email address
 *
 * @author Ram Prasai
 * GitHub: https://github.com/RamPrasai/COSC120-Assignment-3
 */
public record Customer(
        String name,
        String phoneNumber,
        String emailAddress) {
}