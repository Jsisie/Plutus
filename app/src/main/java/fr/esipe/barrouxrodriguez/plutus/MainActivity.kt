package fr.esipe.barrouxrodriguez.plutus

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.esipe.barrouxrodriguez.plutus.model.screen.*
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
                    Log.d("aled", "aled")
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

                    nameTagViewModel = viewModel(
                        factory =
                        NameTagViewModel.NameTagModelFactory(
                            context.applicationContext as Application
                        )
                    )

                    NavigationBasicsApp()
                }
            }
        }
    }
}

lateinit var ntViewModel: NameTagViewModel
lateinit var transactionViewModel: TransactionViewModel
lateinit var notebookViewModel: NoteBookViewModel
lateinit var nameTagViewModel: NameTagViewModel

@Composable
fun NavigationBasicsApp() {
    val navController = rememberNavController()
    val noteBookScreen = NoteBookScreen()
    val homePageScreen = HomePageScreen()
    val transactionScreen = TransactionScreen()
    val budgetScreen = BudgetScreen()
    val nameTagScreen = NameTagScreen()

    NavHost(navController = navController, startDestination = "homepage_screen") {
        composable("homepage_screen") { homePageScreen.HomePageScreen(navController) }

        composable("notebook_screen/{idNoteBook}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("idNoteBook")
            noteBookScreen.NoteBookScreen(navController, id?.toInt())
        }

        composable("add_transaction_screen/{idNoteBook}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("idNoteBook")
            transactionScreen.AddNameTagToTransaction(navController, id?.toInt())
        }

        composable("transaction_screen/{idNoteBook}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("idNoteBook")
            transactionScreen.TransactionScreen(navController, id?.toInt())
        }

        composable("budget_screen/{idNoteBook}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("idNoteBook")
            budgetScreen.BudgetScreen(navController, id?.toInt())
        }

        composable("nameTag_screen/{idNoteBook}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("idNoteBook")
            nameTagScreen.NameTagScreen(navController, id?.toInt())
        }
    }
}
