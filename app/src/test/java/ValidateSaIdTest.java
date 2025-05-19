import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidateSaIdTest {

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

    @Nested
    class InvalidIdNumbers {

        @ParameterizedTest
        @ValueSource(strings = {
            "20010148000",      // Too short
            "20010148",         // Too short
            "20010A4800086",    // Non-numeric
            "2013014800086",    // Invalid month (13)
            "2001324800086",    // Invalid day (32)
            "2001014800286",    // Invalid citizenship
            "2001014800087"     // Invalid checksum
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
}
