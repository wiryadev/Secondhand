package com.firstgroup.secondhand.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.firstgroup.secondhand.R
import com.firstgroup.secondhand.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private val bottomNavBarVisibleDestination = listOf(
        R.id.main_navigation_home,
        R.id.main_navigation_notification,
        R.id.main_navigation_sell_list,
        R.id.main_navigation_account,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession()

        setUpBottomNavBar()
    }

    private fun setUpBottomNavBar() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment
        val navController = navHostFragment.navController

        with(binding) {
            mainBottomNav.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                mainBottomNav.visibility = if (destination.id in bottomNavBarVisibleDestination) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }


}
