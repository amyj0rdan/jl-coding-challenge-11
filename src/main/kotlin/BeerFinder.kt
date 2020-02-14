class BeerFinder(private val pubsApi: PubsApi) {

    fun stringify(beers: List<Beer>): String {
        return beers.joinToString(separator = "\n") { beer ->
            "Name: ${beer.name}\nPub: ${beer.pubName}\nPub Service: ${beer.pubService}\nRegular beer?: ${beer.regularBeer}\n"
        }
    }

    fun getBeers(uId: String, lng: String, lat: String, deg: String): List<Beer> {
        val pubs = pubsApi.getPubs(uId, lng, lat, deg)

        return obtainListOfBeers(pubs)
    }

    fun obtainListOfBeers(pubs: List<Pub>) : List<Beer> {
        return removeDuplicates(pubs)
            .sortedBy { it.name }
            .flatMap { pub ->
                obtainBeers(pub)
            }
    }

    fun removeDuplicates(pubs: List<Pub>) = pubs
        .sortedByDescending { it.createTS }
        .distinctBy { it.id }

    fun obtainBeers(pub: Pub) : List<Beer> {
        val mapOfBeerToRegularOrGuest = (
                pub.regularBeers.map { it to true }
                ) + (
                pub.guestBeers.map { it to false }
                )

        return mapOfBeerToRegularOrGuest.map { (beerName, isRegular) ->
            Beer(
                beerName,
                pub.name,
                pub.pubService,
                isRegular
            )
        }
    }
}