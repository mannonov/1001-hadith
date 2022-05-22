package uz.h1001.hadith.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowInsetsControllerCompat
import uz.h1001.hadith.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowInsetsControllerCompat(window,window.decorView).isAppearanceLightStatusBars = true

    }
}