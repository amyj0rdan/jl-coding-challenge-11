import assertk.assertThat
import assertk.assertions.containsExactly
import org.junit.jupiter.api.Test

internal class ObtainBeersKtTest {

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
        assertThat(pubs.removeDuplicates()).containsExactly(
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

        assertThat(pub.obtainBeers()).containsExactly(
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

        assertThat(pubs.obtainListOfBeers()).containsExactly(
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
}