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
         * Should return false if the checksum is invalid.
         */
        @Test
        void shouldReturnFalseForInvalidChecksum() {
            String invalidChecksumId = "2001014800087"; // Luhn checksum fails
            assertFalse(ValidateSaId.isIdNumberValid(invalidChecksumId));
        }
    }
}
