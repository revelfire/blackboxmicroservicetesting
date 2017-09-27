package app.functions

import app.CarState
import com.raken.test.api.TestBase
import com.raken.test.api.util.objectMapperFactory
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.path.json.JsonPath

object CarServiceFunctions {

    private val carServiceSpec = RequestSpecBuilder()
        .setContentType("application/json;charset=UTF-8")
        .setAccept("application/json")
        .build()

    fun getCars(): List<CarState> {
        val response: String = RestAssured
                .given()
                .spec(carServiceSpec).log().all()
                .get("http://${TestBase.carServiceHost()}/api/cars")
                .then()
                .statusCode(200)
                .extract().response().asString()
        return JsonPath.from(response).using(objectMapperFactory).getList("", CarState::class.java)
    }

    fun isCarReady(carId:Int): Boolean {
        val response: String = RestAssured
                .given()
                .spec(carServiceSpec).log().all()
                .get("http://${TestBase.carServiceHost()}/api/cars/$carId/ready")
                .then()
                .statusCode(200)
                .extract().response().asString()
        return JsonPath.from(response).using(objectMapperFactory).getBoolean("ready")
    }

    fun putCar(make:String, model:String): Int {
        val response: String = RestAssured
                .given()
                .spec(carServiceSpec).log().all()
                .put("http://${TestBase.carServiceHost()}/api/cars?make=$make&model=$model")
                .then()
                .statusCode(201)
                .extract().response().asString()
        print(response)
        return JsonPath.from(response).using(objectMapperFactory).getInt("carId")
    }


    fun putWheels(carId:Int) {
        val response: String = RestAssured
                .given()
                .spec(carServiceSpec).log().all()
                .put("http://${TestBase.carServiceHost()}/api/cars/$carId/tightlugs")
                .then()
                .statusCode(200)
                .extract().response().asString()
    }

    fun putBelt(carId:Int) {
        val response: String = RestAssured
                .given()
                .spec(carServiceSpec).log().all()
                .put("http://${TestBase.carServiceHost()}/api/cars/$carId/seatbelt")
                .then()
                .statusCode(200)
                .extract().response().asString()
    }

    fun putEngine(carId:Int) {
        val response: String = RestAssured
                .given()
                .spec(carServiceSpec).log().all()
                .put("http://${TestBase.carServiceHost()}/api/cars/$carId/startengine")
                .then()
                .statusCode(200)
                .extract().response().asString()
    }


}