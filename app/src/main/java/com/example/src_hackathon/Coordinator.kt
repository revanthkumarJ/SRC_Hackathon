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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun CoordinatorLogIn(navController: NavController)
{
    val context = LocalContext.current
    val auth = Firebase.auth
    var mail by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 35.dp), verticalArrangement = Arrangement.SpaceBetween)
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
                Text(text = "Coordinator Login" )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(value =mail , onValueChange ={
                    mail=it
                }, label = { Text(text = "Email") }, placeholder = { Text(text = "Enter Coordinator Email") } )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value =password , onValueChange ={
                    password=it
                }, visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Password),label = { Text(text = "Password") }, placeholder = { Text(text = "Enter Password") } )

                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = {
                    if (check(mail, password)) {
                        if(mail=="jrevanth101@gmail.com" && password=="Revanth@2004")
                        {
                            val intent = Intent(context, CoordinatorHomeActivity::class.java)
                            context.startActivity(intent)
                            Toast.makeText(context, "Log In Success", Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show()

                        }

                    } else {
                        Toast.makeText(context, "TextFields should not be empty", Toast.LENGTH_SHORT).show()
                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ), modifier = Modifier
                    .fillMaxWidth(0.82f)
                    .height(52.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Login", fontSize = 18.sp)
                }
            }
        }
    }
}