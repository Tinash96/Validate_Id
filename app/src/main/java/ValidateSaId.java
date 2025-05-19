import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Utility class to validate South African ID numbers.
 */
public class ValidateSaId {

    /**
     * Enumeration to represent Gender.
     */
    public enum Gender {
        MALE, FEMALE, UNKNOWN
    }

    /**
     * Validates the South African ID number.
     * Checks format, birth date, gender code, citizenship, and Luhn checksum.
     *
     * @param idNumber the 13-digit SA ID number
     * @return true if the ID number is valid; false otherwise
     */
    public static boolean isIdNumberValid(String idNumber) {
        if (!isProperFormat(idNumber)) return false;
        if (!isValidBirthDate(idNumber)) return false;
        if (!isValidGenderCode(idNumber)) return false;
        if (!isValidCitizenship(idNumber)) return false;
        return isValidLuhnChecksum(idNumber);
    }

    /**
     * Checks if the ID number is non-null and exactly 13 digits.
     *
     * @param idNumber the ID number string
     * @return true if properly formatted; false otherwise
     */
    private static boolean isProperFormat(String idNumber) {
        return idNumber != null && idNumber.matches("\\d{13}");
    }

    /**
     * Validates the birth date encoded in the ID number (YYMMDD).
     * Uses century cutoff: years > 50 treated as 1900s, else 2000s.
     *
     * @param idNumber the SA ID number
     * @return true if the date is valid; false otherwise
     */
    private static boolean isValidBirthDate(String idNumber) {
        try {
            int year = Integer.parseInt(idNumber.substring(0, 2));
            int month = Integer.parseInt(idNumber.substring(2, 4));
            int day = Integer.parseInt(idNumber.substring(4, 6));
            int fullYear = (year > 50 ? 1900 + year : 2000 + year);
            LocalDate.of(fullYear, month, day);
            return true;
        } catch (DateTimeException | NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates the gender code section (positions 7–10) is a number from 0000 to 9999.
     *
     * @param idNumber the SA ID number
     * @return true if the gender code is in range; false otherwise
     */
    private static boolean isValidGenderCode(String idNumber) {
    if (idNumber == null || idNumber.length() < 10) return false;
    try {
        int genderCode = Integer.parseInt(idNumber.substring(6, 10));
        return genderCode >= 0 && genderCode <= 9999;
    } catch (NumberFormatException e) {
        return false;
    }
}

/**
 * Returns the gender based on the gender code portion of the ID number.
 * 0000–4999 = Female, 5000–9999 = Male.
 *
 * @param idNumber the SA ID number
 * @return Gender enum: MALE, FEMALE, or UNKNOWN
 */
public static Gender getGender(String idNumber) {
    if (!isProperFormat(idNumber)) return Gender.UNKNOWN;
    try {
        int genderCode = Integer.parseInt(idNumber.substring(6, 10));
        return (genderCode >= 5000) ? Gender.MALE : Gender.FEMALE;
    } catch (NumberFormatException e) {
        return Gender.UNKNOWN;
    }
}


    /**
     * Validates the citizenship digit:
     * 0 = South African citizen, 1 = permanent resident.
     *
     * @param idNumber the SA ID number
     * @return true if citizenship digit is valid; false otherwise
     */
    private static boolean isValidCitizenship(String idNumber) {
        try {
            int citizenshipDigit = Integer.parseInt(idNumber.substring(10, 11));
            return citizenshipDigit == 0 || citizenshipDigit == 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates the ID number using the Luhn algorithm tailored for South African IDs.
     *
     * Steps:
     * 1. Sum digits at odd positions (1st, 3rd, ..., 11th)
     * 2. Concatenate even-position digits (2nd, 4th, ..., 12th), multiply the number by 2
     * 3. Add digits of the result in step 2
     * 4. Total the results from step 1 and 3, calculate the check digit
     * 5. Compare with the last digit (13th digit)
     *
     * @param idNumber the SA ID number
     * @return true if checksum is valid; false otherwise
     */
    private static boolean isValidLuhnChecksum(String idNumber) {
        int sumOdd = 0;
        int sumEven = 0;

        // Step 1: Add digits in odd positions (0-based index: 0, 2, 4, ..., 10)
        for (int i = 0; i < 12; i += 2) {
            sumOdd += Character.getNumericValue(idNumber.charAt(i));
        }

        // Step 2: Concatenate even-position digits and multiply by 2
        StringBuilder evenDigits = new StringBuilder();
        for (int i = 1; i < 12; i += 2) {
            evenDigits.append(idNumber.charAt(i));
        }

        int evenNumber = Integer.parseInt(evenDigits.toString()) * 2;

        // Step 3: Sum the digits of the multiplied even number
        while (evenNumber > 0) {
            sumEven += evenNumber % 10;
            evenNumber /= 10;
        }

        // Step 4: Total sum and derive the check digit
        int total = sumOdd + sumEven;
        int calculatedCheckDigit = (10 - (total % 10)) % 10;

        // Step 5: Compare with actual check digit (13th digit)
        int actualCheckDigit = Character.getNumericValue(idNumber.charAt(12));
        return calculatedCheckDigit == actualCheckDigit;
    }
}
