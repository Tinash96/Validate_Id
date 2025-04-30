import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ValidateSaIdTest {

    @Test
    void testValidIdNumbers() {
        assertTrue(ValidateSaId.isIdNumberValid("2001014800086"));
        assertTrue(ValidateSaId.isIdNumberValid("2909035800085"));
    }


    @Test
    void shouldReturnFalseForShortId() {
        assertFalse(ValidateSaId.isIdNumberValid("20010148000"));
    }

    @Test
    void shouldReturnFalseForLongId() {
        assertFalse(ValidateSaId.isIdNumberValid("20010148"));
    }

    @Test
    void testNonNumericCharacters() {
        assertFalse(ValidateSaId.isIdNumberValid("20010A4800086"));
    }




    @Test
    void shouldReturnFalseForInvalidMonth() {
        assertFalse(ValidateSaId.isIdNumberValid("2013014800086")); // Month 13 invalid
    }

    @Test
    void shouldReturnFalseForInvalidDay() {
        assertFalse(ValidateSaId.isIdNumberValid("2001324800086")); // Day 32 invalid
    }

    @Test
    void shouldReturnFalseForInvalidCitizenship() {
        assertFalse(ValidateSaId.isIdNumberValid("2001014800286")); // Citizenship '2' invalid
    }

    @Test
    void shouldReturnFalseForInvalidChecksum() {
        assertFalse(ValidateSaId.isIdNumberValid("2001014800087")); // Checksum invalid
    }
}
