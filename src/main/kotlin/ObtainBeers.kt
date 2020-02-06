fun List<Pub>.removeDuplicates() = this
    .sortedByDescending { it.createTS }
    .distinctBy { it.id }

fun Pub.obtainRegularBeers() : List<Beer> {
    return this.regularBeers.map { beerName ->
        Beer(
            beerName,
            this.name,
            this.pubService,
            true
        )
    }
}