package fr.esipe.barrouxrodriguez.plutus

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
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

                }
            }
        }
    }
}

lateinit var ntViewModel: NameTagViewModel
lateinit var transactionViewModel: TransactionViewModel
lateinit var notebookViewModel: NoteBookViewModel


@Composable
fun NoteBookPage() {

}