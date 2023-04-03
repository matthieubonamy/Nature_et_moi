package fr.matthieu.naturecollection

class PlantModel(
    val id: String = "plant0",
    val name: String = "Tulipe",
    val description: String = " Petite description",
    val imageUrl: String = "http://graven.yt/plante.jpg",
    val grow: String = "Faible",
    val water: String = "Moyenne",
    var liked: Boolean = false
)
