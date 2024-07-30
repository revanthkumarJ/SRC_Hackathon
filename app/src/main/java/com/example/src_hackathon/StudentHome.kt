package com.example.src_hackathon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.src_hackathon.ui.theme.SRC_HackathonTheme

class StudentHome : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SRC_HackathonTheme {
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
                    },bottomBar = {
                        BottomNavigationBar(navController = navController, items = listOf(
                            BottomNavItem(name = "Previous Events", route = "home", icon = painterResource(id = R.drawable.previous_event)),
                            BottomNavItem(name = "UpComing Events", route = "chat", icon = painterResource(id = R.drawable.upcoming_event)),
                            BottomNavItem(name = "About SRC", route = "settings", icon = painterResource(
                                id = R.drawable.profile_filled
                            ))
                        ), onItemClick = {
                            top=it.route
                            navController.navigate(it.route)
                        })
                    }
                    ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding))
                    {
                        Navigation(navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(text:String)
{
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text =text,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 0.dp),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.transp_src_logo),
//                    contentDescription = "Back Button"
//                )

            }
        }

    )
}
