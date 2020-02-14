import org.http4k.client.ApacheClient

fun main() {

    val pubsApi = PubsApi("https://pubcrawlapi.appspot.com/pubcache/", ApacheClient(), ::pubJsonToPubs)
    val beerFinder = BeerFinder(pubsApi)
    val beers = beerFinder.getBeers(uId = "mike", lng = "0.141499", lat = "51.496466", deg = "0.003")
    println(beers)
}

