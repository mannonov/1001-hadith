package uz.h1001.hadith.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import uz.h1001.hadith.R
import uz.h1001.hadith.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        binding.mainAdsBanner.loadAd(adRequest)

        binding.mainAdsBanner.adListener = object : AdListener() {
            override fun onAdLoaded() {
                binding.mainAdsBanner.visibility = View.VISIBLE
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                binding.mainAdsBanner.visibility = View.GONE
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }

    }
}