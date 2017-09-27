package app

import org.boon.json.JsonFactory
import spark.Spark.*
import java.util.*


var mapper = JsonFactory.create()
val cars = mutableMapOf<Int, CarState>()

fun main(args: Array<String>) {
    port(8080)
    path("/api") {

        get("/cars") { req, res ->
            mapper.writeValueAsString(cars)
        }

        get("/cars/:carId/ready") { req, res ->

            val carId = req.params(":carId").toInt()
            if (cars.keys.contains(carId)) {
                val car = cars[carId]!!
                if (car.engine.running && car.wheels.lugsTight && car.seatBelt.on) {
                    res.status(200)
                    """
                        {
                            "ready": true
                        }
                    """
                } else {
                    res.status(200)
                    """
                        {
                            "ready": false
                        }
                    """
                }
            } else {
                throw Error("Car not found!")
            }
        }

        put( "/cars") { req, res ->
            val make = req.queryParams("make")
            val model = req.queryParams("model")
            if (make == null || model == null) {

                throw Error("Make and model are both required!")

            }
            val carId = Math.abs(Random().nextInt())

            cars.put(carId, CarState(Car(make,model), Wheels(false), Engine(false), SeatBelt(false)))
            res.status(201)
            """
                {
                    "carId:" $carId
                }
            """
        }

        put( "/cars/:carId/tightlugs") { req, res ->
            val carId = req.params(":carId").toInt()
            if (!cars.keys.contains(carId)) {
                throw Error("Car not found")
            }
            cars[carId]?.let { carState ->
                cars.put(carId, CarState(carState.car, Wheels(true), carState.engine, carState.seatBelt))
            }

            ""

        }
        put( "/cars/:carId/startengine") { req, res ->
            val carId = req.params(":carId").toInt()
            if (!cars.keys.contains(carId)) {
                throw Error("Car not found")
            }
            cars[carId]?.let { carState ->
                cars.put(carId, CarState(carState.car, carState.wheels, Engine(true), carState.seatBelt))
            }

            ""

        }
        put( "/cars/:carId/seatbelt") { req, res ->
            val carId = req.params(":carId").toInt()
            if (!cars.keys.contains(carId)) {
                throw Error("Car not found")
            }
            cars[carId]?.let { carState ->
                cars.put(carId, CarState(carState.car, carState.wheels, carState.engine, SeatBelt(true)))
            }

            ""
        }

    }
}
