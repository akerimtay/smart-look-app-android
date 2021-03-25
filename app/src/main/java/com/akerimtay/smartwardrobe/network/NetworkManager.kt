package com.akerimtay.smartwardrobe.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akerimtay.smartwardrobe.common.base.BaseError

class NetworkManager(context: Context) {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val _networkState = MutableLiveData(isConnectedToNetwork())
    val networkState: LiveData<Boolean> = _networkState

    fun isConnectedToNetwork(): Boolean =
        connectivityManager.allNetworks.any { connectivityManager.getNetworkInfo(it)?.isConnected == true }

    init {
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    _networkState.postValue(isConnectedToNetwork())
                }

                override fun onLost(network: Network) {
                    _networkState.postValue(isConnectedToNetwork())
                }
            }
        )
    }

    fun throwIfNoConnection() {
        if (!isConnectedToNetwork()) throw BaseError.NoInternetError
    }
}