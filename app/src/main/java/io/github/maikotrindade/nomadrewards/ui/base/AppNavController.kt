package io.github.maikotrindade.nomadrewards.ui.base

import android.content.Context
import android.os.Bundle
import androidx.navigation.*

class AppNavController(context: Context) : NavController(context) {
    private val lazyNavController: Lazy<NavController> = lazy { NavController(context) }
    private val navController: NavController by lazyNavController

    override fun addOnDestinationChangedListener(listener: OnDestinationChangedListener) {
        navController.addOnDestinationChangedListener(listener)
    }

    override fun removeOnDestinationChangedListener(listener: OnDestinationChangedListener) {
        navController.removeOnDestinationChangedListener(listener)
    }

    override fun navigate(
        resId: Int,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
        return navController.navigate(resId, args, navOptions, navigatorExtras)
    }
}