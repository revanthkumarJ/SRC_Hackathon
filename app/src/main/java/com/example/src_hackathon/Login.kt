package com.example.src_hackathon

import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun StartingPage(navController: NavController)
{

        Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center)
        {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.src), contentDescription = "Starting_Page_Image", modifier = Modifier.size(200.dp,200.dp))
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
                {
                    Text(
                        text = "Welcome to the SRC (Student Recreation Center), a dynamic club initiated by the Computer Science department of RGUKT RK Valley in 2024. Our mission is to foster increased interaction between seniors and juniors, promoting a culture of mutual learning, collaboration, and support within our college community. You can see all our events in this app",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(text = "",
                    modifier =Modifier.padding(10.dp)
                )
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { navController.navigate("student_page") },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)

                        ) {
                        Text(text = "Student", fontSize = 19.sp, fontWeight = FontWeight.Medium)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {navController.navigate("coordinator_login")},
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                        ,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)

                    ) {
                        Text(text = "Event Organizer", fontSize = 19.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

        }
}

@Composable
fun StudentLogin(navController: NavController)
{

    Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center)
    {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.student_login),
                contentDescription = "Starting_Page_Image",
                modifier = Modifier.size(300.dp, 300.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center)
            {
                Text(text = "Welcome", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Text(text = "Hello RGUKTIANS Welcome to our latest SRC Events App. Login and see all our past and updated events",
                modifier =Modifier.padding(10.dp)
            )
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { navController.navigate("signup_screen") },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)

                ) {
                    Text(text = "Sign UP", fontSize = 19.sp, fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {navController.navigate("login_screen")},
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                    ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)

                ) {
                    Text(text = "Login", fontSize = 19.sp, fontWeight = FontWeight.Medium)
                }
            }
        }

    }
}



@Composable
fun SignUp(navController: NavController)
{
    val context = LocalContext.current
    val auth = Firebase.auth
    var mail by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize().padding(bottom = 35.dp), verticalArrangement = Arrangement.SpaceBetween)
    {
        Column {
            Image(painter = painterResource(id = R.drawable.transp_src_logo), contentDescription ="" )
            Column(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(5.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(value =mail , onValueChange ={
                    mail=it
                }, label = { Text(text = "Email")}, placeholder = { Text(text = "Enter Email")} )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value =password , onValueChange ={
                    password=it
                }, label = { Text(text = "Password")}, placeholder = { Text(text = "Set Password")} )
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = {
                    if (check(mail, password)) {
                        signUp(mail,password,auth,context as ComponentActivity)
                    } else {
                        Toast.makeText(context, "TextFields should not be empty", Toast.LENGTH_SHORT).show()
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ), modifier = Modifier.fillMaxWidth(0.82f).height(52.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Sign UP", fontSize = 18.sp)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 35.dp, end = 35.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already Have an Account?")
            Button(
                onClick = { navController.navigate("login_screen") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Blue
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Login", fontSize = 17.sp)
            }
        }
    }
}



@Composable
fun LogIn(navController: NavController)
{
    val context = LocalContext.current
    val auth = Firebase.auth
    var mail by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize().padding(bottom = 35.dp), verticalArrangement = Arrangement.SpaceBetween)
    {
        Column {
            Image(painter = painterResource(id = R.drawable.transp_src_logo), contentDescription ="" )
            Column(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(5.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(value =mail , onValueChange ={
                    mail=it
                }, label = { Text(text = "Email")}, placeholder = { Text(text = "Enter Email")} )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value =password , onValueChange ={
                    password=it
                }, visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType=KeyboardType.Password),label = { Text(text = "Password")}, placeholder = { Text(text = "Enter Password")} )
                Spacer(modifier = Modifier.height(2.dp))
                Box(modifier = Modifier.fillMaxWidth().padding(end = 30.dp), contentAlignment = Alignment.BottomEnd)
                {
                    Text(text = "Forgot Password?", color = Color.Red)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = {
                    if (check(mail, password)) {
                        logIn(mail, password, auth, context as ComponentActivity)
                    } else {
                        Toast.makeText(context, "TextFields should not be empty", Toast.LENGTH_SHORT).show()
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ), modifier = Modifier.fillMaxWidth(0.82f).height(52.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Login", fontSize = 18.sp)
                }

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 35.dp, end = 35.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't Have an Account?")
            Button(
                onClick = { navController.navigate("signup_screen") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Blue
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Sign Up", fontSize = 17.sp)
            }
        }
    }
}


fun logIn(email: String, password: String, auth: FirebaseAuth, context: ComponentActivity) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(context) { task ->
            if (task.isSuccessful) {
                val intent = Intent(context, StudentHome::class.java)
                context.startActivity(intent)
                Toast.makeText(context, "Log In Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Log In Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
}

fun signUp(email: String, password: String, auth: FirebaseAuth, context: ComponentActivity) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(context) { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Sign Up Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Sign Up Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
}


fun check(email: String, password: String): Boolean {
    return email.isNotEmpty() && password.isNotEmpty()
}