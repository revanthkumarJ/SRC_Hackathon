package com.example.src_hackathon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.src_hackathon.ui.theme.SRC_HackathonTheme
import com.example.srchackathon.coordinator.presentation.CoordinatorDialog
import com.example.srchackathon.coordinator.presentation.CoordinatorDialogUI
import com.example.srchackathon.coordinator.presentation.GetAllCoordinatorDataFromFireBase

class CoordinatorHomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SRC_HackathonTheme {
                var showDialog by remember { mutableStateOf(false) }
                var top by remember {
                    mutableStateOf("home")
                }
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if(top=="home")
                        {
                            TopBar(text = "SRC Previous Events")
                        }
                        else if(top=="settings")
                        {
                            TopBar(text = "About SRC")
                        }
                        else
                        {
                            TopBar(text = "SRC Upcoming Events")
                        }
                    },floatingActionButton = {
                        FloatingActionButton(onClick = {
                            showDialog = true
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription ="" )
                        }
                    },bottomBar = {
                        BottomNavigationUpComingBar(navController = navController, items = listOf(
                            BottomNavItem(name = "Previous Events", route = "home", icon = painterResource(id = R.drawable.previous_event)),
                            BottomNavItem(name = "UpComing Events", route = "chat", icon = painterResource(id = R.drawable.upcoming_event)),
                            BottomNavItem(name = "About SRC", route = "settings", icon = painterResource(
                                id = R.drawable.profile_filled
                            )
                            )
                        ), onItemClick = {
                            top=it.route
                            navController.navigate(it.route)
                        })
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding))
                    {
                        UpcomingNavigation(navController)
                    }
                }
                if (showDialog ) {
                    if(top=="chat")
                    EventDialog(onDismiss = { showDialog = false })
                    else if(top=="home")
                        PreviousEventDialog (
                            onDismiss = { showDialog = false }
                            )
                    else
                        CoordinatorDialog(onDismiss = { showDialog = false } )
                }
            }
        }
    }
}



@Composable
fun UpcomingNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Column {
                GetAllPreviousEventsDataFromFireBase()
            }
        }
        composable("chat") {
            Column() {
                GetAllUpComingEventsDataFromFireBase()

            }
        }
        composable("settings") {
//

            AboutSRC()
        }
    }
}


@Composable
fun BottomNavigationUpComingBar(
    navController: NavHostController,
    items: List<BottomNavItem>,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = Modifier.background(Color.DarkGray)
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray
                ),
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = item.icon,
                            contentDescription = null)
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}
