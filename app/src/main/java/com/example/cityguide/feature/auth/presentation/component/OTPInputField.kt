package com.example.cityguide.feature.auth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import android.view.KeyEvent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import com.example.cityguide.ui.theme.LightSubTextColor

@Composable
fun OTPInputField(
    number:Int?,
    focusRequesters: FocusRequester,
    onFocusChanged:(Boolean) -> Unit,
    onNumberChanged: (Int) -> Unit,
    onKeyboardBackClick:() -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember {
        mutableStateOf(
            TextFieldValue(
                text = number?.toString().orEmpty(),
                selection = TextRange(
                    index = number?.toString()?.length ?: 0
                )
            )
        )
    }

    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    )
    {
        BasicTextField(
            value = text,
            onValueChange = {newText ->
                val newNumber = newText.text
                if(newNumber.length <= 1 && newNumber.isDigitsOnly()){
                    text = newText
                    onNumberChanged(newNumber.toIntOrNull() ?: 0)

                }
            },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            singleLine = true,
            textStyle = MaterialTheme.typography.headlineMedium,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .padding(8.dp)
                .focusRequester(focusRequesters)
                .onFocusChanged {
                    isFocused = it.isFocused
                    onFocusChanged(it.isFocused)
                }.onKeyEvent { event ->
                    val didPressDelete = event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
                    if(didPressDelete && number != null){
                        onKeyboardBackClick()
                    }
                    false
                },
            decorationBox = { innerBox ->
                if(!isFocused && number == null){
                    Text(
                        text = "-",
                        textAlign = TextAlign.Center,
                        color = LightSubTextColor,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
            }
        )
    }
}