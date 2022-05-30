package fr.esipe.barrouxrodriguez.plutus.model.screen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.esipe.barrouxrodriguez.plutus.R
import fr.esipe.barrouxrodriguez.plutus.model.Converters
import fr.esipe.barrouxrodriguez.plutus.model.entity.NameTag
import fr.esipe.barrouxrodriguez.plutus.model.entity.Transaction
import fr.esipe.barrouxrodriguez.plutus.model.entity.TransactionWithNameTags
import fr.esipe.barrouxrodriguez.plutus.model.utils.AlertDialogUtil
import fr.esipe.barrouxrodriguez.plutus.nameTagViewModel
import fr.esipe.barrouxrodriguez.plutus.transactionViewModel
import java.util.*
import kotlin.streams.toList

class TransactionScreen {

    @OptIn(
        ExperimentalMaterialApi::class, ExperimentalFoundationApi::class,
        ExperimentalGraphicsApi::class
    )
    @SuppressLint("NotConstructor")
    @Composable
    fun AddNameTagToTransaction(navController: NavController, idNoteBook: Int?) {
        val titleTransaction: MutableState<TextFieldValue> = remember {
            mutableStateOf(
                TextFieldValue("")
            )
        }
        val descriptionTransaction: MutableState<TextFieldValue> = remember {
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
        val openNameTagDialog: MutableState<Boolean> = remember {
            mutableStateOf(false)
        }
        val customTags = nameTagViewModel.readAllPredefined.observeAsState(emptyList()).value

        var year: Int
        var month: Int
        var day: Int

        val calendar = Calendar.getInstance()

        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.time = Date()

        // Declaring a string value to
        // store date in string format
        val date = remember { mutableStateOf(Converters.printDate(calendar.time, "yyyy-MM-dd")) }        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)
        val mDatePickerDialog = DatePickerDialog(
            LocalContext.current,
            { _: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                date.value = "$mYear/$mDay/${mMonth + 1}"
                year = mYear
                month = mMonth
                day = mDay
            }, year, month, day
        )

        Scaffold(
            topBar = {
                TopAppBar(Modifier.fillMaxWidth()) {
                    Box(Modifier.weight(1f / 5f, fill = true)) {
                        Button(
                            modifier = Modifier.padding(10.dp),
                            onClick = { navController.navigate("notebook_screen/$idNoteBook") })
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
                            .weight(3f / 5f, fill = true),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.create_transaction),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }

                    Box(Modifier.weight(1f / 5f)) {
                        if (titleTransaction.value.text.isNotEmpty() && amountTransaction.value.text.isNotEmpty() && date.value != "Pick a Date") {
                            Button(modifier = Modifier.padding(10.dp), onClick = {
                                Log.d("nt", "$year $month $day")
                                calendar.set(year, month, day)
                                Log.d("aled", "$calendar.time")
                                val transaction = idNoteBook?.let {
                                    Transaction(
                                        title_transaction = titleTransaction.value.text,
                                        amount_transaction = amountTransaction.value.text.toFloat(),
                                        date_transaction = calendar.time,
                                        description_transaction = descriptionTransaction.value.text,
                                        idNotebook = it
                                    )
                                }
                                if (transaction != null) {
                                    transactionViewModel.insertWithNameTags(
                                        transaction,
                                        nameTagList
                                    )
                                }
                                navController.navigate("notebook_screen/$idNoteBook")
                            })
                            {
                                Icon(
                                    Icons.Filled.Check,
                                    stringResource(id = R.string.create_notebook)
                                )
                            }
                        }
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
            Column(modifier = Modifier.fillMaxSize()) {
                ShowTransactionContent(
                    titleTransaction,
                    isTitleError,
                    amountTransaction,
                    descriptionTransaction,
                    isAmountError,
                    mDatePickerDialog,
                    date
                )

                Box {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        //TODO string.xml
                        Text(text = "Predefined Tags:", textAlign = TextAlign.Center)
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
                                    backgroundColor = if (selected) Color(
                                        67,
                                        193,
                                        215
                                    ) else Color.LightGray,
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

                        Text(text = "Custom Tags:", textAlign = TextAlign.Center)
                        LazyVerticalGrid(
                            cells = GridCells.Adaptive(150.dp)
                        ) {
                            items(customTags.size) { i ->
                                val tag = customTags[i]
                                val selected = tag.titleNameTag in nameTagList
                                Card(elevation = 5.dp, modifier = Modifier.padding(2.dp),
                                    onClick = {
                                        if (selected) {
                                            nameTagList.remove(tag.titleNameTag)
                                        } else {
                                            nameTagList.add(tag.titleNameTag)
                                        }
                                    },
                                    backgroundColor = if (selected) Color(
                                        67,
                                        193,
                                        215
                                    ) else Color.LightGray,
                                    content = {
                                        Column(
                                            Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Row(
                                                Modifier.fillMaxSize(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    modifier = Modifier
                                                        .weight(4 / 6f)
                                                        .fillMaxSize()
                                                        .padding(5.dp),
                                                    text = tag.titleNameTag,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 24.sp
                                                )
                                                Button(
                                                    modifier = Modifier
                                                        .weight(2 / 6f)
                                                        .padding(10.dp),
                                                    onClick = {
                                                        nameTagViewModel.delete(tag)
                                                    }) {
                                                    Icon(
                                                        Icons.Filled.Delete,
                                                        stringResource(id = R.string.delete_notebook)
                                                    )
                                                }
                                            }
                                        }
                                    })
                            }
                        }
                    }
                }

                AlertDialogUtil.ShowAlertDialog(
                    openDialog = openNameTagDialog,
                    title = stringResource(id = R.string.create_name_tag),
                    confirmText = stringResource(id = R.string.add),
                    dismissText = stringResource(id = R.string.cancel),
                    onConfirmClick = { text, isError ->
                        if (text.isEmpty() || text.length > 20) {
                            isError.value = true
                        } else {
                            val nameTag = NameTag(text, null, true)
                            nameTagViewModel.insertAll(nameTag)
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun ShowTransactionContent(
        titleTransaction: MutableState<TextFieldValue>,
        isTitleError: MutableState<Boolean>,
        amountTransaction: MutableState<TextFieldValue>,
        descriptionTransaction: MutableState<TextFieldValue>,
        isAmountError: MutableState<Boolean>,
        mDatePickerDialog: DatePickerDialog,
        date: MutableState<String>
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // TODO - change it in strings.xml
            Text(
                modifier = Modifier.weight(1 / 3f),
                text = "Title : ",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1 / 8f))
            TextField(
                modifier = Modifier
                    .padding(3.dp)
                    .weight(5 / 6f),
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            // TODO - change it in strings.xml
            Text(
                modifier = Modifier.weight(1 / 3f),
                text = "Amount : ",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1 / 8f))
            TextField(
                modifier = Modifier
                    .padding(3.dp)
                    .weight(5 / 6f),
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            // TODO - change it in strings.xml
            Text(
                modifier = Modifier.weight(1 / 3f),
                text = "Description : ",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1 / 8f))
            TextField(
                modifier = Modifier
                    .padding(3.dp)
                    .weight(5 / 6f),
                value = descriptionTransaction.value,
                onValueChange = { newText ->
                    descriptionTransaction.value = newText
                }
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            // TODO - change it in strings.xml
            Text(
                modifier = Modifier.weight(1 / 5f),
                text = "Date : ",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1 / 5f))
            Button(modifier = Modifier.weight(2 / 5f), onClick = {
                mDatePickerDialog.show()
            }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))) {
                Text(text = date.value, color = Color.White)
            }
            Spacer(modifier = Modifier.weight(1 / 5f))
        }
    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
    @SuppressLint("NotConstructor")
    @Composable
    fun TransactionScreen(navController: NavController, idTransaction: Int?) {
        val transactionWithNameTags: TransactionWithNameTags =
            idTransaction?.let {
                transactionViewModel.findTransactionsById(it).observeAsState().value
            } ?: return

        val transaction = transactionWithNameTags.transaction

        val idNoteBook = transaction.idNotebook;

        val titleTransaction: MutableState<TextFieldValue> = remember {
            mutableStateOf(
                TextFieldValue(transaction.title_transaction)
            )
        }
        val descriptionTransaction: MutableState<TextFieldValue> = remember {
            mutableStateOf(
                TextFieldValue(transaction.description_transaction)
            )
        }
        val amountTransaction: MutableState<TextFieldValue> =
            remember { mutableStateOf(TextFieldValue(transaction.amount_transaction.toString())) }
        val isTitleError: MutableState<Boolean> = remember { mutableStateOf(false) }
        val isAmountError: MutableState<Boolean> = remember { mutableStateOf(false) }

        val nameTagList: MutableList<String> = remember {
            mutableStateListOf()
        }

        // FIXME Will create bug if remove every nameTag
        if(nameTagList.isEmpty()) {
            nameTagList.addAll(
                transactionWithNameTags.nameTags.stream().map { nameTag -> nameTag.titleNameTag }
                    .toList()
            )
        }

        val openNameTagDialog: MutableState<Boolean> = remember {
            mutableStateOf(false)
        }
        val customTags = nameTagViewModel.readAllPredefined.observeAsState(emptyList()).value

        val year: Int
        val month: Int
        val day: Int

        val calendar = Calendar.getInstance()

        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.time = transaction.date_transaction

        val date = remember {
            mutableStateOf(
                Converters.printDate(
                    transaction.date_transaction,
                    "yyyy-MM-dd"
                )
            )
        }
        val mDatePickerDialog = DatePickerDialog(
            LocalContext.current,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                date.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            }, year, month, day
        )

        Scaffold(
            topBar = {
                TopAppBar(Modifier.fillMaxWidth()) {
                    Box(Modifier.weight(1f / 5f, fill = true)) {
                        Button(
                            modifier = Modifier.padding(10.dp),
                            onClick = { navController.navigate("notebook_screen/$idNoteBook") })
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
                            .weight(3f / 5f, fill = true),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.create_transaction),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }

                    Box(Modifier.weight(1f / 5f)) {
                        if (titleTransaction.value.text.isNotEmpty() && amountTransaction.value.text.isNotEmpty() && date.value != "Pick a Date") {
                            Button(modifier = Modifier.padding(10.dp), onClick = {
                                calendar.set(year, month, day)
                                /*
                                val transaction = idNoteBook?.let {
                                    Transaction(
                                        title_transaction = titleTransaction.value.text,
                                        amount_transaction = Integer.parseInt(amountTransaction.value.text),
                                        date_transaction = calendar.time,
                                        idNotebook = it
                                    )
                                }
                                if (transaction != null) {
                                    transactionViewModel.insertWithNameTags(
                                        transaction,
                                        nameTagList
                                    )
                                }
                                */
                                navController.navigate("notebook_screen/$idNoteBook")
                            })
                            {
                                Icon(
                                    Icons.Filled.Check,
                                    stringResource(id = R.string.create_notebook)
                                )
                            }
                        }
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
            Column(modifier = Modifier.fillMaxSize()) {
                ShowTransactionContent(
                    titleTransaction,
                    isTitleError,
                    amountTransaction,
                    descriptionTransaction,
                    isAmountError,
                    mDatePickerDialog,
                    date
                )
                Box {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        //TODO string.xml
                        Text(text = "Predefined Tags:", textAlign = TextAlign.Center)
                        LazyVerticalGrid(
                            cells = GridCells.Adaptive(150.dp)
                        ) {
                            Log.d("aled", "${nameTagList.size}")
                            items(nameTagList.size) { i ->
                                val tag = nameTagList[i]
                                Card(elevation = 5.dp, modifier = Modifier.padding(2.dp),
                                    onClick = {
                                        /* TODO remove the card, change list to set*/
                                    },
                                    backgroundColor = Color.LightGray,
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
                        /*
                        //TODO string.xml
                        Text(text = "Predefined Tags:", textAlign = TextAlign.Center)
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
                                    backgroundColor = if (selected) Color(
                                        67,
                                        193,
                                        215
                                    ) else Color.LightGray,
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

                        Text(text = "Custom Tags:", textAlign = TextAlign.Center)
                        LazyVerticalGrid(
                            cells = GridCells.Adaptive(150.dp)
                        ) {
                            items(customTags.size) { i ->
                                val tag = customTags[i]
                                val selected = tag.titleNameTag in nameTagList
                                Card(elevation = 5.dp, modifier = Modifier.padding(2.dp),
                                    onClick = {
                                        if (selected) {
                                            nameTagList.remove(tag.titleNameTag)
                                        } else {
                                            nameTagList.add(tag.titleNameTag)
                                        }
                                    },
                                    backgroundColor = if (selected) Color(
                                        67,
                                        193,
                                        215
                                    ) else Color.LightGray,
                                    content = {
                                        Column(
                                            Modifier.fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Row(
                                                Modifier.fillMaxSize(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    modifier = Modifier
                                                        .weight(4 / 6f)
                                                        .fillMaxSize()
                                                        .padding(5.dp),
                                                    text = tag.titleNameTag,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 24.sp
                                                )
                                                Button(
                                                    modifier = Modifier
                                                        .weight(2 / 6f)
                                                        .padding(10.dp),
                                                    onClick = {
                                                        nameTagViewModel.delete(tag)
                                                    }) {
                                                    Icon(
                                                        Icons.Filled.Delete,
                                                        stringResource(id = R.string.delete_notebook)
                                                    )
                                                }
                                            }
                                        }
                                    })
                            }
                        }
                    }
                    */
                    }

                    AlertDialogUtil.ShowAlertDialog(
                        openDialog = openNameTagDialog,
                        title = stringResource(id = R.string.create_name_tag),
                        confirmText = stringResource(id = R.string.add),
                        dismissText = stringResource(id = R.string.cancel),
                        onConfirmClick = { text, isError ->
                            if (text.isEmpty() || text.length > 20) {
                                isError.value = true
                            } else {
                                val nameTag = NameTag(text, null, true)
                                nameTagViewModel.insertAll(nameTag)
                            }
                        }
                    )
                }
            }
        }
    }
}
