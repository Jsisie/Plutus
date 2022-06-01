package fr.esipe.barrouxrodriguez.plutus.utils

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import fr.esipe.barrouxrodriguez.plutus.R

class AlertDialogUtil {

    companion object {


        /**
         * Static method that show an {@link AlertDialog}.
         * It factorize the error handling and the display of an AlertDialog
         *
         * @param openDialog variable for recomposition and pop of the dialog
         * @param title String of the title
         * @param confirmText text showed in the confirm button
         * @param dismissText text showed in the dismiss button
         * @param maxChar max character allowed in the text field
         * @param onConfirmClick action to do when confirm button is clicked, text is the text in the text field, isError is a boolean at false when the text field value is validated, true when it is not
         * @param onDismissClick action to do when dismiss button is clicked
         * @param onErrorText text to display when there is an error
         *
         */
        @Composable
        fun ShowAlertDialog(
            openDialog: MutableState<Boolean>,
            title: String,
            confirmText: String = stringResource(id = R.string.yes),
            dismissText: String = stringResource(id = R.string.no),
            onConfirmClick: (text: String, isError: MutableState<Boolean>) -> Unit = { text, _ ->
                Log.i(
                    "onConfirmClick",
                    "Confirmed text :$text"
                )
            },
            onDismissClick: () -> Unit = { Log.i("onDismissClick", "Dismissed action") },
            maxChar: Int = 20,
            onErrorText: String = "Error",
            isTextFieldValue: Boolean = true,
        ) {


            val isError: MutableState<Boolean> = remember { mutableStateOf(false) }
            val textChange: MutableState<TextFieldValue> =
                remember { mutableStateOf(TextFieldValue("")) }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = { Text(text = title) },
                    text = {
                        if (isTextFieldValue) {
                            Column {
                                TextField(
                                    value = textChange.value,
                                    onValueChange = { newText ->
                                        isError.value = false
                                        if (textChange.value.text.length < maxChar) {
                                            textChange.value = newText
                                        }
                                    })
                                if (isError.value) {
                                    Text(
                                        text = onErrorText,
                                        color = MaterialTheme.colors.error,
                                        style = MaterialTheme.typography.caption,
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            onConfirmClick.invoke(textChange.value.text, isError)
                            openDialog.value = false
                            isError.value = false
                        }) {
                            Text(text = confirmText)
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            onDismissClick.invoke()
                            openDialog.value = false
                            isError.value = false
                        }) {
                            Text(text = dismissText)
                        }
                    }
                )
            }
        }
    }
}
