package app.util

/**
 * Created by cmathias on 5/2/17.
 */

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.restassured.mapper.factory.Jackson2ObjectMapperFactory
import io.restassured.specification.RequestSender
import io.restassured.specification.RequestSpecification
import io.restassured.specification.ResponseSpecification

interface RestAssuredSupport {
    fun RequestSpecification.When(): RequestSpecification {
        return this.`when`()
    }
    
    fun ResponseSpecification.When(): RequestSender {
        return this.`when`()
    }

}

//http://www.programcreek.com/java-api-examples/index.php?api=com.jayway.restassured.config.RestAssuredConfig

object ObjectMapperConfigurator {
    val objectMapper = ObjectMapper().registerModule(KotlinModule())
    init {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
    }
    fun get():ObjectMapper {
        return objectMapper
    }
}

val objectMapperFactory = object : Jackson2ObjectMapperFactory {

    override fun create(cls: Class<*>?, charset: String?): ObjectMapper {
        return ObjectMapperConfigurator.get()
    }
}
