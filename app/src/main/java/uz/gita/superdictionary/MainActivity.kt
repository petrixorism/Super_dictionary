package uz.gita.superdictionary

import android.os.Bundle
import android.view.Gravity
import android.view.Gravity.END
import android.view.Gravity.RIGHT
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.superdictionary.databinding.ActivityMainBinding
import uz.gita.superdictionary.util.makeVisibleOrGone

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.containerFragment) as NavHostFragment

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    findNavController(R.id.containerFragment).navigate(R.id.actionToHome)
                    binding.drawerLayout.closeDrawer(RIGHT)
                }
                R.id.historyFragment -> {
                    findNavController(R.id.containerFragment).navigate(R.id.actionToHistory)
                    binding.drawerLayout.closeDrawer(RIGHT)
                }
                R.id.favouritesFragment -> {
                    findNavController(R.id.containerFragment).navigate(R.id.actionToSavedWords)
                    binding.drawerLayout.closeDrawer(RIGHT)
                }
                R.id.addedWordsFragment -> {
                    findNavController(R.id.containerFragment).navigate(R.id.actionToAddWords)
                    binding.drawerLayout.closeDrawer(RIGHT)
                }
                R.id.infoFragment -> {
                    findNavController(R.id.containerFragment).navigate(R.id.actionToInfo)
                    binding.drawerLayout.closeDrawer(RIGHT)
                }
            }
            true
        }

//
        binding.bottomNav.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            // invisible some fragments later
            when (destination.id) {
                R.id.wordFragment, R.id.editWordFragment, R.id.addWordFragment, R.id.splashFragment -> makeVisibleOrGone(
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