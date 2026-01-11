package com.example.rabisco.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rabisco.ui.theme.RabiscoTheme

@Composable
fun AppInput(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    placeholder: String = "",
    error: String? = null
) {
    Column {
        OutlinedTextField(
            label = { Text(label) },
            value = value,
            placeholder = { Text(placeholder) },
            onValueChange = onChange,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth(),
            isError = error != null,
            singleLine = true
        )

        if (error != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(start = 12.dp, top = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ErrorOutline,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

        }
    }
}


@Preview
@Composable
fun AppInputPreview() {
    var value by remember { mutableStateOf("") }

    RabiscoTheme {
        AppInput(label = "Label", placeholder = "Placeholder", value = value, onChange = { value = it }, error = "Essa é a mensagem de erro")
    }
}