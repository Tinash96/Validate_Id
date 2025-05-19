import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for the ValidateSaId utility class.
 * Tests are organized using @Nested and @ParameterizedTest for better readability and maintenance.
 */
public class ValidateSaIdTest {

    /**
     * Tests for valid South African ID numbers.
     */
    @Nested
    class ValidIdNumbers {

        /**
         * Valid ID numbers should return true.
         */
        @ParameterizedTest
        @ValueSource(strings = {
            "2001014800086",
            "2909035800085"
        })
        void shouldReturnTrueForValidIds(String id) {
            assertTrue(ValidateSaId.isIdNumberValid(id));
        }
    }

    /**
     * Tests for various invalid ID number conditions.
     */
    @Nested
    class InvalidIdNumbers {

        /**
         * Invalid ID numbers should return false:
         * - Too short
         * - Contains non-numeric characters
         * - Invalid birth date (month/day)
         * - Invalid citizenship digit
         */
        @ParameterizedTest
        @ValueSource(strings = {
            "20010148000",      // Too short
            "20010A4800086",    // Non-numeric character
            "2013014800086",    // Invalid month (13)
            "2001324800086",    // Invalid day (32)
            "2001014800286"     // Invalid citizenship digit
        })
        void shouldReturnFalseForInvalidIds(String id) {
            assertFalse(ValidateSaId.isIdNumberValid(id));
        }

        /**
         * Null or empty string input should return false.
         */
        @ParameterizedTest
        @NullAndEmptySource
        void shouldReturnFalseForNullOrEmptyInput(String id) {
            assertFalse(ValidateSaId.isIdNumberValid(id));
        }
    }

    /**
     * Tests specific to checksum validation.
     */
    @Nested
    class ChecksumValidation {

        /**
         * Should return true if the checksum is valid.
         */
        @Test
        void shouldReturnTrueForValidChecksum() {
            String validChecksumId = "2001014800086"; // Known valid ID
            assertTrue(ValidateSaId.isIdNumberValid(validChecksumId));
        }

        /**
         * Should return false if the checksum is invalid.
         */
        @Test
        void shouldReturnFalseForInvalidChecksum() {
            String invalidChecksumId = "2001014800087"; // Luhn checksum fails
            assertFalse(ValidateSaId.isIdNumberValid(invalidChecksumId));
        }
    }
    
    /**
     * Tests specific to gender code validation.
     */
    @Nested
    class GenderCodeValidation {

        /**
         * Valid gender codes (0000 to 9999) should return true.
         */
        @ParameterizedTest
        @ValueSource(strings = {
            "2001010000086",  // gender code 0000 (lower bound)
            "2001019999086"   // gender code 9999 (upper bound)
        })
        void shouldReturnTrueForValidGenderCodes(String id) {
            assertTrue(ValidateSaId.isIdNumberValid(id));
        }

        /**
         * Invalid gender codes (less than 0000 or greater than 9999) should return false.
         * Since digits are 4 digits, invalid cases might be hard to form but we simulate by invalid checksum or format.
         */
        @ParameterizedTest
        @ValueSource(strings = {
            "2001011000086", // a valid gender code within range, so just a placeholder
        })
        void shouldReturnTrueForBoundaryGenderCodes(String id) {
            // This test can be used for boundary if needed
            assertTrue(ValidateSaId.isIdNumberValid(id));
        }

        // Note: Since gender code is numeric substring of 4 digits,
        // values outside 0000-9999 cannot occur because of the regex check.
        // But if business rules change, add tests accordingly.
    }
}
