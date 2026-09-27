package clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.auth.AuthRequest;
import utils.LoadPropertiesUtils;

import static utils.HttpStatus.OK;

public class AuthClient extends BaseClient {

    private static final String AUTH_PATH = "/auth";

    @Step("POST /auth с кредами '{username}'")
    public Response postAuth(String username, String password) {
        return post(AUTH_PATH, new AuthRequest(username, password));
    }

    @Step("Авторизация пользователем '{username}'")
    public String login(String username, String password) {
        AuthRequest body = new AuthRequest(username, password);
        Response response = post(AUTH_PATH, body);
        response.then().statusCode(OK.code());
        return response.jsonPath().getString("token");
    }

    @Step("Авторизация дефолтным пользователем")
    public String loginAsDefaultUser() {
        return login(
                LoadPropertiesUtils.get("auth.username"),
                LoadPropertiesUtils.get("auth.password")
        );
    }
}