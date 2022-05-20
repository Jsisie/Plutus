package fr.esipe.barrouxrodriguez.plutus.model.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.esipe.barrouxrodriguez.plutus.model.Converters
import fr.esipe.barrouxrodriguez.plutus.notebookViewModel

class NoteBookScreen {

    private val notebookVM = notebookViewModel

    @SuppressLint("NotConstructor")
    @Composable
    fun NoteBookScreen(navController: NavController, idNoteBook: Int?) {
        val noteBookWithLists =
            idNoteBook?.let { notebookVM.findNoteBookById(it).observeAsState() }
                ?: return

        Scaffold(
            topBar = {
                TopAppBar(Modifier.fillMaxWidth()) {
                    Button(modifier = Modifier
                        .padding(10.dp),
                        onClick =
                        { navController.navigate("homepage_screen") })
                    {
                        Text(text = "Home")
                    }
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${noteBookWithLists.value?.noteBook?.titleNoteBook}",
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }
                }
            },
//            floatingActionButton = {
//                FloatingActionButton(onClick = {
////                    openAddDialog.value = true
//                }) {
//                    Icon(Icons.Filled.Add, stringResource(id = R.string.create_notebook))
//                }
//            },
//            isFloatingActionButtonDocked = true,
            bottomBar = {
                BottomAppBar() {
                    Column(modifier = Modifier
                        .weight(1f / 2f, fill = true),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(modifier = Modifier
                            .padding(10.dp),
                            onClick = {
                                navController.navigate("notebook_screen/$idNoteBook")
                            }) {
                            Text(text = "Transaction")
                        }
                    }
                    Column(modifier = Modifier
                        .weight(1f / 2f, fill = true),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(modifier = Modifier
                            .padding(10.dp),
                            onClick = {
                                navController.navigate("budget_screen/$idNoteBook")
                            }) {
                            Text(text = "Budget")
                        }
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Text("Welcome to the NoteBook page !")
                Text("idNoteBook : $idNoteBook")

                Text("NameNoteBook : ${noteBookWithLists.value?.noteBook?.titleNoteBook}")
                Text("DateNoteBook : ${
                    noteBookWithLists.value?.noteBook?.dateCreation?.let { it1 ->
                        Converters.printDate(
                            it1, "yyyy-MM-dd"
                        )
                    }
                }")


                Spacer(modifier = Modifier.padding(top = 16.dp))

                Button(onClick = { navController.navigate("transaction_screen/$idNoteBook") }) {
                    Text(text = "Transaction")
                }
            }
        }
    }
}