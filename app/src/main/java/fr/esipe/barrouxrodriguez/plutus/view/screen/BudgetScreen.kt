package fr.esipe.barrouxrodriguez.plutus.view.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.esipe.barrouxrodriguez.plutus.R
import fr.esipe.barrouxrodriguez.plutus.model.entity.NoteBookWithTransactionsAndBudget
import fr.esipe.barrouxrodriguez.plutus.notebookViewModel
import fr.esipe.barrouxrodriguez.plutus.utils.Converters


class BudgetScreen {

    private val notebookVM = notebookViewModel

    @SuppressLint("NotConstructor")
    @Composable
    fun BudgetScreen(navController: NavController, idNoteBook: Int?) {
        Button(modifier = Modifier
            .padding(10.dp),
            onClick = { navController.navigate("notebook_screen/$idNoteBook") }) {
            Text(text = "Return")
        }

        val noteBookWithLists: NoteBookWithTransactionsAndBudget = idNoteBook?.let {
            notebookVM.findNoteBookById(it).observeAsState().value
        } ?: return

        val openNameTagDialog: MutableState<Boolean> = remember {
            mutableStateOf(false)
        }

        Scaffold(
            topBar = {
                TopAppBar(Modifier.fillMaxWidth()) {
                    Column {
                        Button(
                            modifier = Modifier.padding(10.dp),
                            onClick = { navController.navigate("homepage_screen") })
                        {
                            Icon(
                                Icons.Filled.ArrowBack,
                                stringResource(id = R.string.create_notebook)
                            )
                        }
                    }
                    Column(
                        Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = noteBookWithLists.noteBook.titleNoteBook,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    openNameTagDialog.value = true
                }) {
                    Icon(Icons.Filled.Add, stringResource(id = R.string.create_name_tag))
                }
            },
            isFloatingActionButtonDocked = true,
            bottomBar = {
                BottomAppBar(
                    cutoutShape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50))
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f / 2f, fill = true),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(modifier = Modifier
                            .padding(10.dp),
                            onClick = {
                                navController.navigate("notebook_screen/$idNoteBook")
                            }) { Text(stringResource(id = R.string.transaction)) }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f / 2f, fill = true),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(modifier = Modifier
                            .padding(10.dp),
                            enabled = false,
                            onClick = {
                                navController.navigate("budget_screen/$idNoteBook")
                            }) { Text(stringResource(id = R.string.budget)) }
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
            ) {
                Text("${stringResource(id = R.string.creation_date)} : ${
                    noteBookWithLists.noteBook.dateCreation?.let { it1 ->
                        Converters.printDate(
                            it1, "yyyy-MM-dd"
                        )
                    }
                }",
                    Modifier.fillMaxWidth(),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Right
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = String.format("%.2f €", noteBookWithLists.listTransaction.stream()
                        .mapToDouble { transaction -> transaction.transaction.amount_transaction.toDouble() }
                        ?.sum()?.toFloat()),
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    stringResource(id = R.string.budget),
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )

                // Display all budgets
//                Column(
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    LazyColumn {
//                        items(
//                            noteBookWithLists.listBudget.toList()
//                        ) { budget ->
//                            Card(modifier = Modifier
//                                .padding(5.dp)
//                                .fillMaxWidth(),
//                                elevation = 5.dp
//                            ) {
//                                Row(Modifier.fillMaxWidth()) {
//
//                                }
//                            }
//                        }
//                    }
//                }
            }
        }
    }
}