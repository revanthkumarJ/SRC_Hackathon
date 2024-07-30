package com.example.src_hackathon

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.srchackathon.coordinator.presentation.CoordinatorData
import com.example.srchackathon.coordinator.presentation.GetAllCoordinatorDataFromFireBase


@Composable
fun AboutSRC()
{
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)
        .verticalScroll(rememberScrollState())){
        Text(text = "Student Recreation Center", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = "Unlock Your Potential, Computer Science Recreation Center Welcomes You.", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = "We are pleased to propose the establishment of a Student Recreational Center at Rajiv Gandhi University of Knowledge Technologies, initiated by the Computer Science and Engineering (CSE) department. We aim to establish a welcoming space that caters to all students, offering opportunities to explore technical interests and collaborate across disciplines.\n" +
                "We think that by giving our students this kind of environment, we can foster their creativity, ingenuity, and commitment to lifelong learning. This program will improve students' college experience while also better preparing them for their future professions.")
        Spacer(modifier = Modifier.height(10.dp))
        Column {
            Text(text = "SRC Coordinators", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))
            GetAllCoordinatorDataFromFireBase()
            Spacer(modifier = Modifier.height(20.dp))
        }

    }
}


