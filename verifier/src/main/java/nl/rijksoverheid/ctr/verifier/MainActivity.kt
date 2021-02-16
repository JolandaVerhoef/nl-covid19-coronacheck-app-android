/*
 *  Copyright (c) 2021 De Staat der Nederlanden, Ministerie van Volksgezondheid, Welzijn en Sport.
 *   Licensed under the EUROPEAN UNION PUBLIC LICENCE v. 1.2
 *
 *   SPDX-License-Identifier: EUPL-1.2
 *
 */

package nl.rijksoverheid.ctr.verifier

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import nl.rijksoverheid.ctr.shared.ext.styleTitle
import nl.rijksoverheid.ctr.verifier.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_status, R.id.nav_scan_qr),
            binding.drawerLayout
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navigationDrawerStyling()

        supportFragmentManager.registerFragmentLifecycleCallbacks(
            object :
                FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentViewCreated(
                    fm: FragmentManager,
                    f: Fragment,
                    v: View,
                    savedInstanceState: Bundle?
                ) {
                    when (f) {
                        is NavHostFragment, is HideToolbar -> {
                            binding.toolbar.visibility = View.GONE
                            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                        }
                        else -> {
                            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                            binding.toolbar.visibility = View.VISIBLE
                        }
                    }
                }
            }, true
        )

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_about_this_app -> {
                    openBrowser("https://www.google.com")
                    true
                }
                R.id.nav_give_us_feedback -> {
                    openBrowser("https://www.google.com")
                    true
                }
                else -> false
            }
        }
    }

    private fun openBrowser(url: String) {
        CustomTabsIntent.Builder().build().also {
            it.launchUrl(this, Uri.parse(url))
        }
    }

    private fun navigationDrawerStyling() {
        val context = binding.navView.context
        binding.navView.menu.findItem(R.id.nav_scan_qr)
            .styleTitle(context, R.attr.textAppearanceHeadline6)
        binding.navView.menu.findItem(R.id.nav_support)
            .styleTitle(context, R.attr.textAppearanceHeadline6)
        binding.navView.menu.findItem(R.id.nav_about_this_app)
            .styleTitle(context, R.attr.textAppearanceBody1)
        binding.navView.menu.findItem(R.id.nav_give_us_feedback)
            .styleTitle(context, R.attr.textAppearanceBody1)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.close()
            return
        }
        super.onBackPressed()
    }
}
