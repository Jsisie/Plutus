package fr.esipe.barrouxrodriguez.plutus.model.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.esipe.barrouxrodriguez.plutus.R
import fr.esipe.barrouxrodriguez.plutus.nameTagViewModel

class TransactionScreen {

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @SuppressLint("NotConstructor")
    @Composable
    fun AddNameTagToTransaction(navController: NavController, idNoteBook: Int?) {
        val titleTransaction: MutableState<TextFieldValue> = remember {
            mutableStateOf(
                TextFieldValue("")
            )
        }
        val amountTransaction: MutableState<TextFieldValue> =
            remember { mutableStateOf(TextFieldValue("")) }
        val isTitleError: MutableState<Boolean> = remember { mutableStateOf(false) }
        val isAmountError: MutableState<Boolean> = remember { mutableStateOf(false) }
        val nameTagList: MutableList<String> = remember {
            mutableStateListOf()
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
                            text = stringResource(id = R.string.create_transaction),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (titleTransaction.value.text.isNotEmpty()) {
                            Button(onClick = {
                                // TODO Add transaction
                            }) {
                                Icon(
                                    Icons.Filled.Add,
                                    stringResource(id = R.string.create_transaction)
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier.fillMaxSize()) {
                Row {
                    // TODO - change it in strings.xml
                    Text(text = "Title : ")
                    TextField(
                        modifier = Modifier.padding(3.dp),
                        value = titleTransaction.value,
                        onValueChange = { newText ->
                            isTitleError.value = false
                            if (titleTransaction.value.text.length < 25) {
                                titleTransaction.value = newText
                            }
                        }
                    )
                    if (isTitleError.value) {
                        Text(
                            stringResource(id = R.string.message_size_error_message),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
                Row {
                    // TODO - change it in strings.xml
                    Text(text = "Amount : ")
                    TextField(
                        modifier = Modifier.padding(3.dp),
                        value = amountTransaction.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { newText ->
                            isAmountError.value = false
                            if (amountTransaction.value.text.length < 25) {
                                amountTransaction.value = newText
                            }
                        }
                    )
                    if (isAmountError.value) {
                        Text(
                            stringResource(id = R.string.message_size_error_message),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }

                Box {
                    Column() {
                        Text(text = "Tags:")
                        LazyVerticalGrid(
                            cells = GridCells.Adaptive(150.dp)
                        ) {
                            items(nameTagViewModel.predefTags.size) { i ->
                                val tag = nameTagViewModel.predefTags[i]
                                val selected = tag in nameTagList
                                Card(elevation = 5.dp, modifier = Modifier.padding(2.dp),
                                    onClick = {
                                        if (selected) {
                                            nameTagList.remove(tag)
                                        } else {
                                            nameTagList.add(tag)
                                        }
                                    },
                                    backgroundColor = if (selected) Color.Magenta else Color.LightGray,
                                    content = {
                                        Column(
                                            Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = tag,
                                                textAlign = TextAlign.Center,
                                                fontSize = 24.sp
                                            )
                                        }
                                    })
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("NotConstructor")
    @Composable
    fun TransactionScreen(navController: NavController, idNoteBook: Int?) {
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
            Text("Welcome to the Transaction page !")

            Spacer(modifier = Modifier.padding(top = 16.dp))

            Button(onClick = { navController.navigate("nameTag_screen/$idNoteBook") }) {
                Text(text = "NameTag")
            }
            Button(onClick = { navController.navigate("budget_screen/$idNoteBook") }) {
                Text(text = "Budget")
            }
        }
    }
}
