package uz.gita.superdictionary.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.superdictionary.R
import uz.gita.superdictionary.databinding.FragmentShareBinding
import uz.gita.superdictionary.util.showToast
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class ShareFragment : Fragment(R.layout.fragment_share) {

    private val binding by viewBinding(FragmentShareBinding::bind)
    private val args by navArgs<ShareFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        binding.wordNameTv.text = args.word
        binding.wordMeaningTv.text = args.meaning

        binding.cancelBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.shareBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (requireActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, 1001)
                } else {
                    share()
                }
            } else {
                share()
            }
        }
    }

    private fun share() {

        val bitmap = getBitmapFromView(binding.wordCl)

        try {
            val path = Environment.getExternalStorageDirectory().toString()
            val file = File(path, "background.png")
            val fout = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout)
            val intent = Intent(Intent.ACTION_SEND)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            intent.type = "image/png"
            requireActivity().startActivity(intent)


        } catch (e: Throwable) {
            Log.d("SHARE", "share: ${e.message}")
            showToast(e.message.toString())
        }
    }
//

    @SuppressLint("ResourceAsColor")
    fun getBitmapFromView(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawer: Drawable? = view.background
        if (bgDrawer != null) {
            bgDrawer.draw(canvas)
        } else {
            canvas.drawColor(android.R.color.white)
        }
        view.draw(canvas)
        return returnedBitmap
    }
}