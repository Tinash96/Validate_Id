# Validate South African ID Number

This is a Java project that uses **Test-Driven Development (TDD)** to implement and test a validator for South African ID numbers using **JUnit** and **Gradle**.

## 📌 Project Goal

Build a function `isIdNumberValid(String id)` that:
- Returns `true` for valid South African ID numbers
- Returns `false` for invalid ones

A valid SA ID number is a 13-digit string with the format: `YYMMDDSSSSCAZ`, where:
- **YYMMDD**: Date of birth
- **SSSS**: Gender code (0000–4999 = female, 5000–9999 = male)
- **C**: Citizenship (0 = citizen, 1 = permanent resident)
- **Z**: Checksum digit using the Luhn algorithm

## 🛠 Technologies

- Java
- JUnit
- Gradle

## 🚀 How to Run

1. Clone the repository
2. Run the following in your terminal:

```bash
./gradlew test
