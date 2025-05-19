import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Utility class to validate South African ID numbers.
 */
public class ValidateSaId {

    /**
     * Validates the South African ID number.
     * Checks for correct format, valid birth date, gender, citizenship, and checksum.
     *
     * @param idNumber the 13-digit SA ID number
     * @return true if valid; false otherwise
     */
    public static boolean isIdNumberValid(String idNumber) {
        if (!isProperFormat(idNumber)) return false;
        if (!isValidBirthDate(idNumber)) return false;
        if (!isValidGenderCode(idNumber)) return false;
        if (!isValidCitizenship(idNumber)) return false;
        return isValidLuhnChecksum(idNumber);
    }

    /** Check if ID number is non-null and exactly 13 digits */
    private static boolean isProperFormat(String idNumber) {
        return idNumber != null && idNumber.matches("\\d{13}");
    }

    /** Validate birth date encoded in the ID number */
    private static boolean isValidBirthDate(String idNumber) {
        int year = Integer.parseInt(idNumber.substring(0, 2));
        int month = Integer.parseInt(idNumber.substring(2, 4));
        int day = Integer.parseInt(idNumber.substring(4, 6));
        int fullYear = (year > 50 ? 1900 + year : 2000 + year);

        try {
            LocalDate.of(fullYear, month, day);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /** Validate gender code (0000 to 9999) */
    private static boolean isValidGenderCode(String idNumber) {
        int genderCode = Integer.parseInt(idNumber.substring(6, 10));
        // You can refine this if business logic requires a gender range check here
        return genderCode >= 0 && genderCode <= 9999;
    }

    /** Validate citizenship digit: 0 for SA citizen, 1 for permanent resident */
    private static boolean isValidCitizenship(String idNumber) {
        int citizenshipDigit = Integer.parseInt(idNumber.substring(10, 11));
        return citizenshipDigit == 0 || citizenshipDigit == 1;
    }

    /** Validate the ID number using the Luhn checksum algorithm */
    private static boolean isValidLuhnChecksum(String idNumber) {
        int sum = 0;
        boolean alternate = false;

        for (int i = idNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(idNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }
}
