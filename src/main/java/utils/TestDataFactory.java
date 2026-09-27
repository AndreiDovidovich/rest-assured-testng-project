package utils;

import models.booking.Booking;
import models.booking.BookingDates;
import net.datafaker.Faker;

public final class TestDataFactory {

    private static final Faker FAKER = new Faker();

    private TestDataFactory() {}

    public static Booking randomBooking() {
        return Booking.builder()
                .firstname(FAKER.name().firstName())
                .lastname(FAKER.name().lastName())
                .totalprice(FAKER.number().numberBetween(50, 500))
                .depositpaid(FAKER.bool().bool())
                .bookingdates(BookingDates.builder()
                        .checkin("2025-01-01")
                        .checkout("2025-01-10")
                        .build())
                .additionalneeds(FAKER.food().dish())
                .build();
    }
}