fun List<Pub>.obtainListOfBeers() : List<Beer> {
    return this
        .removeDuplicates()
        .sortedBy { it.name }
        .flatMap { pub ->
        pub.obtainBeers()
    }
}

fun List<Pub>.removeDuplicates() = this
    .sortedByDescending { it.createTS }
    .distinctBy { it.id }

fun Pub.obtainBeers() : List<Beer> {
    val mapOfBeerToRegularOrGuest = (
            this.regularBeers.map { it to true }
            ) + (
            this.guestBeers.map { it to false }
            )

    return mapOfBeerToRegularOrGuest.map { (beerName, isRegular) ->
        Beer(
            beerName,
            this.name,
            this.pubService,
            isRegular
        )
    }
}