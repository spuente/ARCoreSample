package com.example.spuente.arcoresample

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
    private lateinit var letterRenderable1: ModelRenderable
    private lateinit var letterRenderable2: ModelRenderable
    private lateinit var letterRenderable3: ModelRenderable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arFragment = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment

        ModelRenderable.builder()
            .setSource(this, R.raw.letters_1)
            .build()
            .thenAccept { this.letterRenderable1 = it }
            .exceptionally { null }

        ModelRenderable.builder()
            .setSource(this, R.raw.letters_2)
            .build()
            .thenAccept { this.letterRenderable2 = it }
            .exceptionally { null }

        ModelRenderable.builder()
            .setSource(this, R.raw.letters_3)
            .build()
            .thenAccept { this.letterRenderable3 = it }
            .exceptionally { null }

        arFragment.arSceneView.planeRenderer.isEnabled = true

        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)

            val parentNode = TransformableNode(arFragment.transformationSystem)
            parentNode.setParent(anchorNode)
            parentNode.select()
            val node1 = Node()
            node1.setParent(parentNode)
            node1.renderable = letterRenderable1

            val x1 = parentNode.children[1].localPosition.x + 0.1f
            val y1 = parentNode.children[1].localPosition.y + 0.0f
            val z1 = parentNode.children[1].localPosition.z + 0.0f
            val node2 = Node()
            node2.setParent(parentNode)
            node2.localPosition = Vector3(x1, y1, z1)
            node2.renderable = letterRenderable2

            val x2 = parentNode.children[2].localPosition.x + 0.1f
            val y2 = parentNode.children[2].localPosition.y + 0.0f
            val z2 = parentNode.children[2].localPosition.z + 0.0f
            val node3 = Node()
            node3.setParent(parentNode)
            node3.localPosition = Vector3(x2, y2, z2)
            node3.renderable = letterRenderable3
        }
    }
}
