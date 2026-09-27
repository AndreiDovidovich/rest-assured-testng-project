package tests;

import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.HttpStatus.OK;

@Epic("Restful Booker API")
@Feature("Auth")
public class AuthTest extends BaseTest {

    @Test(priority = 1, groups = {"smoke"})
    public void loginWithValidCredentialsTest() {
        String token = authClient.login("admin", "password123");

        assertThat(token)
                .as("Auth token")
                .isNotEmpty();
    }

    @Test(priority = 2)
    public void loginWithInvalidCredentialsTest() {
        var response = authClient.postAuth("wrong", "wrong");

        assertThat(response.statusCode()).isEqualTo(OK.code());
        assertThat(response.jsonPath().getString("reason"))
                .isEqualTo("Bad credentials");
    }

    @Test(priority = 3, dataProvider = "invalidCredentials")
    public void loginWithDataProviderTest(String username, String password) {
        var response = authClient.postAuth(username, password);

        assertThat(response.jsonPath().getString("reason"))
                .isEqualTo("Bad credentials");
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][] {
                { "admin",  "wrongpass" },
                { "wrong",  "password123" },
                { "",       "" },
                { "admin",  "" },
                { null,     "password123" }
        };
    }
}