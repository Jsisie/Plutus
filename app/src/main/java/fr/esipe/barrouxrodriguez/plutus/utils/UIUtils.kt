package fr.esipe.barrouxrodriguez.plutus.utils

import android.app.DatePickerDialog
import android.util.Log
import android.util.Size
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.esipe.barrouxrodriguez.plutus.R
import fr.esipe.barrouxrodriguez.plutus.model.entity.NameTag
import java.util.function.Predicate

class UIUtils {

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
            text: String = "",
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
            if(text.isNotEmpty()){
                textChange.value = TextFieldValue(text);
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                        textChange.value = TextFieldValue("")
                    },
                    title = { Text(text = title) },
                    text = {
                        if (isTextFieldValue) {
                            Column {
                                TextField(
                                    value = textChange.value,
                                    onValueChange = { newText ->
                                        isError.value = false
                                        if (newText.text.length <= maxChar) {
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
                            textChange.value = TextFieldValue("")
                            openDialog.value = false
                            isError.value = false
                        }) {
                            Text(text = confirmText)
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            onDismissClick.invoke()
                            textChange.value = TextFieldValue("")
                            openDialog.value = false
                            isError.value = false
                        }) {
                            Text(text = dismissText)
                        }
                    }
                )
            }
        }


        @OptIn(ExperimentalMaterialApi::class)
        @Composable
        fun ShowListOfNameTags(
            title: String,
            titleSize: TextUnit = 13.sp,
            tags: List<NameTag>,
            tagsSize: TextUnit = 15.sp,
            tagWidth: Dp = 120.dp,
            tagHeight: Dp = 45.dp,
            onLongPress: ((NameTag) -> Unit) = {},
            onTap: ((NameTag) -> Unit),
            selected: Predicate<NameTag>,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$title :", textAlign = TextAlign.Center, fontSize = titleSize)
                LazyHorizontalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    rows = GridCells.Fixed(2)
                ) {
                    items(tags) { tag ->
                        Card(
                            modifier = Modifier
                                .width(tagWidth)
                                .height(tagHeight)
                                .padding(2.dp)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {
                                            onLongPress.invoke(tag)
                                        },
                                        onTap = {
                                            onTap.invoke(tag)
                                        }
                                    )
                                },
                            elevation = 5.dp,
                            backgroundColor = Color.LightGray,
                            content = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = tag.titleNameTag,
                                        textAlign = TextAlign.Center,
                                        fontSize = tagsSize,
                                        color = if (selected.test(tag)) Color(
                                            66,
                                            66,
                                            66,
                                            128
                                        ) else Color.Black
                                    )
                                }
                            })
                    }
                }
            }
        }
    }
}
