package clients;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import specs.RequestSpecs;

import static io.restassured.RestAssured.given;

public abstract class BaseClient {

    protected RequestSpecification request() {
        return given().spec(RequestSpecs.baseRequest());
    }

    protected RequestSpecification requestWithToken(String token) {
        return given()
                .spec(RequestSpecs.baseRequest())
                .cookie("token", token);
    }

    protected Response get(String path) {
        return request().when().get(path);
    }

    protected Response post(String path, Object body) {
        return request().body(body).when().post(path);
    }

    protected Response put(String path, Object body, String token) {
        return requestWithToken(token).body(body).when().put(path);
    }

    protected Response patch(String path, Object body, String token) {
        return requestWithToken(token).body(body).when().patch(path);
    }

    protected Response delete(String path, String token) {
        return requestWithToken(token).when().delete(path);
    }
}