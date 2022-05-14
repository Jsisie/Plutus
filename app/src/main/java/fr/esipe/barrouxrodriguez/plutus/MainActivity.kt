package fr.esipe.barrouxrodriguez.plutus

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.esipe.barrouxrodriguez.plutus.model.entity.NameTag
import fr.esipe.barrouxrodriguez.plutus.model.entity.Transaction
import fr.esipe.barrouxrodriguez.plutus.model.entity.TransactionWithNameTags
import fr.esipe.barrouxrodriguez.plutus.model.viewmodel.NameTagViewModel
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
                        TransactionViewModel.TransactionViewModelFactory(
                            context.applicationContext as Application
                        )
                    )

                    val items: List<TransactionWithNameTags> =
                        transactionViewModel.readAllData.observeAsState(
                            emptyList()
                        ).value

                    transactionViewModel.insertAll(
                        Transaction(
                            1,
                            "Remboursement Eric",
                            amount_transaction = 500,
                            idNotebook = null
                        )
                    )

                    DisplayNoteBook(items)
                }
            }
        }
    }
}

lateinit var ntViewModel: NameTagViewModel
lateinit var transactionViewModel: TransactionViewModel

@Composable
fun DisplayNoteBook(items: List<TransactionWithNameTags>) {
    Column {
        Button(modifier = Modifier.fillMaxWidth(),
            onClick = {
                ntViewModel.insertAll(NameTag("sdfgsdfgsdfg", 1))
            }) {
            Text(text = "waoua")
        }

        Text(text = items.toString())
    }
}