package tests;

import io.qameta.allure.*;
import models.booking.Booking;
import models.booking.BookingResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestDataFactory;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.HttpStatus.*;

@Epic("Restful Booker API")
@Feature("Booking CRUD")
public class BookingCrudTest extends BaseTest {

    private static Booking testBooking;
    private static int testBookingId;
    private static final String FIRSTNAME_PARAM = "firstname";

    @BeforeClass(alwaysRun = true)
    public void prepareBooking() {
        testBooking = TestDataFactory.randomBooking();

        var response = bookingClient.createBooking(testBooking);
        testBookingId = response.getBookingid();
    }

    @Test(priority = 11, groups = {"smoke"})
    public void createBookingTest() {
        Booking booking = TestDataFactory.randomBooking();
        var response = bookingClient.createBookingWithoutValidation(booking);

        assertThat(response.statusCode()).isEqualTo(OK.code());

        BookingResponse created = response.as(BookingResponse.class);
        assertThat(created.getBookingid()).isNotNull().isPositive();
        assertThat(created.getBooking().getFirstname())
                .isEqualTo(booking.getFirstname());
    }

    @Test(priority = 12)
    public void getBookingByIdTest() {
        var response = bookingClient.getBooking(testBookingId);
        assertThat(response.statusCode()).isEqualTo(OK.code());
    }

    @Test(priority = 13)
    public void updateBookingTest() {
        Booking updated = TestDataFactory.randomBooking();
        var response = bookingClient.updateBooking(testBookingId, updated, token);

        assertThat(response.statusCode()).isEqualTo(OK.code());
    }

    @Test(priority = 14)
    public void patchBookingTest() {
        var updatedName = "PatchedName";
        var response = bookingClient.patchBooking(testBookingId,
                Map.of(FIRSTNAME_PARAM, updatedName), token);

        assertThat(response.statusCode()).isEqualTo(OK.code());
        assertThat(response.as(Booking.class).getFirstname())
                .isEqualTo(updatedName);
    }

    @Test(priority = 15)
    public void deleteBookingTest() {
        var booking = TestDataFactory.randomBooking();
        var response = bookingClient.createBooking(booking);
        var id = response.getBookingid();

        var deleteResponse = bookingClient.deleteBooking(id, token);
        assertThat(deleteResponse.statusCode()).isEqualTo(CREATED.code());

        var getResponse = bookingClient.getBooking(id);
        assertThat(getResponse.statusCode()).isEqualTo(NOT_FOUND.code());
    }

    @Test(priority = 16)
    public void getNotExistingBookingTest() {
        var response = bookingClient.getBooking(9999999);
        assertThat(response.statusCode()).isEqualTo(NOT_FOUND.code());
    }

    @Test(priority = 17)
    public void deleteWithoutTokenTest() {
        var response = bookingClient.deleteBooking(testBookingId, "");
        assertThat(response.statusCode()).isEqualTo(FORBIDDEN.code());
    }
}