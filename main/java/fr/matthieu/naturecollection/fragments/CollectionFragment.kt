package fr.matthieu.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.matthieu.naturecollection.MainActivity
import fr.matthieu.naturecollection.PlantRepository.Singleton.plantList
import fr.matthieu.naturecollection.R
import fr.matthieu.naturecollection.adapter.PlantAdapter
import fr.matthieu.naturecollection.adapter.PlantItemDecoration

class CollectionFragment (
    private val context: MainActivity
        ):Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_collection, container, false)

        //recuperer recyclerview
        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerView.adapter = PlantAdapter(context, plantList.filter { it.liked },
            R.layout.item_vertical_plant
        )
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        collectionRecyclerView.addItemDecoration(PlantItemDecoration())

        return view
    }

}