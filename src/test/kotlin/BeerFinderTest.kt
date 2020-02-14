import assertk.assertThat
import assertk.assertions.containsExactly
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test

internal class BeerFinderTest {

    private val pubsApi = mock<PubsApi>()
    private val beerFinder = BeerFinder(pubsApi)


@Test
fun `removeDuplicates removes duplicate pubs by id and latest created timestamp`() {
    val pubs = listOf(
        Pub(
            "Wetherspoons",
            15953,
            "WLD",
            "2020-01-20 08:52:53",
            "someURL",
            listOf("Fuller's London Pride", "Greene King IPA"),
            listOf("Adnams --varies--", "Windsor and Eton --varies--")
        ),
        Pub(
            "Brass Monkey",
            16193,
            "WLD",
            "2020-01-03 17:52:21",
            "someUrl",
            listOf("Sharp#039;s Doom Bar", "Timothy Taylor Landlord"),
            emptyList()
        ),
        Pub(
            "Brass Monkey",
            16193,
            "WLD",
            "2020-01-05 17:52:21",
            "someUrl",
            listOf("Sharp#039;s Doom Bar", "Timothy Taylor Landlord"),
            emptyList()
        )
    )
    assertThat(beerFinder.removeDuplicates(pubs)).containsExactly(
        Pub(
            "Wetherspoons",
            15953,
            "WLD",
            "2020-01-20 08:52:53",
            "someURL",
            listOf("Fuller's London Pride", "Greene King IPA"),
            listOf("Adnams --varies--", "Windsor and Eton --varies--")
        ),
        Pub(
            "Brass Monkey",
            16193,
            "WLD",
            "2020-01-05 17:52:21",
            "someUrl",
            listOf("Sharp#039;s Doom Bar", "Timothy Taylor Landlord"),
            emptyList()
        )
    )
}

@Test
fun `obtainBeers returns list of both regular and guest beers from a pub`() {
    val pub = Pub(
        "Wetherspoons",
        15953,
        "WLD",
        "2020-01-20 08:52:53",
        "someURL",
        listOf("Fuller's London Pride", "Greene King IPA"),
        listOf("Sharp's Doom Bar", "Timothy Taylor Landlord")
    )

    assertThat(beerFinder.obtainBeers(pub)).containsExactly(
        Beer(
            "Fuller's London Pride",
            "Wetherspoons",
            "someURL",
            true
        ),
        Beer(
            "Greene King IPA",
            "Wetherspoons",
            "someURL",
            true
        ),
        Beer(
            "Sharp's Doom Bar",
            "Wetherspoons",
            "someURL",
            false
        ),
        Beer(
            "Timothy Taylor Landlord",
            "Wetherspoons",
            "someURL",
            false
        )
    )

}

@Test
fun `obtainListOfBeers return list of beers from a list of pubs`() {
    val pubs = listOf(
        Pub(
            "Wetherspoons",
            15953,
            "WLD",
            "2020-01-20 08:52:53",
            "someURL",
            listOf("Fuller's London Pride"),
            listOf("Sharp's Doom Bar")
        ),
        Pub(
            "Willow Walk",
            15951,
            "WLD",
            "2020-01-03 17:52:05",
            "anotherURL",
            listOf("Fuller's London Pride"),
            listOf("Beavertown Neck Oil")
        )
    )

    assertThat(beerFinder.obtainListOfBeers(pubs)).containsExactly(
        Beer(
            "Fuller's London Pride",
            "Wetherspoons",
            "someURL",
            true
        ),
        Beer(
            "Sharp's Doom Bar",
            "Wetherspoons",
            "someURL",
            false
        ),
        Beer(
            "Fuller's London Pride",
            "Willow Walk",
            "anotherURL",
            true
        ),
        Beer(
            "Beavertown Neck Oil",
            "Willow Walk",
            "anotherURL",
            false
        )
    )
}

@Test
fun `obtainListOfBeers return list of beers from a list of pubs sorted by pub name alphabetically`() {
    val pubs = listOf(
        Pub(
            "Zetherspoons",
            15953,
            "WLD",
            "2020-01-20 08:52:53",
            "someURL",
            listOf("Fuller's London Pride"),
            listOf("Sharp's Doom Bar")
        ),
        Pub(
            "Willow Walk",
            15951,
            "WLD",
            "2020-01-03 17:52:05",
            "anotherURL",
            listOf("Fuller's London Pride"),
            listOf("Beavertown Neck Oil")
        )
    )
    assertThat(beerFinder.obtainListOfBeers(pubs)).containsExactly(
        Beer(
            "Fuller's London Pride",
            "Willow Walk",
            "anotherURL",
            true
        ),
        Beer(
            "Beavertown Neck Oil",
            "Willow Walk",
            "anotherURL",
            false
        ),
        Beer(
            "Fuller's London Pride",
            "Zetherspoons",
            "someURL",
            true
        ),
        Beer(
            "Sharp's Doom Bar",
            "Zetherspoons",
            "someURL",
            false
        )
    )
}

@Test
fun `obtainListOfBeers removes duplicate pubs and uses latest information for a pub`() {
    val pubs = listOf(
        Pub(
            "Willow Walk",
            15951,
            "WLD",
            "2020-01-03 17:52:05",
            "anotherURL",
            listOf("Fuller's London Pride"),
            listOf("Sharp's Doom Bar")
        ),
        Pub(
            "Willow Walk",
            15951,
            "WLD",
            "2020-02-03 17:52:05",
            "anotherURL",
            listOf("Stella Artois"),
            listOf("Beavertown Neck Oil")
        )
    )
    val beers = beerFinder.obtainListOfBeers(pubs)

    assertThat(beers).containsExactly(
        Beer(
            "Stella Artois",
            "Willow Walk",
            "anotherURL",
            true
        ),
        Beer(
            "Beavertown Neck Oil",
            "Willow Walk",
            "anotherURL",
            false
        )
    )
}


@Test
fun `getBeers returns a list of beers`() {
    val listOfPubs = listOf(
        Pub(
            "Brass Monkey",
            16193,
            "WLD",
            "2020-01-03 17:52:21",
            "someUrl",
            listOf("Sharp's Doom Bar", "Timothy Taylor Landlord"),
            emptyList()
        )
    )
    whenever(pubsApi.getPubs("name", "1234", "5678","0.03")).thenReturn(listOfPubs)
    val beers = beerFinder.getBeers("name", "1234", "5678", "0.03")
    assertThat(beers).containsExactly(
        Beer(
            "Sharp's Doom Bar",
            "Brass Monkey",
            "someUrl",
            true
        ),
        Beer(
            "Timothy Taylor Landlord",
            "Brass Monkey",
            "someUrl",
            true
        )
    )
}


//    @Test
//    fun mockingWithoutMockito() {
//
//        val baseURl = "www.something.com"
//        val client : HttpHandler = { request ->
//            assertThat(request.uri.host + request.uri.path).isEqualTo(baseURl)
//            assertThat(request.query("uId")).isEqualTo("mike")
//            // more assertions
//            Response(OK).body("some string")
//        }
//        val responseConvertor: (String) -> List<Pub> = {
//            json -> assertThat(json).isEqualTo("some string")
//            listOfPubs
//        }
//
//        val pubsApi = PubsApi(baseURl, client, responseConvertor)
//
//        assertThat(pubsApi.getPubs("1234", "5678")).isEqualTo(Pub("blah"))
//
//    }
}