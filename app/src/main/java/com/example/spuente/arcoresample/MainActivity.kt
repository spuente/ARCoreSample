package com.example.spuente.arcoresample

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment
    private var letterRenderablesMap = mutableMapOf<String, ModelRenderable>()
    private lateinit var textToDisplay: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arFragment = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment

        val letters = listOf(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"
        )
        letters.forEach { letter ->
            ModelRenderable.builder()
                .setSource(this, Uri.parse("letters_$letter.sfb"))
                .build()
                .thenAccept { this.letterRenderablesMap[letter] = it }
                .exceptionally { null }
        }

        arFragment.arSceneView.planeRenderer.isEnabled = true
        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            textToDisplay = messageEditText.text.toString()
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)

            val parentNode = TransformableNode(arFragment.transformationSystem)
            parentNode.setParent(anchorNode)
            parentNode.select()

            val lettersNamesToDisplay = textToDisplay.map { "${it.toUpperCase()}" }
            for ((index, letterName) in lettersNamesToDisplay.withIndex()) {
                val offsetX = if (index == 0) 0f else 0.1f
                val x = parentNode.children[index].localPosition.x + offsetX
                val y = parentNode.children[index].localPosition.y
                val z = parentNode.children[index].localPosition.z
                val node = Node()
                node.setParent(parentNode)
                node.localPosition = Vector3(x, y, z)
                node.renderable = letterRenderablesMap[letterName]
            }
        }
    }

    fun toggleGrid(view: View) {
        arFragment.arSceneView.planeRenderer.isVisible = !arFragment.arSceneView.planeRenderer.isVisible
    }

    fun toggleRed(view: View) {
        MaterialFactory.makeOpaqueWithColor(this, Color(255f, 0f, 0f))
            .thenAccept { material ->
                letterRenderablesMap.forEach {
                    it.value.material = material
                }
            }
    }

    fun toggleGreen(view: View) {
        MaterialFactory.makeOpaqueWithColor(this, Color(0f, 255f, 0f))
            .thenAccept { material ->
                letterRenderablesMap.forEach {
                    it.value.material = material
                }
            }
    }

    fun toggleBlue(view: View) {
        MaterialFactory.makeOpaqueWithColor(this, Color(0f, 0f, 255f))
            .thenAccept { material ->
                letterRenderablesMap.forEach {
                    it.value.material = material
                }
            }
    }
}
