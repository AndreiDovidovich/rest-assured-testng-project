package tests;

import clients.AuthClient;
import clients.BookingClient;
import io.qameta.allure.restassured.AllureRestAssured;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.filters;

public abstract class BaseTest {

    protected AuthClient authClient;
    protected BookingClient bookingClient;
    protected String token;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        filters(new AllureRestAssured());

        authClient = new AuthClient();
        bookingClient = new BookingClient();

        token = authClient.loginAsDefaultUser();
    }
}