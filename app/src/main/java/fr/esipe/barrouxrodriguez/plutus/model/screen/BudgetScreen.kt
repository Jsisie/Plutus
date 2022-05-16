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

class BudgetScreen {
    @SuppressLint("NotConstructor")
    @Composable
    fun BudgetScreen(navController: NavController, idNoteBook: Int?) {
        Button(modifier = Modifier
            .padding(10.dp),
            onClick = { navController.navigate("notebook_screen/$idNoteBook") }) {
            Text(text = "Return")
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Welcome to the Budget page !")

            Spacer(modifier = Modifier.padding(top = 16.dp))

            Button(onClick = { navController.navigate("transaction_screen/$idNoteBook") }) {
                Text(text = "Transaction")
            }
        }
    }
}