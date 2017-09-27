package app.journeys

import app.functions.FoodServiceFunctions
import com.raken.test.api.Journey
import com.raken.test.api.util.objectMapperFactory
import io.kotlintest.matchers.lt
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.FeatureSpec
import io.restassured.path.json.JsonPath

class FoodServiceJourney : FeatureSpec() {
    init {
        feature("Food service visit") {

            scenario("Should give us the food we ask for, like we asked for it, in under a minute") {

                val orderResponse = FoodServiceFunctions.getOrder("cheese")
                orderResponse shouldNotBe null
                val windowResponse = FoodServiceFunctions.getWindow()
                windowResponse shouldNotBe null
                JsonPath.from(windowResponse).using(objectMapperFactory).getBoolean("[0].burger_correct") shouldBe true
                JsonPath.from(windowResponse).using(objectMapperFactory).getLong("[0].time_taken") shouldBe lt(60000)

            }.config(tags = setOf(Journey))

        }
    }
}