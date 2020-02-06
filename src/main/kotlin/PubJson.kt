import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

object PubJson {
    val mapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE)
}

fun pubJsonToPubs(json: String) = PubJson.mapper.readValue<Pubs>(json).pubs

