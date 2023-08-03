package com.maggnet.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.mayNavigate(destinationIdOfThisFragment: Int): Boolean {

    val navController = findNavController()
    val destinationIdInNavController = navController.currentDestination?.id

    // check that the navigation graph is still in 'this' fragment, if not then the app already navigated:
    return destinationIdInNavController == destinationIdOfThisFragment

}