package com.akerimtay.smartwardrobe.auth.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.auth.ui.signIn.SignInFragment
import com.akerimtay.smartwardrobe.common.utils.replaceFragment

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.replaceFragment(
            fragment = SignInFragment(),
            shouldAddToBackStack = false
        )
    }

    companion object {
        fun start(context: Context) = context.startActivity(
            Intent(context, LoginActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }
}