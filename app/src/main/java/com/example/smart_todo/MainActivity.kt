package com.example.smart_todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.smart_todo.ui.features.todo_list.ToDoListScreen
import com.example.smart_todo.ui.features.todo_stats.StatisticsScreen
import com.example.smart_todo.ui.features.task_details.TaskDetailsScreen
import com.example.smart_todo.ui.features.todo_onboarding.OnboardingScreen

import com.example.smart_todo.ui.navigation.AppNavigation
import com.example.smart_todo.ui.theme.SmarttodoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmarttodoTheme {
                val navController = rememberNavController()

                // текущие экраны
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                // нужен ли bottom bur
                val shouldShowBottomBar =
                    currentDestination?.hasRoute<AppNavigation.ToDoList>() == true ||
                            currentDestination?.hasRoute<AppNavigation.Statistics>() == true

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar) {
                            NavigationBar {

                                // Главная
                                NavigationBarItem (
                                    selected = currentDestination.hasRoute<AppNavigation.ToDoList>(),
                                    onClick = {
                                        navController.navigate(AppNavigation.ToDoList) {
                                            popUpTo (navController.graph.startDestinationId) { saveState = true}
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null)},
                                    label = { Text("Задачи") }
                                )

                                // Статистика
                                NavigationBarItem (
                                    selected = currentDestination.hasRoute<AppNavigation.Statistics>(),
                                    onClick = {
                                        navController.navigate(AppNavigation.Statistics) {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = { Icon(Icons.Default.BarChart, contentDescription = null)},
                                    label = { Text("Статистика") }
                                )
                            }
                        }
                    },


                    floatingActionButton = {
                        if (currentDestination?.hasRoute<AppNavigation.ToDoList>() == true) {
                            FloatingActionButton(onClick = { navController.navigate(AppNavigation.TaskDetails())}) {
                                Icon(Icons.Default.Add, contentDescription = null)
                            }
                        }
                    }

                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AppNavigation.ToDoList,
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable<AppNavigation.ToDoList> {
                            ToDoListScreen(
                                onTaskClick = {id -> navController.navigate(AppNavigation.TaskDetails(id))}
                            )
                        }

                        composable<AppNavigation.TaskDetails> { backStackEntry ->
                            val args = backStackEntry.toRoute<AppNavigation.TaskDetails>()
                            TaskDetailsScreen(id = args.id, onBackClick = { navController.popBackStack()})

                        }

                        composable<AppNavigation.Statistics> {
                            StatisticsScreen()
                        }

                        composable<AppNavigation.Onboarding> {
                            OnboardingScreen()
                        }
                    }
                }
            }
        }
    }
}
