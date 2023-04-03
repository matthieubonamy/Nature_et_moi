package fr.matthieu.naturecollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.matthieu.naturecollection.fragments.AddPlantFragment
import fr.matthieu.naturecollection.fragments.CollectionFragment
import fr.matthieu.naturecollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this),R.string.home_page_title)

        //importer la bottom navigation view
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)
        navigationView.setOnItemSelectedListener {
            when(it.itemId)
            {
            R.id.home_page -> {
                loadFragment(HomeFragment(this),R.string.home_page_title)
                return@setOnItemSelectedListener true
            }
            R.id.collection_page -> {
                loadFragment(CollectionFragment(this),R.string.collection_page_title)
                return@setOnItemSelectedListener true
            }
            R.id.add_plant_page -> {
                loadFragment(AddPlantFragment(this),R.string.add_plant_page_title)
                return@setOnItemSelectedListener true
            }
                else -> false
            }
        }

    }

    private fun loadFragment(fragment: Fragment, string: Int) {
        //charger notre repostory
        val repo = PlantRepository()

        //actualiserletitrede la page
        findViewById<TextView>(R.id.page_title).text = resources.getString(string)

        //mettre à jour la liste de plantes
        repo.updateData {

            // injecter le fragment dans notre boite: (fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }

    }

}