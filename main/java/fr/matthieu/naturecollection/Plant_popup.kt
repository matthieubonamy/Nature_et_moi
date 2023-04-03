package fr.matthieu.naturecollection

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.matthieu.naturecollection.adapter.PlantAdapter
import org.w3c.dom.Text

class Plant_popup(
    private val adapter: PlantAdapter,
    private val currentPlant : PlantModel
    ) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_plants_details)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupStarButton()
    }
    private fun updateStar(button: ImageView) {
        if (currentPlant.liked) {
            button.setImageResource(R.drawable.ic_star)
        }
        else {
            button.setImageResource(R.drawable.ic_unstar)
        }
    }

    private fun setupStarButton() {
        // recuppere le composant
        val starButton =  findViewById<ImageView>(R.id.star_button)
            //mettre en favoris
        updateStar(starButton)

        //interaction
        starButton.setOnClickListener {
            currentPlant.liked = ! currentPlant.liked
            val repo = PlantRepository()
            repo.updatePlant(currentPlant)
            updateStar(starButton)
        }
    }

    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener{
            // supprimer la plante
            val repo = PlantRepository()
            repo.deletePlant(currentPlant)
            dismiss()
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener{
            //fermer lapopup
            dismiss()
        }
    }

    private fun setupComponents() {
        //actualiser l(image dela plante
        val plantImage= findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentPlant.imageUrl)).into(plantImage)

        //actualiser le nom de la plante
        findViewById<TextView>(R.id.Popup_plant_name).text = currentPlant.name

        // actualiser la description de la plante
        findViewById<TextView>(R.id.Popup_plant_description_subtitle_title).text = currentPlant.description

        // actuliaser la croissance
        findViewById<TextView>(R.id.Popup_plant_grow_subtitle_title).text = currentPlant.grow

        // actualiser la conso d'eau
        findViewById<TextView>(R.id.Popup_plant_water_subtitle_title).text = currentPlant.water

        //
    }
}