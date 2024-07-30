package com.example.src_hackathon

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

data class UpComingEvent(
    val eventName: String? = null,
    val date: String? = null,
    val time: String? = null,
    val venue: String? = null,
    val image:String?=null
)


@Composable
fun UpcomingEventUI(image: String, eventName: String, date: String, time: String, venue: String) {
    val painter = rememberImagePainter(
        data = image,
        builder = {
            placeholder(R.drawable.src)
            error(R.drawable.ic_launcher_background)
        }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.LightGray, shape = RectangleShape)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(380.dp, 220.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = eventName, fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Date: $date")
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "Time: $time")
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "Venue: $venue")
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { /* TODO: Implement action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Apply Here for Participation")
            }
        }
    }
}


@Composable
fun EventDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            UpcomingEventDialogUI(context = context, onDismiss = onDismiss)
        }
    }
}


@Composable
fun UpcomingEventDialogUI(context: Context, onDismiss: () -> Unit) {
    var eventName by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var venue by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        if (uri != null) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            imageBitmap = ImageDecoder.decodeBitmap(source)
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imageBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

        Button(onClick = {
            imagePickerLauncher.launch("image/*")
        }) {
            Text(text = "Choose Image")
        }

        OutlinedTextField(
            value = eventName,
            onValueChange = { eventName = it },
            singleLine = true,
            label = { Text(text = "Event Name") },
            placeholder = { Text(text = "Enter Event Name") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            singleLine = true,
            label = { Text(text = "Date") },
            placeholder = { Text(text = "DD/MM/YYYY") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = time,
            onValueChange = { time = it },
            singleLine = true,
            label = { Text(text = "Time") },
            placeholder = { Text(text = "HH:MM AM/PM") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = venue,
            onValueChange = { venue = it },
            singleLine = true,
            label = { Text(text = "Venue") },
            placeholder = { Text(text = "Room, Which Department") }
        )
        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val storageRef = FirebaseStorage.getInstance().reference.child("UpcomingEventImages/$fileName")
            imageUri?.let {
                storageRef.putFile(it).addOnSuccessListener {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

                    val result = it.metadata?.reference?.downloadUrl
                    result?.addOnSuccessListener { uri ->
                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.getReference("UpComingEvents")
                        myRef.child(eventName).setValue(
                            UpComingEvent(eventName, date, time, venue, uri.toString())
                        )
                    }
                }
            }
            onDismiss()
        }) {
            Text(text = "Upload Data")
        }
    }
}


@Composable
fun GetAllUpComingEventsDataFromFireBase() {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("UpComingEvents")

    var listOfUpcomingEvents by remember { mutableStateOf(emptyList<UpComingEvent>()) }

    LaunchedEffect(Unit) {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val events = snapshot.children.mapNotNull { it.getValue(UpComingEvent::class.java) }
                listOfUpcomingEvents = events
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Error",error.toString())
            }
        })
    }

    LazyColumn {
        itemsIndexed(listOfUpcomingEvents) { index, item ->
            item.image?.let {
                UpcomingEventUI(
                    image = it,
                    eventName = item.eventName.orEmpty(),
                    date = item.date.orEmpty(),
                    time = item.time.orEmpty(),
                    venue = item.venue.orEmpty()
                )
            }
        }
    }
}