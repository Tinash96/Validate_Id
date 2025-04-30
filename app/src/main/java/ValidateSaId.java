import java.time.DateTimeException;
import java.time.LocalDate;

public class ValidateSaId {

    public static boolean isIdNumberValid(String idNumber) {
        if (idNumber == null || idNumber.length() != 13 || !idNumber.matches("\\d{13}")) {
            return false;
        }

        // Validate date of birth (YYMMDD)
        int year = Integer.parseInt(idNumber.substring(0, 2));
        int month = Integer.parseInt(idNumber.substring(2, 4));
        int day = Integer.parseInt(idNumber.substring(4, 6));

        int fullYear = (year >= 0 && year <= 99) ? (year > 50 ? 1900 + year : 2000 + year) : -1;
        try {
            LocalDate.of(fullYear, month, day);
        } catch (DateTimeException e) {
            return false;
        }

        // Validate gender code
        int genderCode = Integer.parseInt(idNumber.substring(6, 10));
        if (genderCode < 0 || genderCode > 9999) {
            return false;
        }
        if (month < 1 || month > 12) {
            return false;
        }

        if (day < 1 || day > 31) {
            return false;
        }

        // Validate citizenship digit (0 or 1)
        int citizenship = Integer.parseInt(idNumber.substring(10, 11));
        if (citizenship != 0 && citizenship != 1) {
            return false;
        }

         // Validate checksum with Luhn algorithm
        return isValidLuhn(idNumber);

    }

    private static boolean isValidLuhn(String idNumber) {
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

