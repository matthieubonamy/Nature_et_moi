package fr.matthieu.naturecollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.matthieu.naturecollection.*

class PlantAdapter (
    val context: MainActivity,
    private val plantList: List<PlantModel>,
    private val layoutId: Int
    ) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {
    //boite pour ranger tout les composants à controler"
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName:TextView? = view.findViewById(R.id.name_item)
        val plantDescription:TextView? = view.findViewById(R.id.descriptin_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

    return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //recupererlesinfosdeaplantes
        val currentPlant = plantList[position]
        //recperelerepository
        val repo = PlantRepository()
        //utuliser glide pour recuppere l'image a partir desonlien ->composant
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)
        //metttre ajour le nom de la plante
        holder.plantName?.text = currentPlant.name
        //mettre a jour la description
        holder.plantDescription?.text = currentPlant.description
        //verifier silaplate estliked ou non
        if(currentPlant.liked) {
            holder.starIcon.setImageResource(R.drawable.ic_star)
            }
        else{
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
            }

        //interactionsur lesfavoris
        holder.starIcon.setOnClickListener {

            currentPlant.liked = !currentPlant.liked
            // mettre a jour objet plants
            repo.updatePlant(currentPlant)
        }
        //interactionlors d'unclicksur uneplante
        holder.itemView.setOnClickListener{
            //afficherlapopup
            Plant_popup(this, currentPlant).show()
        }
    }


    override fun getItemCount(): Int = plantList.size

}