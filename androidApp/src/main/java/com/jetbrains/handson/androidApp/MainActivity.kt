package com.jetbrains.handson.androidApp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.jetbrains.handson.androidApp.databinding.ActivityMainBinding
import com.jetbrains.handson.kmm.shared.SpaceXSdk
import com.jetbrains.handson.kmm.shared.cache.DatabaseDriverFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val launchesAdapter = LaunchesAdapter()
    private val sdk = SpaceXSdk(DatabaseDriverFactory(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "SpaceX Launches"

        binding.launchesList.adapter = launchesAdapter

        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false
            displayLaunches(true)
        }

        displayLaunches(false)
    }

    private fun displayLaunches(forceReload: Boolean) {
        binding.progressBar.isVisible = true
        lifecycleScope.launchWhenResumed {
            kotlin.runCatching { sdk.getLaunches(forceReload) }
                .onSuccess { launchesAdapter.launches = it }
                .onFailure {
                    Log.e("MainActivity", "Error loading launches", it)
                    Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            binding.progressBar.isVisible = false
        }
    }
}
