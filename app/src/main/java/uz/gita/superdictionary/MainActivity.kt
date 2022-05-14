package uz.gita.superdictionary

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.Gravity.END
import android.view.Gravity.RIGHT
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.superdictionary.data.SharedPref
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

        binding.bottomNav.setupWithNavController(navHostFragment.navController)
        binding.navView.setupWithNavController(navHostFragment.navController)
        binding.closeBtn.setOnClickListener {
            binding.drawerLayout.closeDrawer(RIGHT)
        }

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            // invisible some fragments later
            when (destination.id) {
                R.id.wordFragment, R.id.editWordFragment, R.id.addWordFragment, R.id.splashFragment, R.id.shareFragment -> makeVisibleOrGone(
                    binding.bottomNav,
                    false
                )
                else -> makeVisibleOrGone(
                    binding.bottomNav,
                    true
                )
            }

        }

        if (SharedPref.getInstance().isDayMode) {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate
                        .MODE_NIGHT_NO
                )
        } else {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate
                        .MODE_NIGHT_YES
                )
        }
    }

    @SuppressLint("RtlHardcoded")
    override fun onBackPressed() {
        val drawer = binding.drawerLayout
        if (drawer.isDrawerOpen(RIGHT)) {
            drawer.closeDrawer(RIGHT)
        } else {
            super.onBackPressed()
        }
    }


}