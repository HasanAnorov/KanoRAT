package com.ierusalem.androrat.core.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.data.preferences.DataStorePreferenceRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStorePreferenceRepository: DataStorePreferenceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets -> insets }

        // Setup the NavHostFragment and set the graph before setting content view
        val navHostFragment = NavHostFragment.create(R.navigation.nav_graph)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_container, navHostFragment)
            .setPrimaryNavigationFragment(navHostFragment)
            .commitNow()

        // Set up navigation graph and start destination
        setupNavigationGraph()

        setContentView(R.layout.activity_main)
    }

    private fun setupNavigationGraph() {
        val navController = findNavController()
        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(R.navigation.nav_graph)

        // Set the start destination synchronously based on login state
        val startDestination = if (runBlocking { isLoginRequired() }) {
            R.id.homeFragment
        } else {
            R.id.loginFragment
        }

        navGraph.setStartDestination(startDestination)
        navController.graph = navGraph
    }

    private suspend fun isLoginRequired(): Boolean {
        return withContext(Dispatchers.IO) {
            dataStorePreferenceRepository.getLoginRequired.first()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController().navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * See https://issuetracker.google.com/142847973
     */
    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        return navHostFragment.navController
    }

}
