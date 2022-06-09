package fr.esipe.barrouxrodriguez.plutus.view.screen

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.esipe.barrouxrodriguez.plutus.R

import fr.esipe.barrouxrodriguez.plutus.model.entity.NoteBook
import fr.esipe.barrouxrodriguez.plutus.notebookViewModel
import fr.esipe.barrouxrodriguez.plutus.utils.UIUtils
import fr.esipe.barrouxrodriguez.plutus.utils.Converters

class HomePageScreen {
    @SuppressLint("NotConstructor")
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun HomePageScreen(navController: NavController) {
        // List of database notebooks
        val notebooks = notebookViewModel.readAllData.observeAsState(emptyList()).value

        val openAddDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
        val openEditDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
        val openDeleteDialog: MutableState<Boolean> = remember { mutableStateOf(false) }

        val selectedNoteBook: MutableState<NoteBook> = remember {
            mutableStateOf(NoteBook("notebook_screen"))
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
        ) { innerTag ->
            LazyColumn(Modifier.padding(innerTag)) {
                items(notebooks.size) { i ->
                    val noteBook = notebooks[i]

                    Card(elevation = 5.dp, modifier = Modifier.padding(15.dp),
                        onClick = {
                            navController.navigate("notebook_screen/${noteBook.idNotebook}")
                        },
                        content = {
                            Row(
                                Modifier
                                    .fillMaxSize()
                                    .padding(5.dp),

                                ) {
                                Column {
                                    Text(
                                        text = stringResource(id = R.string.title) + ": ${
                                            noteBook.titleNoteBook
                                        }"
                                    )
                                    Text(
                                        text = stringResource(id = R.string.creation_date) + ": ${
                                            noteBook.dateCreation?.let { it1 ->
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
                                        selectedNoteBook.value = noteBook
                                    },
                                    contentPadding = PaddingValues(
                                        start = 2.dp,
                                        top = 5.dp,
                                        end = 2.dp,
                                        bottom = 5.dp
                                    )
                                ) {
                                    Icon(
                                        Icons.Filled.Create,
                                        stringResource(id = R.string.edit_notebook)
                                    )
                                }
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Button(
                                    onClick = {
                                        openDeleteDialog.value = true
                                        selectedNoteBook.value = noteBook
                                    },
                                    contentPadding = PaddingValues(
                                        start = 2.dp,
                                        top = 5.dp,
                                        end = 2.dp,
                                        bottom = 5.dp
                                    )
                                ) {
                                    Icon(
                                        Icons.Filled.Delete,
                                        stringResource(id = R.string.delete_notebook)
                                    )
                                }
                            }
                        })
                }
            }

            // Add notebook
            UIUtils.ShowAlertDialog(
                openDialog = openAddDialog,
                title = stringResource(id = R.string.add_notebook),
                confirmText = stringResource(id = R.string.add),
                dismissText = stringResource(id = R.string.cancel),
                onConfirmClick = { text, isError ->
                    if (text.isEmpty() || text.length > 20) {
                        isError.value = true
                    } else {
                        notebookViewModel.insertAll(NoteBook(text))
                    }
                },
                onErrorText = stringResource(id = R.string.message_size_error_message)
            )

            // Edit dialog
            UIUtils.ShowAlertDialog(
                openDialog = openEditDialog,
                title = stringResource(id = R.string.edit_notebook_name),
                text = selectedNoteBook.value.titleNoteBook,
                confirmText = stringResource(id = R.string.modify_notebook),
                dismissText = stringResource(id = R.string.cancel),
                onConfirmClick = { text, isError ->
                    if (text.isEmpty() || text.length > 20) {
                        isError.value = true
                    } else {
                        notebookViewModel.update(
                            NoteBook(
                                text,
                                dateCreation = selectedNoteBook.value.dateCreation,
                                totalAmount = selectedNoteBook.value.totalAmount,
                                idNotebook = selectedNoteBook.value.idNotebook
                            )
                        )
                    }
                },
                onErrorText = stringResource(id = R.string.message_size_error_message)
            )

            //Delete Notebook
            UIUtils.ShowAlertDialog(
                openDialog = openDeleteDialog,
                title = stringResource(id = R.string.delete_notebook),
                onConfirmClick = { _, _ -> notebookViewModel.delete(selectedNoteBook.value) },
                isTextFieldValue = false
            )
        }
    }
}