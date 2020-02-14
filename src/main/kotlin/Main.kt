import org.http4k.client.ApacheClient

fun main(args: Array<String>) {

    val pubsApi = PubsApi("https://pubcrawlapi.appspot.com/pubcache/", ApacheClient(), ::pubJsonToPubs)
    val beerFinder = BeerFinder(pubsApi)
    val beers = beerFinder.getBeers(uId = "mike", lng = args[0], lat = args[1], deg = "0.003")
    println(beerFinder.stringify(beers))
}

// "-0.141499", lat = "51.496466"

