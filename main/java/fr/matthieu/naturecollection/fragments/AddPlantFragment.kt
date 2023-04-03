package fr.matthieu.naturecollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.matthieu.naturecollection.MainActivity
import fr.matthieu.naturecollection.PlantModel
import fr.matthieu.naturecollection.PlantRepository
import fr.matthieu.naturecollection.PlantRepository.Singleton.downloadUri
import fr.matthieu.naturecollection.R
import java.util.UUID

class AddPlantFragment (
    private val context: MainActivity
        ) : Fragment() {

    private var file: Uri? = null
    private var uploadedImage:ImageView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_plant, container, false)

        //recupere uploadedImage pourlui associer son composant
        uploadedImage = view.findViewById(R.id.preview_image)

        //recuperer le button pour charger l'image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

         //iteraction quand onclick dessus ça ouvre l'image
        pickupImageButton.setOnClickListener {pickupImage()}

        //recupere le bouton "confirmer"
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        confirmButton.setOnClickListener{sendForm(view)}

        return view
    }

    private fun sendForm(view: View) {
        val repo = PlantRepository()
        repo.uploadImage(file!!) {
            val plantName =  view.findViewById<EditText>(R.id.name_input).text.toString()
            val plantDescription = view.findViewById<EditText>(R.id.descriptin_input).text.toString()
            val grow = view.findViewById<Spinner>(R.id.grow_spinner).selectedItem.toString()
            val water = view.findViewById<Spinner>(R.id.water_spinner).selectedItem.toString()
            val downloadImageUri = downloadUri

            // creer un nouvelle objet PlantModel
            val plant = PlantModel(
                UUID.randomUUID().toString(),
                plantName,
                plantDescription,
                downloadImageUri.toString(),
                grow,
                water
            )

            //envoyer dans la BDD
            repo.insertPlant(plant)
        }

    }

    private fun pickupImage() {
    val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 && resultCode == Activity.RESULT_OK){

            //verifier si les données sont nulles
            if(data == null || data.data == null ) return

            //recupererlesimages
            file = data.data

            //mettre a jour l'apercu image
            uploadedImage?.setImageURI(file)

        }

    }
}