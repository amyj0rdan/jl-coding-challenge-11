data class Beer(
    val name: String,
    val pubName: String,
    val pubService: String,
    val regularBeer: Boolean
)

data class Pub(
    val name: String,
    val id: Int,
    val branch: String,
    val createTS: String,
    val pubService: String,
    val regularBeers: List<String> = emptyList(),
    val guestBeers: List<String> = emptyList()
)

data class Pubs(
    val pubs: List<Pub>
)