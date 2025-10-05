package org.lautaropastorino.poc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.time.Month.APRIL;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JUNE;
import static java.time.Month.NOVEMBER;
import static java.time.Month.SEPTEMBER;

public class TransactionBuilder {
    public Transactions buildAuthorizedTransaction() {
        return Transactions.newBuilder()
                .setEstablishmentId(String.valueOf(generateRandomNumber(8)))
                .setAuthorizationDate(generateRandomDate(2024, 1, 1, 2025, 12, 31))
                .setTransactionAmount(generateRandomAmount(1000, 100_000))
                .setOtherField("ABC")
                .setAnotherField("123")
                .setEvenAnotherField("AA11BB22")
                .build();
    }

    public static long generateRandomNumber(int digitLength) {
        long lowerBound = (long) Math.pow(10, digitLength - 1);
        long upperBound = (long) Math.pow(10, digitLength);

        return ThreadLocalRandom.current().nextLong(lowerBound, upperBound);
    }

    public static double generateRandomAmount(double min, double max) {
        double randomValue = ThreadLocalRandom.current().nextDouble(min, max);
        return Math.round(randomValue * 100.0) / 100.0;
    }

    public static String generateRandomDate(int minYear, int minMonth, int minDay, int maxYear, int maxMonth, int maxDay) {
        int year = ThreadLocalRandom.current().nextInt(minYear, maxYear + 1);
        int month = ThreadLocalRandom.current().nextInt(minMonth, maxMonth + 1);
        int day = ThreadLocalRandom.current().nextInt(minDay, maxDay + 1);

        day = month == FEBRUARY.getValue() && day > 28 ? 28 : day;
        day = List.of(APRIL.getValue(), JUNE.getValue(), SEPTEMBER.getValue(), NOVEMBER.getValue()).contains(month) &&
                day > 30 ? 30 : day;

        return LocalDate.of(year, month, day).format(DateTimeFormatter.ISO_DATE);
    }
}
