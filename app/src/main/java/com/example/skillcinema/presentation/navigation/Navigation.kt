package com.example.skillcinema.presentation.navigation

import com.example.skillcinema.presentation.profile.ProfileScreen
import com.example.skillcinema.presentation.search.SearchScreen
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skillcinema.presentation.film.FilmScreen
import com.example.skillcinema.presentation.home.HomeScreen
import com.example.skillcinema.presentation.onboarding.OnBoardingScreen
import com.example.testing.R


@Composable
fun NavLogic(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        content = { innerPadding ->
            NavigationGraph(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
            )
        },
        bottomBar = {
            BottomNav(navController)
        }
    )
}

@Composable
fun BottomNav(
    navController: NavHostController
) {
    val items = listOf(
        BottomNavItems.Home,
        BottomNavItems.Search,
        BottomNavItems.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(screen.icon),
                        contentDescription = null,
                        tint = if (navBackStackEntry?.destination?.route == screen.route)
                            Color(0xFF3D3BFF)
                        else Color.Gray
                    )
                },
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                selected = navBackStackEntry?.destination?.route == screen.route
            )

        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "onboarding",
        modifier = modifier
    ) {
        composable(route = "onboarding") {
            OnBoardingScreen(navController)
        }
        composable(route = BottomNavItems.Home.route) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate("movie/$movieId")
                }
            )
        }
        composable(route = BottomNavItems.Search.route) {
            SearchScreen()
        }
        composable(route = BottomNavItems.Profile.route) {
            ProfileScreen()
        }
        composable(
            route = "movie/{movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getInt("movieId")
            if (movieId != null){
                FilmScreen(filmId = movieId)
            }
        }
    }
}

sealed class BottomNavItems(val icon: Int, val route: String) {
    object Home : BottomNavItems(R.drawable.icons, "home")
    object Search : BottomNavItems(R.drawable.ic, "search")
    object Profile : BottomNavItems(R.drawable.ic1, "profile")
}