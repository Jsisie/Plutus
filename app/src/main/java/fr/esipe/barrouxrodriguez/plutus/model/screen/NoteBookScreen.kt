package fr.esipe.barrouxrodriguez.plutus.model.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

class NoteBookScreen {
    @SuppressLint("NotConstructor")
    @Composable
    fun NoteBookScreen(navController: NavController) {
        Button(modifier = Modifier
            .padding(10.dp),
            onClick = { navController.navigate("homepage_screen") }) {
            Text(text = "Home")
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Welcome to the NoteBook page !")

            Spacer(modifier = Modifier.padding(top = 16.dp))

            Button(onClick = { navController.navigate("transaction_screen") }) {
                Text(text = "Transaction")
            }
        }
    }
}