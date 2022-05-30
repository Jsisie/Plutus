package fr.esipe.barrouxrodriguez.plutus.model.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import fr.esipe.barrouxrodriguez.plutus.model.Converters
import fr.esipe.barrouxrodriguez.plutus.model.entity.NoteBookWithTransactionsAndBudget
import fr.esipe.barrouxrodriguez.plutus.model.entity.TransactionWithNameTags
import fr.esipe.barrouxrodriguez.plutus.notebookViewModel
import fr.esipe.barrouxrodriguez.plutus.transactionViewModel

class NoteBookScreen {
    private val notebookVM = notebookViewModel

    @SuppressLint("NotConstructor")
    @Composable
    fun NoteBookScreen(navController: NavController, idNoteBook: Int?) {
        val openAddDialog: MutableState<Boolean> = remember { mutableStateOf(false) }

        val noteBookWithLists: NoteBookWithTransactionsAndBudget = idNoteBook?.let {
            notebookVM.findNoteBookById(it).observeAsState().value
        } ?: return

        Scaffold(
            topBar = {
                TopAppBar(Modifier.fillMaxWidth()) {
                    Column(
                        Modifier.weight(1f / 3f, fill = true),
                    ) {
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
                            .fillMaxSize()
                            .weight(1f / 3f, fill = true),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = noteBookWithLists.noteBook.titleNoteBook,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }

                    Spacer(Modifier.weight(1f / 3f))
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("add_transaction_screen/$idNoteBook")
                        // openAddDialog.value = true
                    },
                    // TODO - Move a little bit to the top the "Add" button
                    // Modifier.paddingFromBaseline(30.dp)
                )
                {
                    Icon(Icons.Filled.Add, stringResource(id = R.string.create_transaction))
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
                            enabled = false,
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
                            onClick = {
                                navController.navigate("budget_screen/$idNoteBook")
                            }) { Text(stringResource(id = R.string.budget)) }
                    }
                }
            }
        ) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
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
                    // TODO - change it in strings.xml
                    text = String.format("%.2f €", noteBookWithLists.listTransaction.stream()
                        .mapToDouble { transaction -> transaction.transaction.amount_transaction.toDouble() }
                        ?.sum()?.toFloat()),
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    stringResource(id = R.string.transaction),
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                )
                {
                    LazyColumn {
                        noteBookWithLists.listTransaction.size.let { it ->
                            items(it) { i ->
                                val transaction: TransactionWithNameTags =
                                    noteBookWithLists.listTransaction[i]
                                Button(modifier = Modifier.padding(5.dp),
                                    onClick = { navController.navigate("transaction_screen/${transaction.transaction.idTransaction}") }) {
                                    Column {
                                        Text(text = transaction.transaction.title_transaction)
                                        // TODO - change it in strings.xml
                                        Text(text = "${transaction.transaction.amount_transaction} €")
                                        Text(
                                            text = Converters.printDate(
                                                transaction.transaction.date_transaction,
                                                "yyyy-MM-dd"
                                            )
                                        )
                                        Button(onClick = {
                                            transactionViewModel.delete(transaction)
                                        }) {
                                            Icon(
                                                Icons.Filled.Delete,
                                                contentDescription = "yeetusDeletusTransactius"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}