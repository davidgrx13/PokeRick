package com.example.pokerick.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokerick.ui.screens.list.CharacterListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "character_list") {

        composable("character_list") {
            CharacterListScreen(
                onNavigateToDetail = { characterId ->
                    // to detail
                }
            )
        }
    }
}