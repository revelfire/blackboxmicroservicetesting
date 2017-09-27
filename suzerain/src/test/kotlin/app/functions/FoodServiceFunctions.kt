package app.functions

import com.raken.test.api.TestBase
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder

object FoodServiceFunctions {

    private val foodServiceSpec = RequestSpecBuilder()
        .setContentType("application/json;charset=UTF-8")
        .setAccept("application/json")
        .build()

    fun getOrder(with:String): String {
        return RestAssured.given()
                .spec(foodServiceSpec).log().all()
                .get("http://${TestBase.foodServiceHost()}/api/order/burger?with=$with")
                .then()
                .statusCode(200)
                .extract().response().asString()
    }

    fun getWindow(): String {
        return RestAssured.given()
                .spec(foodServiceSpec).log().all()
                .get("http://${TestBase.foodServiceHost()}/api/window")
                .then()
                .statusCode(200)
                .extract().response().asString()
    }

}