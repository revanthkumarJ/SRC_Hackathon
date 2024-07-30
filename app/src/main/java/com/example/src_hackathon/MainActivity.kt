package com.example.src_hackathon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.src_hackathon.ui.theme.SRC_HackathonTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Firebase.auth
            SRC_HackathonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Navigation()
                    }

                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val context = LocalContext.current
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController = navController,context =context)
        }

        // Main Screen
        composable("main_screen") {
            StartingPage(navController = navController)
        }

        // Main Screen
        composable("student_page") {
            StudentLogin(navController = navController)
        }

        // Login Screen
        composable("login_screen") {
            LogIn(navController = navController)
        }

        // Student Screen
        composable("coordinator_login") {
            CoordinatorLogIn(navController = navController)
        }

        // SignUp Screen
        composable("signup_screen") {
            SignUp(navController = navController)
        }
    }
}
@Composable
fun SplashScreen(navController: NavController,context: Context) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            // tween Animation
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }))
        // Customize the delay time
        delay(3000L)
        navController.navigate("main_screen")
//        val intent = Intent(context, StudentHome::class.java)
//        context.startActivity(intent)
    }

    // Image
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
        // Change the logo
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.transp_src_logo),
                contentDescription = "Logo",
                modifier = Modifier.scale(scale.value))

           Column(
                modifier = Modifier.fillMaxWidth(),
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(text = "Powered by", fontSize = 16.sp)
                Text(text = "SRC", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
