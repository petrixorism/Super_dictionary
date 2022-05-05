package uz.gita.superdictionary.util

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import uz.gita.superdictionary.app.DictionaryApp


fun Fragment.showToast(message: String) {
    Toast.makeText(DictionaryApp.instance, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
}

fun makeVisibleOrGone(view: View, isVisible: Boolean) {
    if (isVisible) view.visibility = VISIBLE
    else view.visibility = GONE
}