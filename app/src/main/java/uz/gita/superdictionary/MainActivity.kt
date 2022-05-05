package uz.gita.superdictionary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import uz.gita.superdictionary.databinding.ActivityMainBinding
import uz.gita.superdictionary.util.makeVisibleOrGone

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.containerFragment) as NavHostFragment

        binding.bottomNav.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            // invisible some fragments later
            when (destination.id) {
                R.id.wordFragment, R.id.updateAddedWordFragment, R.id.addedWordsFragment -> makeVisibleOrGone(
                    binding.bottomNav,
                    false
                )
                else -> makeVisibleOrGone(
                    binding.bottomNav,
                    true
                )
            }

        }

    }


}