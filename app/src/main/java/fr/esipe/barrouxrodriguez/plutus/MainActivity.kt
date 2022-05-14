package fr.esipe.barrouxrodriguez.plutus

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.esipe.barrouxrodriguez.plutus.model.Converters
import fr.esipe.barrouxrodriguez.plutus.model.entity.NoteBook
import fr.esipe.barrouxrodriguez.plutus.model.viewmodel.NameTagViewModel
import fr.esipe.barrouxrodriguez.plutus.model.viewmodel.NoteBookViewModel
import fr.esipe.barrouxrodriguez.plutus.model.viewmodel.TransactionViewModel
import fr.esipe.barrouxrodriguez.plutus.ui.theme.PlutusTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlutusTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val context = LocalContext.current

                    ntViewModel = viewModel(
                        factory =
                        NameTagViewModel.NameTagModelFactory(
                            context.applicationContext as Application
                        )
                    )

                    transactionViewModel = viewModel(
                        factory =
                        TransactionViewModel.TransactionModelFactory(
                            context.applicationContext as Application
                        )
                    )
                    notebookViewModel = viewModel(
                        factory =
                        NoteBookViewModel.NoteBookModelFactory(
                            context.applicationContext as Application
                        )
                    )
                    NoteBookDisplay()
                }
            }
        }
    }
}

lateinit var ntViewModel: NameTagViewModel
lateinit var transactionViewModel: TransactionViewModel
lateinit var notebookViewModel: NoteBookViewModel


@Composable
fun NoteBookDisplay() {
    // List of database notebooks
    val notebooks = notebookViewModel.readAllData.observeAsState(emptyList()).value

    val openAddDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
    val openEditDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
    val openDeleteDialog: MutableState<Boolean> = remember { mutableStateOf(false) }

    val selectedNoteBook: MutableState<NoteBook> = remember {
        mutableStateOf(NoteBook("placeholder"))
    }

    Scaffold(
        topBar = {
            TopAppBar(Modifier.fillMaxWidth()) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
            }
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {
                openAddDialog.value = true
            }) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.create_notebook))
            }
        },
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(
                // Defaults to null, that is, No cutout
                cutoutShape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50)
                )
            ) {

            }
        }
    ) {

        LazyColumn {
            items(notebooks.size) { i ->
                Card(elevation = 5.dp, modifier = Modifier.padding(15.dp)) {
                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                    ) {
                        Column {
                            Text(text = stringResource(id = R.string.title) + ": ${
                                notebooks[i].titleNoteBook
                            }")
                            Text(
                                text = stringResource(id = R.string.creation_date) + ": ${
                                    notebooks[i].dateCreation?.let { it1 ->
                                        Converters.printDate(
                                            it1, "yyyy-MM-dd"
                                        )
                                    }
                                }"
                            )
                        }
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Button(
                            onClick = {
                                openEditDialog.value = true
                                selectedNoteBook.value = notebooks[i]
                            },
                            contentPadding = PaddingValues(
                                start = 2.dp,
                                top = 5.dp,
                                end = 2.dp,
                                bottom = 5.dp
                            )
                        ) {
                            Icon(Icons.Filled.Create, stringResource(id = R.string.edit_notebook))
                        }
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Button(
                            onClick = {
                                openDeleteDialog.value = true
                                selectedNoteBook.value = notebooks[i]
                            },
                            contentPadding = PaddingValues(
                                start = 2.dp,
                                top = 5.dp,
                                end = 2.dp,
                                bottom = 5.dp
                            )
                        ) {
                            Icon(Icons.Filled.Delete, stringResource(id = R.string.delete_notebook))
                        }
                    }
                }
            }
        }

        AddNoteBookDialog(openAddDialog)
        EditNoteBookDialog(openEditDialog, selectedNoteBook.value)
        DeleteNoteBookDialog(openDeleteDialog, selectedNoteBook.value)
    }
}

@Composable
fun AddNoteBookDialog(openAddDialog: MutableState<Boolean>) {
    if (openAddDialog.value) {
        val notebookName = remember { mutableStateOf(TextFieldValue("")) }
        AlertDialog(
            onDismissRequest = {
                openAddDialog.value = false
            },
            title = {
                Text(text = stringResource(id = R.string.add_notebook))
            },
            text = {
                TextField(
                    value = notebookName.value,
                    onValueChange = { newText -> notebookName.value = newText })
            },
            confirmButton = {
                Button(onClick = {
                    notebookViewModel.insertAll(NoteBook(notebookName.value.text))
                    openAddDialog.value = false
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

@Composable
fun DeleteNoteBookDialog(openDialog: MutableState<Boolean>, notebook: NoteBook) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = stringResource(id = R.string.delete_notebook))
            },
            text = {
                Text(stringResource(id = R.string.confirmation_delete_notebook))
            },
            confirmButton = {
                Button(onClick = {
                    notebookViewModel.delete(notebook)
                    openDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                Button(onClick = {
                    openDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.no))
                }
            }
        )
    }
}

@Composable
fun EditNoteBookDialog(openDialog: MutableState<Boolean>, notebook: NoteBook) {
    if (openDialog.value) {
        val notebookName = remember { mutableStateOf(TextFieldValue(notebook.titleNoteBook)) }
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = stringResource(id = R.string.edit_notebook_name))
            },
            text = {
                TextField(
                    value = notebookName.value,
                    onValueChange = { newText -> notebookName.value = newText })
            },
            confirmButton = {
                Button(onClick = {

                    notebookViewModel.update(
                        NoteBook(
                            notebookName.value.text,
                            dateCreation = notebook.dateCreation,
                            idNotebook = notebook.idNotebook
                        )
                    )
                    openDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.modify_notebook))
                }
            },
            dismissButton = {
                Button(onClick = {
                    openDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        )
    }
}
