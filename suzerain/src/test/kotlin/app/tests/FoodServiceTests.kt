package app.tests

import app.Integration
import app.functions.FoodServiceFunctions
import app.util.objectMapperFactory
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.StringSpec
import io.restassured.path.json.JsonPath

class FoodServiceTests : StringSpec() {

    init {

        "get(/order): Order the food" {

            val orderResponse = FoodServiceFunctions.getOrder("cheese")
            orderResponse shouldNotBe null

        }.config(tags = setOf(Integration))

        "get(/window) Get food at window" {

            val windowResponse = FoodServiceFunctions.getWindow()
            windowResponse shouldNotBe null

            print(windowResponse)

            //Here we use JsonPath in the test rather than the function - we didn't
            // cast to a friendly model because this is a NodeJS service            //Indexing into a list here
            JsonPath.from(windowResponse).using(objectMapperFactory).getBoolean("[0].burger_correct") shouldBe true

        }.config(tags = setOf(Integration))

    }

}