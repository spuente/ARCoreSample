package com.example.spuente.arcoresample

import android.view.View
import com.google.ar.sceneform.ux.ArFragment

class CustomArFragment : ArFragment() {
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}