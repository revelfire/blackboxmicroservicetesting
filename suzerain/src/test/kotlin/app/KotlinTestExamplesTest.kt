import com.github.javafaker.Faker
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.BehaviorSpec
import io.kotlintest.specs.FeatureSpec

class KotlinTestExamplesTest : BehaviorSpec() {

    val faker = Faker()

    init {

        Given("simple calculator") {
            When("calculating the sum of 2 and 2") {
                Then("it should return 4") {

                    val calculator = Calculator()
                    val result = calculator.add(2, 2)
                    result shouldBe 4

                }

            }

            When("calculating the multiplication of 2 and 2") {
                then("implement multiplication!") {
                    val calculator = Calculator()
                    val result = calculator.multiply(2, 2)
                    result shouldBe 4
                }
            }

            When("using faker") {
                then("timezone") {
                    print(faker.address().timeZone())
                }
            }
        }

    }
}

class FeatureSpecTest : FeatureSpec() {
    init {
        feature("the thingy bob") {
            scenario("should explode when I touch it") {
                // test here
            }
            scenario("and should do this when I wibble it") {
                // test heree
            }
        }
    }
}

open class Calculator {
    fun add(vararg x: Int) = x.sum()
    fun substract(x: Int, y: Int) = x - y
    fun multiply(vararg x: Int):Int {
        var total = 1
        x.forEach { x ->
            total = total * x
        }
        return total
    }
    fun divide(x: Int, y: Int): Int {
        if (y == 0) {
            throw IllegalArgumentException()
        }
        return x / y
    }
}