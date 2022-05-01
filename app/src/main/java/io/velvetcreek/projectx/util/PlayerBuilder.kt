package io.velvetcreek.projectx.util

class PlayerBuilder {

    private lateinit var status: PlayerStatus
    private lateinit var shirt: Shirt
    private lateinit var shoes: Shoes
    private var badges = ArrayList<Badge>()

    fun setStatus(status: PlayerStatus): PlayerBuilder {
        this.shirt
        return this
    }

    fun setBadges(badge: Badge): PlayerBuilder {
        this.status.numberOfBadges++
        badges.add(badge)
        return this
    }

    fun setShirt(shirt: Shirt): PlayerBuilder {
        this.shirt
        return this
    }

    fun setShoes(shoes: Shoes): PlayerBuilder {
        this.shoes
        return this
    }

    //concrete builder
    fun build(): Player {
        // do some checks
        return Player(status, shirt, shoes)
    }


}

data class PlayerStatus(
    val name: String,
    var numberOfBadges: Int,
    val pokemonCaught: Int,
    val money: Int
)

data class Shirt(val length: Char, val color: String)
data class Shoes(val size: Int, val color: String)
data class Badge(val name: String)
data class Player(val status: PlayerStatus, val shirt: Shirt, val shoes: Shoes)


fun createPlayer(badges: ArrayList<Badge>) {
    var player = PlayerBuilder()
        .setStatus(PlayerStatus("The Rock", 5, 100, 5125))
        .setShirt(Shirt('M', "Gray"))
        .setShoes(Shoes(12, "black"))
        .setBadges((Badge("Thunder")))
}