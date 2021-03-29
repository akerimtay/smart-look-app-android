package com.akerimtay.smartwardrobe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.akerimtay.smartwardrobe.common.AppContract
import com.akerimtay.smartwardrobe.common.base.BaseActivity
import com.akerimtay.smartwardrobe.databinding.ActivityMainBinding

class MainActivity : BaseActivity(R.layout.activity_main),
    AppContract {
    companion object {
        fun start(context: Context) =
            context.startActivity(
                Intent(context, MainActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
    }

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController.setGraph(R.navigation.navigation_auth)
    }

    override fun setTitle(text: String) {
        supportActionBar?.title = text
    }

    override fun setDisplayHomeAsUpEnabled(value: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(value)
    }

    override fun setBottomNavigationViewVisibility(value: Boolean) {

    }

    override fun setActionBarVisibility(value: Boolean) {
        if (value) supportActionBar?.show() else supportActionBar?.hide()
    }
}