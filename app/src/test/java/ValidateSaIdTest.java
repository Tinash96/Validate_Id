import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for the {@link ValidateSaId} utility class.
 */
public class ValidateSaIdTest {

    /**
     * Tests to verify that valid South African ID numbers are correctly validated.
     */
    @Nested
    class ValidIdNumbers {
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
     * Tests to verify that invalid South African ID numbers are correctly rejected.
     */
    @Nested
    class InvalidIdNumbers {
        @ParameterizedTest
        @ValueSource(strings = {
            "20010148000",      // Too short
            "20010A4800086",    // Non-numeric character
            "2013014800086",    // Invalid month
            "2001324800086",    // Invalid day
            "2001014800286"     // Invalid citizenship digit
        })
        void shouldReturnFalseForInvalidIds(String id) {
            assertFalse(ValidateSaId.isIdNumberValid(id));
        }

        @ParameterizedTest
        @NullAndEmptySource
        void shouldReturnFalseForNullOrEmptyInput(String id) {
            assertFalse(ValidateSaId.isIdNumberValid(id));
        }
    }

    /**
     * Tests related to the Luhn checksum validation in South African ID numbers.
     */
    @Nested
    class ChecksumValidation {
        @Test
        void shouldReturnTrueForValidChecksum() {
            String validChecksumId = "2001014800086";
            assertTrue(ValidateSaId.isIdNumberValid(validChecksumId));
        }

        @Test
        void shouldReturnFalseForInvalidChecksum() {
            String invalidChecksumId = "2001014800087";
            assertFalse(ValidateSaId.isIdNumberValid(invalidChecksumId));
        }
    }

    /**
     * Tests for detecting gender from South African ID numbers.
     */
    @Nested
    class GenderDetection {

        /**
         * Should return FEMALE for gender codes below 5000.
         */
        @Test
        void shouldReturnFemaleForGenderCodeBelow5000() {
            String femaleId = "2001010000086"; // gender code 0000
            assertEquals(ValidateSaId.Gender.FEMALE, ValidateSaId.getGender(femaleId));
        }

        /**
         * Should return MALE for gender codes 5000 or above.
         */
        @Test
        void shouldReturnMaleForGenderCode5000OrAbove() {
            String maleId = "2001015000084"; // gender code 5000
            assertEquals(ValidateSaId.Gender.MALE, ValidateSaId.getGender(maleId));
        }

        /**
         * Should return UNKNOWN for invalid ID numbers.
         */
        @Test
        void shouldReturnUnknownForInvalidId() {
            String invalidId = "123"; // clearly invalid
            assertEquals(ValidateSaId.Gender.UNKNOWN, ValidateSaId.getGender(invalidId));
        }
    }
}
