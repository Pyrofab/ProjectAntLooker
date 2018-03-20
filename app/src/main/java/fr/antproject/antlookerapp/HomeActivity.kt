package fr.antproject.antlookerapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView

class HomeActivity : AppCompatActivity() {
    private val SPLASH = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Handler().postDelayed({
            val i = Intent(this@HomeActivity, AntLookerApp::class.java)
            startActivity(i)
        }, SPLASH.toLong())
    }
}
