package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.LoadPropertiesUtils;

public final class RequestSpecs {

    private RequestSpecs() {}

    public static RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .setBaseUri(LoadPropertiesUtils.get("base.url"))
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .log(LogDetail.URI)
                .build();
    }

    public static ResponseSpecification okResponse() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
}