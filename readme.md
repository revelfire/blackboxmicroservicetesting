# Blackbox Microservice Testing with Kotlin and RestAssured

This repo exists as a support for the article expressing this form of testing
solution, available here: 


## Car Service
Just for fun, since the article mentions it, we use SparkJava for this service. It's 
only slightly lighter weight than a basic Spring Boot service and it serves the 
purpose. This service only serves as a placeholder here for real things that do
important work.

If SparkJava intrigues you, find more info here: http://sparkjava.com/documentation

You can find the API contract in src/main/kotlin/app/Service.kt

### Build
```commandline
cd car-service
gradle build
```

### Run

If you are running in an IDE just run/debug the Service file main method.

You can also build and run at command line using the gradle application plugin.

```commandline
gradle install installDist
./build/install/car-service/bin/car-service
```
- service is now up and running, listening on 8080

### Test
```commandline
curl localhost:8080/api/cars
```
Should see {}

The whole conversation to create and start the car looks like this:
`curl localhost:8080/api/cars`

```json
{}
```

`curl -X PUT "localhost:8080/api/cars?make=BMW&model=X3"`
```json
{
	"carId:" 451711177
}
```

`curl localhost:8080/api/cars`
```json
[{"car":{"make":"BMW","model":"X3"},"wheels":{"lugsTight":false},"engine":{"running":false},"seatBelt":{"on":false}}]
```

`curl localhost:8080/api/cars/451711177/ready`
```json
{
	"ready": false
}
```

Make it ready...
```commandline
curl -X PUT localhost:8080/api/cars/451711177/tightlugs
curl -X PUT localhost:8080/api/cars/451711177/startengine
curl -X PUT localhost:8080/api/cars/451711177/seatbelt
```

`curl localhost:8080/api/cars/451711177/ready`
```json
{
	"ready": true
}
```


## Food Service
Just for fun this service is in a whole different stack, NodeJS. Why? Because we want
to represent that the solution being demonstrated is purely a REST client, and the
tech stack outside that client is irrelevant.

### Build
```commandline
cd food-service
npm install
```

### Run
```commandline
npm start
```

### Test
You can run quick curl to check this server, e.g. 
`curl localhost:3000/api/order/burger?with=cheese`
or
`curl localhost:3000/api/window/`


## Suzerain

Now to the point of all this - you now have 2 microservices running API's and you 
want a "user journey" to stitch it all together and make sure things really work.

This project is the "Kotlin + Rest Assured for RESTful microservices testing" part 
of all this.

ALL of these tests use HTTP + REST to do their job.

To run all the tests, just
```commandline
cd suzerain
gradle test
```

You should see something like:

```
> Task :test

app.journeys.EntireEscapadeJourney > Feature: Get in the car, prepare the car, and go get food.Scenario: Should get in the car & makeit ready, go to drivethrough, which should give us the food we ask for, like we asked for it, in under a minute PASSED

app.journeys.FoodServiceJourney > Feature: Food service visit.Scenario: Should give us the food we ask for, like we asked for it, in under a minute PASSED

app.journeys.GetCarReadyJourney > Feature: the car.Scenario: when new should not be ready PASSED

app.journeys.GetCarReadyJourney > Feature: the car.Scenario: and should be ready after I do all the things PASSED

app.tests.CarTests > CarTests.get(/cars): Call the car list and get 200 PASSED

app.tests.CarTests > CarTests.put(/cars) and id back PASSED

app.tests.CarTests > CarTests.get(/ready) should not be true for a new car with no calls to seat/wheels/engine PASSED

app.tests.FoodServiceTests > FoodServiceTests.get(/order): Order the food PASSED

app.tests.FoodServiceTests > FoodServiceTests.get(/window) Get food at window PASSED

FeatureSpecTest > Feature: the thingy bob.Scenario: should explode when I touch it PASSED

FeatureSpecTest > Feature: the thingy bob.Scenario: and should do this when I wibble it PASSED

KotlinTestExamplesTest > When calculating the sum of 2 and 2.Then it should return 4 PASSED

KotlinTestExamplesTest > When calculating the multiplication of 2 and 2.Then implement multiplication! PASSED

KotlinTestExamplesTest > When using faker.Then timezone PASSED


BUILD SUCCESSFUL in 8s
3 actionable tasks: 3 executed
```

