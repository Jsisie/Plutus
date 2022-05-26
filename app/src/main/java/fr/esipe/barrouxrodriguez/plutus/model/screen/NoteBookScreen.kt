package fr.esipe.barrouxrodriguez.plutus.model.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.esipe.barrouxrodriguez.plutus.R
import fr.esipe.barrouxrodriguez.plutus.model.Converters
import fr.esipe.barrouxrodriguez.plutus.model.entity.Transaction
import fr.esipe.barrouxrodriguez.plutus.notebookViewModel
import fr.esipe.barrouxrodriguez.plutus.transactionViewModel

class NoteBookScreen {

    private val notebookVM = notebookViewModel

    @SuppressLint("NotConstructor")
    @Composable
    fun NoteBookScreen(navController: NavController, idNoteBook: Int?) {
        val openAddDialog: MutableState<Boolean> = remember { mutableStateOf(false) }

        val noteBookWithLists = idNoteBook?.let {
            notebookVM.findNoteBookById(it).observeAsState()
        } ?: return

        Scaffold(
            topBar = {
                TopAppBar(Modifier.fillMaxWidth()) {
                    Column(
                        Modifier
                            .weight(1f / 3f, fill = true),
                    ) {
                        Button(modifier = Modifier
                            .padding(10.dp),
                            onClick =
                            { navController.navigate("homepage_screen") })
                        {
                            Icon(Icons.Filled.ArrowBack,
                                stringResource(id = R.string.create_notebook))
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
                            text = "${noteBookWithLists.value?.noteBook?.titleNoteBook}",
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }
                    Column(Modifier.weight(1f / 3f, fill = true)) { }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        openAddDialog.value = true
                    },
                    // TODO - Move a little bit to the top the "Add" button
//                    Modifier
//                        .paddingFromBaseline(30.dp)
                )
                {
                    // TODO - change in string.xml
                    Icon(Icons.Filled.Add, "Create Transaction")
                }
            },
            isFloatingActionButtonDocked = true,
            bottomBar = {
                BottomAppBar() {
                    Column(modifier = Modifier
                        .weight(1f / 2f, fill = true),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(modifier = Modifier
                            .padding(10.dp),
                            enabled = false,
                            onClick = {
                                navController.navigate("notebook_screen/$idNoteBook")
                            }) {
                            // TODO - change in string.xml
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
                            // TODO - change in string.xml
                            Text(text = "Budget")
                        }
                    }
                }
            }
        ) { innerPadding ->
            Column (Modifier.padding(innerPadding)) {
                    // TODO - change in string.xml
                    Text("date de creation : ${
                        noteBookWithLists.value?.noteBook?.dateCreation?.let { it1 ->
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
                    // TODO - change in string.xml
                    "Transactions :",
                    Modifier.fillMaxWidth(),
                    fontSize = 35.sp,
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
                        noteBookWithLists.value?.listTransaction?.size?.let { it1 ->
                            items(it1) { i ->
                                // TODO - makes this works
                                val transactionList = (i+1)?.let {
                                    transactionViewModel.findTransactionsById(it).observeAsState()
                                }

//                                val transaction = transactionViewModel.findTransactionsById(i)
                                Button(onClick = { navController.navigate("transaction_screen/$idNoteBook") }) {
                                    Text(text = "${transactionList.value?.transaction?.title_transaction}")
                                }
                            }
                        }
                    }
                }
            }
        }
        AddNoteBookDialog(openAddDialog, idNoteBook)
    }

    @Composable
    fun AddNoteBookDialog(openAddDialog: MutableState<Boolean>, idNoteBook: Int) {
        if (openAddDialog.value) {
            val transactionName = remember { mutableStateOf(TextFieldValue("")) }
            val isError = remember { mutableStateOf(false) }

            AlertDialog(
                onDismissRequest = {
                    openAddDialog.value = false
                },
                title = {
                    // TODO - change in string.xml
                    Text(text = "Add transaction")
                },
                text = {
                    Column {
                        TextField(
                            value = transactionName.value,
                            onValueChange = { newText ->
                                isError.value = false
                                if (transactionName.value.text.length < 25) {
                                    transactionName.value = newText
                                }
                            })
                        if (isError.value) {
                            Text(
                                // TODO - change in string.xml
                                text = "Name must not be empty or superior than 20 characters",
                                color = MaterialTheme.colors.error,
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        val text = transactionName.value.text
                        if (text.isEmpty() || text.length > 20) {
                            isError.value = true
                        } else {
                            transactionViewModel.insertAll(Transaction(title_transaction = text,
                                amount_transaction = 0,
                                idNotebook = idNoteBook))
                            openAddDialog.value = false
                        }
                    }) {
                        Text(text = stringResource(id = R.string.add))
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        openAddDialog.value = false
                    }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                }
            )
        }
    }
}