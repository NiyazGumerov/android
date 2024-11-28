package ru.itis.myapplication

import kotlin.random.Random

object InformationRepository {
    val informations: MutableList<Information> = mutableListOf(
        Information(
            text = "Led Zeppelin",
            image = R.drawable.led_zeppelin,
            description = "this is the best description in the world"
        ),
        Information(
            text = "Led Zeppelin",
            image = R.drawable.led_zeppelin,
            description = "this is the best description in the world"
        ),
        Information(
            text = "Led Zeppelin",
            image = R.drawable.led_zeppelin,
            description = "this is the best description in the world"
        ),
        Information(
            text = "Led Zeppelin",
            image = R.drawable.led_zeppelin,
            description = "this is the best description in the world"
        ),
        Information(
            text = "The Doors",
            image = R.drawable.the_doors,
            description = "this is the best description in the world"
        ),
        Information(
            text = "The Doors",
            image = R.drawable.the_doors,
            description = "this is the best description in the world"
        ),
        Information(
            text = "The Doors",
            image = R.drawable.the_doors,
            description = "this is the best description in the world"
        ),
        Information(
            text = "The Doors",
            image = R.drawable.the_doors,
            description = "this is the best description in the world"
        ),
        Information(
            text = "Daft Punk",
            image = R.drawable.daft_punk,
            description = "this is the best description in the world"
        ),
        Information(
            text = "Black Sabbath",
            image = R.drawable.black_sabbath,
            description = "this is the best description in the world"
        ),
        Information(
            text = "Black Sabbath",
            image = R.drawable.black_sabbath,
            description = "this is the best description in the world"
        ),
        Information(
            text = "Black Sabbath",
            image = R.drawable.black_sabbath,
            description = "this is the best description in the world"
        ),
        Information(
            text = "Black Sabbath",
            image = R.drawable.black_sabbath,
            description = "this is the best description in the world"
        ),
        Information(
            text = "Black Sabbath",
            image = R.drawable.black_sabbath,
            description = "this is the best description in the world"
        ),
        Information(
            text = "Queen",
            image = R.drawable.queen,
            description = "this is the best description in the world"
        ),
        Information(
            text = "Queen",
            image = R.drawable.queen,
            description = "this is the best description in the world"
        ),
        Information(
            text = "Queen",
            image = R.drawable.queen,
            description = "this is the best description in the world"
        ),
        Information(
            text = "Queen",
            image = R.drawable.queen,
            description = "this is the best description in the world"
        ),
        Information(
            text = "AC/DC",
            image = R.drawable.ac_dc,
            description = "this is the best description in the world"
        ),
        Information(
            text = "AC/DC",
            image = R.drawable.ac_dc,
            description = "this is the best description in the world"
        ),
        Information(
            text = "AC/DC",
            image = R.drawable.ac_dc,
            description = "this is the best description in the world"
        ),
        Information(
            text = "AC/DC",
            image = R.drawable.ac_dc,
            description = "this is the best description in the world"
        ),
        Information(
            text = "50 Cent",
            image = R.drawable.fifty_cent,
            description = "this is the best description in the world"
        ),
        Information(
            text = "50 Cent",
            image = R.drawable.fifty_cent,
            description = "this is the best description in the world"
        ),
        Information(
            text = "50 Cent",
            image = R.drawable.fifty_cent,
            description = "this is the best description in the world"
        ),
    )
    val recyclerItems = mutableListOf<RecyclerItem>(Buttons).apply { this.addAll(informations) }

    private val texts = listOf(
        "Pirate Hunter", "Black Leg", "Straw Hat", "Fire Fist", "Flame Emperor"
    )
    private val images = listOf(
        R.drawable.roronoa_zoro, R.drawable.vinsmoke_sanji, R.drawable.monkey_d_luffy_fifth_gear,
        R.drawable.portgas_d_ace, R.drawable.sabo
    )
    private val descriptions = listOf(
        "description one one one one one one one one one one one one one one",
        "description two two two two two two two two two two two two two two two v",
        "description three three threev three v three v three v threethreethree three",
        "description four four four four four four four four four four four four ",
        "description one one one one onfour four four four four ",
        "description one one one ofour four four four four four one one one one one one one",

        )

    fun addRandomInformationToList() {
        val newInformation = Information(texts.random(), images.random(), descriptions.random())
        val randomIndex = Random.nextInt(
            from = 1,
            until = recyclerItems.size + 1
        )
        recyclerItems.add(randomIndex, newInformation)
    }

    fun deleteRandomInformation() {
        if (recyclerItems.size == 1) return

        val randomIndex = Random.nextInt(
            from = 1,
            until = recyclerItems.size
        )
        recyclerItems.removeAt(randomIndex)
    }

}
