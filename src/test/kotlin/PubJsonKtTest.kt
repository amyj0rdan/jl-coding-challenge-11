import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class PubJsonKtTest {

    @Test
    fun `get list of pubs from Json`() {
        val json = PubJsonKtTest::class.java.getResource("/pub-api-response.json").readText()

        assertThat(pubJsonToPubs(json)).isEqualTo(
            listOf(
                Pub(
                    "Wetherspoons",
                    15953,
                    "WLD",
                    "2020-01-20 08:52:53",
                    "https://pubcrawlapi.appspot.com/pub/?v=1&id=15953&branch=WLD&uId=mike&pubs=&realAle=&memberDiscount=&town=London",
                    listOf("Fuller#039;s London Pride", "Greene King IPA"),
                    listOf("Adnams --varies--", "Windsor and Eton --varies--")
                ),
                Pub(
                    "Brass Monkey",
                    16193,
                    "WLD",
                    "2020-01-03 17:52:21",
                    "https://pubcrawlapi.appspot.com/pub/?v=1&id=16193&branch=WLD&uId=mike&pubs=yes&realAle=yes&memberDiscount=no&town=London",
                    listOf("Sharp#039;s Doom Bar", "Timothy Taylor Landlord"),
                    emptyList()
                ),
                Pub(
                    "Willow Walk",
                    15951,
                    "WLD",
                    "2020-01-03 17:52:05",
                    "https://pubcrawlapi.appspot.com/pub/?v=1&id=15951&branch=WLD&uId=mike&pubs=yes&realAle=yes&memberDiscount=no&town=London",
                    listOf("Fuller#039;s London Pride", "Greene King Abbot", "Greene King IPA"),
                    emptyList()
                )
            )
        )
    }
}