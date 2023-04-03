package fr.matthieu.naturecollection

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.matthieu.naturecollection.PlantRepository.Singleton.databaseRef
import fr.matthieu.naturecollection.PlantRepository.Singleton.downloadUri
import fr.matthieu.naturecollection.PlantRepository.Singleton.plantList
import fr.matthieu.naturecollection.PlantRepository.Singleton.storageReference
import java.net.URI
import java.util.*


class PlantRepository {

    object Singleton {
        //lien vers le bucket Firebase
        private val BUCKET_URL: String = "gs://nature-collection-a4c83.appspot.com"

        //se connecter anotre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        //se connecter a la ref plants
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        // creerunelist plants
        val plantList = arrayListOf<PlantModel>()

        // contenir
        var downloadUri: Uri? = null

    }

    fun updateData(callback: () -> Unit){
        //absorber les data depuis le
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //retirer les anciennes plantes
                plantList.clear()
                //recolter la liste
                for (ds in snapshot.children){
                    //construire un objet plante
                    val plant = ds.getValue(PlantModel::class.java)

                    //verifier que la plante n'est pas null
                    if(plant!= null) {
                        //ajouter la plante à notre liste
                        plantList.add(plant)
                    }
                }
                //actionnerlecallbac
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}


        })
    }
    // creer une fonctionpour envoyer des fichiers vers le bucket
    fun uploadImage(file:Uri, callback: () -> Unit) {
        // verifier fichiers n'est pas null
        if(file != null){
            // créer unnom de fichier (nom au hasard  = UUID)
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            //demarrer la tache d'envoie
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot,Task<Uri>>{task->

                // si problem lrsde l'envoie
                if(!task.isSuccessful) {
                    task.exception?.let { throw it }
                }

                return@Continuation ref.downloadUrl
            }).addOnCompleteListener { task ->
                // verifier si tout a bien fonctionné
                if (task.isSuccessful) {
                    // recuperer l'image
                    downloadUri = task.result
                    callback()
                }

            }

        }

    }


    //update plants
    fun updatePlant (plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    // inserer une nouvelleplant enBDD
    fun insertPlant (plant: PlantModel) = databaseRef.child(plant.id).setValue(plant)

    //supprimer la plante dela base
    fun deletePlant (plant: PlantModel) = databaseRef.child(plant.id).removeValue()

}