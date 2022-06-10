package fr.esipe.barrouxrodriguez.plutus.view.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import fr.esipe.barrouxrodriguez.plutus.R
import fr.esipe.barrouxrodriguez.plutus.filterViewModel
import fr.esipe.barrouxrodriguez.plutus.model.entity.Filter
import fr.esipe.barrouxrodriguez.plutus.model.entity.FilterWithNameTags
import fr.esipe.barrouxrodriguez.plutus.model.entity.NameTag
import fr.esipe.barrouxrodriguez.plutus.notebookViewModel

class FilterScreen {

    private val notebookVM = notebookViewModel

    @SuppressLint("NotConstructor")
    @Composable
    fun FilterScreen(navController: NavController, idNoteBook: Int, idFilter: Int?) {

        val listFilter: List<FilterWithNameTags>? = filterViewModel.readAllData.observeAsState().value

        var selectedFilterId: MutableState<FilterWithNameTags>? = null
        val maybe: FilterWithNameTags?

        if (listFilter != null) {
            maybe =
                idFilter?.let { listFilter.filter { transaction -> transaction.filter.idFilter == idFilter } }
                    ?.toList()?.get(0)
            maybe?.let { selectedFilterId = remember {
                mutableStateOf(maybe)
            } }
        }

        if(selectedFilterId == null){
            selectedFilterId = remember {
                mutableStateOf(FilterWithNameTags( Filter("", null, null, null, null, null, null), emptyList<NameTag>()))
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
                            navController.navigate("notebook_screen/${idNoteBook}_${selectedFilterId!!.value}")
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
                Row() {
                    Text("Titre Contient: ")
                    TextField(value = "", onValueChange = {newText -> newText})
                }
                Row() {
                    Text("Montant >")
                    TextField(value = "", onValueChange = {newText -> newText})
                    Text("==")
                    TextField(value = "", onValueChange = {newText -> newText})
                    Text("<")
                    TextField(value = "", onValueChange = {newText -> newText})
                }
            }
        }
    }
}