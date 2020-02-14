import org.http4k.client.ApacheClient
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request

class PubsApi(private val baseUrl: String, val client: HttpHandler = ApacheClient(), val responseConverter: (String) -> List<Pub>) {
    fun getPubs(uId: String, lng: String, lat: String, deg: String) : List<Pub> {
        val response = client(
            Request(Method.GET, baseUrl)
                .query("uId", uId)
                .query("lng", lng)
                .query("lat", lat)
                .query("deg", deg))

        return responseConverter(response.bodyString())
    }
}