package app.journeys

import app.Journey
import app.functions.CarServiceFunctions
import io.kotlintest.matchers.gt
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.FeatureSpec

class GetCarReadyJourney : FeatureSpec() {
    init {
        feature("the car") {

            scenario("when new should not be ready") {

                val carId = CarServiceFunctions.putCar("TOYOTA", "COROLLA")
                carId shouldBe gt(0)

                val ready = CarServiceFunctions.isCarReady(carId)
                ready shouldBe false

            }.config(tags = setOf(Journey))

            scenario("and should be ready after I do all the things") {
                val carId = CarServiceFunctions.putCar("HYUNDAI", "ELANTRA")
                carId shouldBe gt(0)

                val ready = CarServiceFunctions.isCarReady(carId)
                ready shouldBe false

                CarServiceFunctions.putBelt(carId)
                CarServiceFunctions.putEngine(carId)
                CarServiceFunctions.putWheels(carId)

                val readyNow = CarServiceFunctions.isCarReady(carId)
                readyNow shouldBe true

            }.config(tags = setOf(Journey))
        }
    }
}