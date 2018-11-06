package com.example.spuente.arcoresample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment

class MainActivity : AppCompatActivity() {

    lateinit var arFragment: ArFragment
    lateinit var renderable: ModelRenderable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arFragment = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment

        ModelRenderable.builder()
            .setSource(this, R.raw.letters_1)
            .build()
            .thenAccept { this.renderable = it }
            .exceptionally { null }
    }
}
