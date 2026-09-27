package tests;

import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.HttpStatus.OK;

@Epic("Restful Booker API")
@Feature("Booking Filters")
public class BookingFilterTest extends BaseTest {

    @Test(priority = 21, dataProvider = "filterData")
    void filterBookingsTest(String filter, String value) {
        var response = bookingClient.getBookingsFiltered(filter, value);

        assertThat(response.statusCode()).isEqualTo(OK.code());
    }

    @DataProvider(name = "filterData")
    public Object[][] filterData() {
        return new Object[][] {
                { "firstname", "Susan" },
                { "lastname",  "Wilson" },
                { "checkin",   "2024-01-01" },
                { "checkout",  "2024-01-10" }
        };
    }

    @Test(priority = 22)
    void getAllBookingsTest() {
        var response = bookingClient.getAllBookings();

        assertThat(response.statusCode()).isEqualTo(OK.code());
        assertThat(response.jsonPath().getList("bookingid")).isNotEmpty();
    }
}