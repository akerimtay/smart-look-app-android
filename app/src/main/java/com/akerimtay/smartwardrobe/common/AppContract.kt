package com.akerimtay.smartwardrobe.common

interface AppContract {
    fun setTitle(text: String)
    fun setDisplayHomeAsUpEnabled(value: Boolean)
    fun setBottomNavigationViewVisibility(value: Boolean)
    fun setActionBarVisibility(value: Boolean)
}