package app

import app.util.objectMapperFactory
import io.kotlintest.ProjectConfig
import io.kotlintest.Tag
import io.restassured.RestAssured
import io.restassured.config.ObjectMapperConfig
import io.restassured.config.RestAssuredConfig
import java.io.FileInputStream
import java.util.*

object QuickRun : Tag()
object Integration : Tag()    //Represents a single unit of testing an API endpoint (various scenarios possibly)
object Journey : Tag()        //Represents a group of tests that mimic a user behavior/journey through the app

object TestBase {

    var props:Properties = Properties();

    init {
        props = readProperties()
        validateProperties()
    }

    fun carServiceHost():String = "${getProperty("service.car-service.url")}:${getProperty("service.car-service.port")}"
    fun foodServiceHost():String = "${getProperty("service.food-service.url")}:${getProperty("service.food-service.port")}"

    fun validateProperties() {

    }

    fun getPropertyAsInt(key:String):Int {
        val value = getProperty(key)
        value?.let {
            return value.toInt()
        }
        throw RuntimeException("Property ${key} not found!")
    }

    fun getPropertyAsBoolean(key:String):Boolean {
        val value = getProperty(key)
        value?.let {
            return value.toBoolean()
        }
        throw RuntimeException("Property ${key} not found!")
    }

    fun getProperty(key:String):String? {
        System.getProperty(key)?.let {
            return System.getProperty(key)
        }
        return props[key] as String
    }

    fun getRequiredProperty(key:String):String? {
        System.getProperty(key)?.let {
            return System.getProperty(key)
        }
        props[key]?.let {
            return props[key] as String
        }
        throw RuntimeException("Property ${key} marked as required but not present!")
    }

    fun readProperties():Properties = Properties().apply {
        FileInputStream("src/test/resources/testing.conf").use { fis ->
            load(fis)
        }
    }

}

//Kotlintest interceptors that fire before the entire suite, and after.
object GlobalTestSuiteInitializer : ProjectConfig() {

    private var started: Long = 0

    override fun beforeAll() {
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(objectMapperFactory))
        started = System.currentTimeMillis()
    }

    override fun afterAll() {
        val time = System.currentTimeMillis() - started
        println("overall time [ms]: " + time)
    }
}