package clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.booking.Booking;
import models.booking.BookingResponse;

import static utils.HttpStatus.OK;

public class BookingClient extends BaseClient {

    private static final String BOOKING_PATH = "/booking";

    @Step("Создать бронирование")
    public Response createBookingWithoutValidation(Booking booking) {
        return post(BOOKING_PATH, booking);
    }

    @Step("Создать бронирование")
    public BookingResponse createBooking(Booking booking) {
        return createBookingWithoutValidation(booking)
                .then()
                .statusCode(OK.code())
                .extract()
                .as(BookingResponse.class);
    }

    @Step("Получить бронирование по id={id}")
    public Response getBooking(int id) {
        return get(BOOKING_PATH + "/" + id);
    }

    @Step("Обновить бронирование id={id} (PUT)")
    public Response updateBooking(int id, Booking booking, String token) {
        return put(BOOKING_PATH + "/" + id, booking, token);
    }

    @Step("Частично обновить бронирование id={id} (PATCH)")
    public Response patchBooking(int id, Object partialBody, String token) {
        return patch(BOOKING_PATH + "/" + id, partialBody, token);
    }

    @Step("Удалить бронирование id={id}")
    public Response deleteBooking(int id, String token) {
        return delete(BOOKING_PATH + "/" + id, token);
    }

    @Step("Получить список всех бронирований")
    public Response getAllBookings() {
        return get(BOOKING_PATH);
    }

    @Step("Получить список бронирований с фильтром {filter}={value}")
    public Response getBookingsFiltered(String filter, String value) {
        return request()
                .queryParam(filter, value)
                .when()
                .get(BOOKING_PATH);
    }
}