package com.example.pokerick.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokerick.ui.screens.detail.CharacterDetailScreen
import com.example.pokerick.ui.screens.list.CharacterListScreen
import com.example.pokerick.ui.screens.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate("character_list") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

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