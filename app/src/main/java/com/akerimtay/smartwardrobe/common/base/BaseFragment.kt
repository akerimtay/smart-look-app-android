package com.akerimtay.smartwardrobe.common.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.akerimtay.smartwardrobe.common.AppContract
import com.akerimtay.smartwardrobe.R

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
    protected var appContract: AppContract? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContract = context as? AppContract
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appContract?.setDisplayHomeAsUpEnabled(!isRootFragment())
        appContract?.setTitle(getTitle())
        appContract?.setActionBarVisibility(isActionBarVisible())
        appContract?.setBottomNavigationViewVisibility(isBottomNavigationViewVisible())
    }

    open fun isRootFragment() = false

    open fun getTitle() = getString(R.string.app_name)

    open fun isActionBarVisible() = true

    open fun isBottomNavigationViewVisible() = true
}