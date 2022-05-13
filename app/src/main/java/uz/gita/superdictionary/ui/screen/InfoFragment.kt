package uz.gita.superdictionary.ui.screen

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.superdictionary.R
import uz.gita.superdictionary.databinding.FragmentInfoBinding

@AndroidEntryPoint
class InfoFragment : Fragment(R.layout.fragment_info) {

    private val binding by viewBinding(FragmentInfoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gridBtn.setOnClickListener {
            val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
            drawer.openDrawer(Gravity.RIGHT)
        }
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }


    }


}