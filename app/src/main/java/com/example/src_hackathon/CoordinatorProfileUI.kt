package com.example.srchackathon.coordinator.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.example.src_hackathon.PreviousEvent
import com.example.src_hackathon.PreviousEventUI
import com.example.src_hackathon.R
import com.example.src_hackathon.UpComingEvent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

data class CoordinatorData(
    val image:String?=null,
    val name: String?=null,
    val batch: String?=null,
    val domain: String?=null
)

@Composable
fun GetAllCoordinatorDataFromFireBase() {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Coordinators")

    var listOfPreviousEvents by remember { mutableStateOf(emptyList<CoordinatorData>()) }

    LaunchedEffect(Unit) {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val events = snapshot.children.mapNotNull { it.getValue(CoordinatorData::class.java) }
                listOfPreviousEvents = events
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Error",error.toString())
            }
        })
    }

    LazyRow (modifier = Modifier.fillMaxWidth().height(300.dp)){
        itemsIndexed(listOfPreviousEvents) { index, item ->
            item.image?.let {
                CoordinatorUI(
                    image = it,
                    name = item.name.orEmpty(),
                    batch = item.batch.orEmpty(),
                    domain = item.domain.orEmpty()
                )
            }
        }
    }
}


@Composable
fun CoordinatorUI(image: String, name: String, batch: String, domain: String) {
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
                .border(width = 1.dp, color = Color.LightGray, shape = RectangleShape)
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(100.dp, 100.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = name)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = batch)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = domain)
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}


@Composable
fun CoordinatorDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            CoordinatorDialogUI(context = context, onDismiss = onDismiss)
        }
    }
}


@Composable
fun CoordinatorDialogUI(context: Context, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var batch by remember { mutableStateOf("") }
    var domain by remember { mutableStateOf("") }
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
            value = name,
            onValueChange = { name = it },
            singleLine = true,
            label = { Text(text = "Coordinator Name") },
            placeholder = { Text(text = "Enter Coordinator Name") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = batch,
            onValueChange = { batch = it },
            singleLine = true,
            label = { Text(text = "Batch") },
            placeholder = { Text(text = "E1/E2/E3/E4") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = domain,
            onValueChange = { domain = it },
            singleLine = true,
            label = { Text(text = "Domain") },
            placeholder = { Text(text = "SRC Coordinator") }
        )
        Spacer(modifier = Modifier.height(10.dp))


        Button(onClick = {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val storageRef = FirebaseStorage.getInstance().reference.child("Coordinators/$fileName")
            imageUri?.let {
                storageRef.putFile(it).addOnSuccessListener {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

                    val result = it.metadata?.reference?.downloadUrl
                    result?.addOnSuccessListener { uri ->
                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.getReference("Coordinators")
                        myRef.child(name).setValue(
                            CoordinatorData(uri.toString(),name,batch,domain)
                        )
                    }
                }
            }
            onDismiss()
        }) {
            Text(text = "Add Coordinator")
        }
    }
}