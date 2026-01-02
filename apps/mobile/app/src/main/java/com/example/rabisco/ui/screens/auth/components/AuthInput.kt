package com.example.rabisco.ui.screens.auth.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rabisco.ui.theme.RabiscoTheme

@Composable
fun AuthInput(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    placeholder: String = ""
) {
    OutlinedTextField(
        label = { Text(label) },
        value = value,
        placeholder = { Text(placeholder) },
        onValueChange = onChange,
        shape = RoundedCornerShape(size = 14.dp),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Preview
@Composable
fun AuthInputPreview() {
    var value by remember { mutableStateOf("") }

    RabiscoTheme {
        AuthInput(label = "Label", placeholder = "Placeholder", value = value, onChange = { value = it })
    }
}