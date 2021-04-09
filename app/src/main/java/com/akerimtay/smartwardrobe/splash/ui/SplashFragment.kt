package com.akerimtay.smartwardrobe.splash.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.base.BaseFragment
import com.akerimtay.smartwardrobe.common.utils.observeNotNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment(R.layout.fragment_splash) {
    private val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.actions.observeNotNull(viewLifecycleOwner) { action ->
            when (action) {
                is SplashAction.ShowStartPage -> {
                    val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    val mainGraph = navController.navInflater.inflate(R.navigation.app_navigation_graph)
                    mainGraph.startDestination = when (action.startPage) {
                        StartPage.AUTH -> R.id.auth_navigation_graph
                        StartPage.MAIN -> R.id.mainFragment
                    }
                    navController.graph = mainGraph
                }
            }
        }
    }
}