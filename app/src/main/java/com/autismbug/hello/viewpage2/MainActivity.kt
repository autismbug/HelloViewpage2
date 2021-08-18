package com.autismbug.hello.viewpage2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<MaterialButton>(R.id.buttonNormalUse).setOnClickListener {
            startActivity(Intent(this, ViewPage2NormalUseActivity::class.java))
        }

        findViewById<MaterialButton>(R.id.buttonFragmentUse).setOnClickListener {
            startActivity(Intent(this, ViewPage2FragmentActivity::class.java))
        }

        findViewById<MaterialButton>(R.id.buttonTablayoutUse).setOnClickListener {
            startActivity(Intent(this, ViewPage2TablayoutActivity::class.java))
        }

        findViewById<MaterialButton>(R.id.buttonBottomNavigationUse).setOnClickListener {
            startActivity(Intent(this, ViewPage2BottomNavigationViewActivity::class.java))
        }
    }
}