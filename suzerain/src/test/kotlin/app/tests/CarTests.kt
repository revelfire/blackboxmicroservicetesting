package app.tests

import app.Integration
import app.functions.CarServiceFunctions.isCarReady
import app.functions.CarServiceFunctions.getCars
import app.functions.CarServiceFunctions.putCar
import io.kotlintest.matchers.gt
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.StringSpec

class CarTests : StringSpec() {

    init {

        "get(/cars): Call the car list and get 200" {
            getCars() shouldNotBe null
        }.config(tags = setOf(Integration))

        "put(/cars) and id back" {
            val carId = putCar("BMW", "X3")
            carId shouldBe gt(0)
        }.config(tags = setOf(Integration))

        "get(/ready) should not be true for a new car with no calls to seat/wheels/engine" {
            val carId = putCar("BMW", "X3")
            carId shouldBe gt(0)

            val ready = isCarReady(carId)
            ready shouldBe false

        }.config(tags = setOf(Integration))


    }


}
