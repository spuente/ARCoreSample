package com.example.spuente.arcoresample

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment
    private var letterRenderablesMap = mutableMapOf<String, ModelRenderable>()
    private val textToDisplay = "SAMPLE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arFragment = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment

        val letterNames = listOf(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"
        ).map { "letters_$it.sfb" }

        letterNames.forEach { modelName ->
            ModelRenderable.builder()
                .setSource(this, Uri.parse(modelName))
                .build()
                .thenAccept { this.letterRenderablesMap[modelName] = it }
                .exceptionally { null }
        }

        arFragment.arSceneView.planeRenderer.isEnabled = true

        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)

            val parentNode = TransformableNode(arFragment.transformationSystem)
            parentNode.setParent(anchorNode)
            parentNode.select()

            val lettersNamesToDisplay = textToDisplay.map { "letters_$it.sfb" }
            for ((index, letterName) in lettersNamesToDisplay.withIndex()) {
                val offsetX = if (index == 0) 0.0f else 0.1f
                val x = parentNode.children[index].localPosition.x + offsetX
                val y = parentNode.children[index].localPosition.y + 0.0f
                val z = parentNode.children[index].localPosition.z + 0.0f
                val node = Node()
                node.setParent(parentNode)
                node.localPosition = Vector3(x, y, z)
                node.renderable = letterRenderablesMap[letterName]
            }
        }
    }
}
