package com.example.w6.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.w6.ui.screen.ClubProjectsScreen
import com.example.w6.ui.screen.CommunitiesScreen
import com.example.w6.ui.screen.DashboardScreen
import com.example.w6.ui.screen.ProjectDetailScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isDashboard = currentDestination?.hasRoute<DashboardRoute>() == true
    val isCommunities = currentDestination?.hasRoute<CommunitiesRoute>() == true
    val showBottomBar = isDashboard || isCommunities

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        selected = isDashboard,
                        onClick = {
                            if (!isDashboard) {
                                navController.navigate(DashboardRoute) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Dashboard"
                            )
                        },
                        label = {
                            Text(
                                text = "Dashboard",
                                fontSize = 11.sp,
                                fontWeight = if (isDashboard) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF2563EB),
                            selectedTextColor = Color(0xFF2563EB),
                            unselectedIconColor = Color(0xFF94A3B8),
                            unselectedTextColor = Color(0xFF94A3B8),
                            indicatorColor = Color(0xFFEFF6FF)
                        )
                    )

                    NavigationBarItem(
                        selected = isCommunities,
                        onClick = {
                            if (!isCommunities) {
                                navController.navigate(CommunitiesRoute) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Hub,
                                contentDescription = "Communities"
                            )
                        },
                        label = {
                            Text(
                                text = "Communities",
                                fontSize = 11.sp,
                                fontWeight = if (isCommunities) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF2563EB),
                            selectedTextColor = Color(0xFF2563EB),
                            unselectedIconColor = Color(0xFF94A3B8),
                            unselectedTextColor = Color(0xFF94A3B8),
                            indicatorColor = Color(0xFFEFF6FF)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<DashboardRoute> {
                DashboardScreen(
                    onCommunityClick = { communityId ->
                        navController.navigate(ClubProjectsRoute(communityId))
                    },
                    onProjectClick = { projectId ->
                        navController.navigate(ProjectDetailRoute(projectId))
                    }
                )
            }

            composable<CommunitiesRoute> {
                CommunitiesScreen(
                    onCommunitySelected = { communityId ->
                        navController.navigate(ClubProjectsRoute(communityId))
                    }
                )
            }

            composable<ClubProjectsRoute> {
                ClubProjectsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onProjectClick = { projectId ->
                        navController.navigate(ProjectDetailRoute(projectId))
                    }
                )
            }

            composable<ProjectDetailRoute> {
                ProjectDetailScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
