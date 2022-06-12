package fr.esipe.barrouxrodriguez.plutus.view.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import fr.esipe.barrouxrodriguez.plutus.filterViewModel
import fr.esipe.barrouxrodriguez.plutus.model.entity.Filter
import fr.esipe.barrouxrodriguez.plutus.model.entity.FilterWithNameTags
import fr.esipe.barrouxrodriguez.plutus.model.entity.NameTag
import fr.esipe.barrouxrodriguez.plutus.nameTagViewModel
import fr.esipe.barrouxrodriguez.plutus.utils.UIUtils
import kotlin.streams.toList

class FilterScreen {

    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("NotConstructor")
    @Composable
    fun FilterScreen(navController: NavController, idNoteBook: Int, idFilter: Int?) {

        val listFilter: List<FilterWithNameTags>? =
            filterViewModel.readAllData.observeAsState().value

        var selectedFilterId: MutableState<FilterWithNameTags>? = null
        val maybe: FilterWithNameTags?

        if (listFilter != null) {
            maybe =
                idFilter?.let { listFilter.filter { transaction -> transaction.filter.idFilter == idFilter } }
                    ?.toList()?.get(0)
            maybe?.let {
                selectedFilterId = remember {
                    mutableStateOf(maybe)
                }
            }
        }

        val actualTagMap: MutableMap<NameTag, NameTag> = remember {
            mutableStateMapOf()
        }

        val titreContient: MutableState<TextFieldValue> = remember {
            mutableStateOf(TextFieldValue(""))
        }

        val amountSuperior: MutableState<TextFieldValue> = remember {
            mutableStateOf(TextFieldValue(""))
        }

        val amountEquals: MutableState<TextFieldValue> = remember {
            mutableStateOf(TextFieldValue(""))
        }

        val amountInferior: MutableState<TextFieldValue> = remember {
            mutableStateOf(TextFieldValue(""))
        }

        val customTags = nameTagViewModel.readAllCustomPredefined.observeAsState(emptyList()).value

        if (selectedFilterId == null) {
            selectedFilterId = remember {
                mutableStateOf(
                    FilterWithNameTags(
                        Filter("", null, null, null, null, null, null),
                        emptyList()
                    )
                )
            }
        }
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
                            text = stringResource(id = R.string.transaction),
                            textAlign = TextAlign.Center,
                        )
                    }

                    Box(Modifier.weight(1f / 5f)) {
                        Button(modifier = Modifier.padding(10.dp), onClick = {
                            val title = titreContient.value.text.ifEmpty { null }
                            val amountS = amountSuperior.value.text.ifEmpty { null }?.toFloat()
                            val amountE = amountEquals.value.text.ifEmpty { null }?.toFloat()
                            val amountI = amountInferior.value.text.ifEmpty { null }?.toFloat()

                            val newFilter = Filter(
                                title = title, amountSuperior = amountS,
                                amountEqual = amountE, amountInferior = amountI
                            )

                            filterViewModel.insertWithNameTags(
                                newFilter,
                                actualTagMap.keys.toList()
                            )
                            navController.navigate("filter_screen_choice/${idNoteBook}")
                        })
                        {
                            Icon(
                                Icons.Filled.Check,
                                stringResource(id = R.string.create_notebook)
                            )
                        }
                    }
                }
            },
            bottomBar = {}
        ) {
            Column(Modifier.fillMaxWidth()) {
                Column(Modifier.weight(0.3f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // TODO - change it in strings.xml
                        Text(
                            modifier = Modifier.weight(1 / 3f),
                            text = "Title contient: ",
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.weight(1 / 8f))
                        TextField(
                            modifier = Modifier
                                .padding(3.dp)
                                .weight(5 / 6f),
                            value = titreContient.value,
                            onValueChange = { newText ->
                                titreContient.value = newText
                            }
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // TODO - change it in strings.xml
                        Text("Montant >", modifier = Modifier.weight(2 / 7f))
                        TextField(
                            modifier = Modifier.weight(1 / 7f),
                            value = amountSuperior.value,
                            onValueChange = { newText -> amountSuperior.value = newText },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        )
                        Text("==", modifier = Modifier.weight(1 / 7f))
                        TextField(
                            modifier = Modifier.weight(1 / 7f),
                            value = amountEquals.value,
                            onValueChange = { newText -> amountEquals.value = newText },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        )
                        Text("<", modifier = Modifier.weight(1 / 7f))
                        TextField(
                            modifier = Modifier.weight(1 / 7f),
                            value = amountInferior.value,
                            onValueChange = { newText -> amountInferior.value = newText },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        )
                    }
                }
                Column(Modifier.weight(0.7f)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1 / 3f)
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Transaction's tags:",
                                textAlign = TextAlign.Center
                            )
                            LazyHorizontalGrid(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                rows = GridCells.Fixed(2)
                            ) {
                                items(actualTagMap.keys.stream().sorted().toList()) { tag ->
                                    Card(
                                        modifier = Modifier
                                            .width(120.dp)
                                            .height(45.dp)
                                            .padding(2.dp),
                                        elevation = 5.dp,
                                        onClick = {
                                            actualTagMap.remove(tag)
                                        },
                                        backgroundColor = Color.LightGray,
                                        content = {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = tag.titleNameTag,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 15.sp,
                                                    color = Color.Black
                                                )
                                            }
                                        })
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1 / 3f)
                    ) {
                        UIUtils.ShowListOfNameTags(
                            title = "Predefined Tags",
                            tags = nameTagViewModel.predefTags,
                            onTap = { tag ->
                                val newTag = NameTag(tag.titleNameTag, null, false)
                                actualTagMap[newTag] = newTag
                            },
                            color = { tag ->
                                if (tag in actualTagMap.keys) Color(
                                    66,
                                    66,
                                    66,
                                    128
                                ) else Color.Black
                            }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1 / 3f)
                    ) {

                        UIUtils.ShowListOfNameTags(
                            title = "Custom Tags",
                            tags = customTags,
                            onLongPress = { tag ->
                                nameTagViewModel.delete(tag)
                            },
                            onTap = { tag ->
                                val newTag = NameTag(tag.titleNameTag, null, false)
                                actualTagMap[newTag] = newTag
                            },
                            color = { tag ->
                                if (tag in actualTagMap.keys) Color(
                                    66,
                                    66,
                                    66,
                                    128
                                ) else Color.Black
                            }
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ShowListOfFilter(navController: NavController, idNoteBook: Int) {
        val filterList = filterViewModel.readAllData.observeAsState(emptyList()).value

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
                            text = "Choose a filter",
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navController.navigate("filter_screen/${idNoteBook}")
                }) {
                    Icon(Icons.Filled.Add, "Create Filter")
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
        ) { innerPadding ->
            LazyColumn(Modifier
                .padding(innerPadding)
                .padding(6.dp)) {
                items(filterList.reversed()) { filter ->
                    Card(
                        onClick = {
                            navController.navigate("notebook_screen/${idNoteBook}_${filter.filter.idFilter}")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp),
                        elevation = 5.dp,
                        backgroundColor = Color.LightGray
                    ) {
                        Text(text = filter.toString())
                    }
                }
            }
        }
    }
}