package app

data class Car(val make:String, val model:String)

data class Wheels(val lugsTight:Boolean)
data class Engine(val running:Boolean)
data class SeatBelt(val on:Boolean)
data class CarState(val car:Car, val wheels: Wheels, val engine: Engine, val seatBelt: SeatBelt)



