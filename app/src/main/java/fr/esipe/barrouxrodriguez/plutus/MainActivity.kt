package fr.esipe.barrouxrodriguez.plutus

import android.app.Application
import android.os.Bundle
import android.util.Log
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
import fr.esipe.barrouxrodriguez.plutus.model.NameTagViewModel
import fr.esipe.barrouxrodriguez.plutus.model.entity.NameTag
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
                    val NTviewModel: NameTagViewModel = viewModel(
                        factory =
                        NameTagViewModel.NameTagModelFactory(
                            context.applicationContext as Application
                        )
                    )

                    val items: List<NameTag> =
                        NTviewModel.readAllData.observeAsState(emptyList()).value

                    Log.i("test", "aled ?")
                    Log.i("test", items.toString())
                    Greeting(items, NTviewModel = NTviewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(items: List<NameTag>, NTviewModel: NameTagViewModel) {
    Column {
        Button(modifier = Modifier.fillMaxWidth(),
            onClick = {
                NTviewModel.insertAll(NameTag("Bonjour", null))
            }) {
            Text(text = "waoua")
        }

        Text(text = items.toString())
    }
}