package com.example.pokerick.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokerick.ui.screens.detail.CharacterDetailScreen
import com.example.pokerick.ui.screens.list.CharacterListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "character_list") {

        composable("character_list") {
            CharacterListScreen(
                onNavigateToDetail = { characterId ->
                    navController.navigate("character_detail/$characterId")
                }
            )
        }

        composable(
            route = "character_detail/{characterId}",
            arguments = listOf(
                navArgument("characterId") { type = NavType.StringType }
            )
        ) {
            CharacterDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}