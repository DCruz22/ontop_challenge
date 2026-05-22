package com.example.ontop_challenge.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.ontop_challenge.R
import com.example.ontop_challenge.ui.details.PokemonDetails
import com.example.ontop_challenge.ui.list.PokemonList

private const val ROUTE_LIST = "list"
private const val ROUTE_DETAILS = "details/{name}"
private const val ARG_NAME = "name"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonNavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = ROUTE_LIST,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val refreshAction = remember { mutableStateOf<(() -> Unit)?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val titleRes = if (currentRoute == ROUTE_LIST) {
                        R.string.pokemon_title
                    } else {
                        R.string.details_title
                    }
                    Text(stringResource(id = titleRes))
                },
                navigationIcon = {
                    if (currentRoute != ROUTE_LIST) {
                        IconButton(onClick = { 
                            navController.popBackStack()
                        }) {
                            Icon(
                                painter = painterResource(android.R.drawable.ic_menu_revert),
                                contentDescription = stringResource(id = R.string.back)
                            )
                        }
                    }
                },
                actions = {
                    if (currentRoute == ROUTE_LIST) {
                        IconButton(onClick = { refreshAction.value?.invoke() }) {
                            Icon(
                                painter = painterResource(android.R.drawable.ic_menu_rotate),
                                contentDescription = stringResource(id = R.string.refresh)
                            )
                        }
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ROUTE_LIST,
                exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -300 }) },
            ) {
                PokemonList(
                    onItemClick = { name ->
                        navController.navigate("details/$name")
                    },
                    onRefresh = { action -> refreshAction.value = action }
                )
            }

            composable(
                route = ROUTE_DETAILS,
                arguments = listOf(navArgument(ARG_NAME) { type = NavType.StringType }),
                enterTransition = { fadeIn() + slideInHorizontally(initialOffsetX = { 300 }) },
                exitTransition = { fadeOut() + slideOutHorizontally(targetOffsetX = { -300 }) },
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString(ARG_NAME) ?: ""
                PokemonDetails(
                    pokemonName = name
                )
            }
        }
    }
}
