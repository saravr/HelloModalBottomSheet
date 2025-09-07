package com.sandymist.hellomodalbottomsheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    var menuOpened by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = {
                menuOpened = true
            }
        ) {
            Text("Open Menu")
        }
    }

    MyModalBottomSheet(
        menuOpened = menuOpened,
        setMenuOpened = {
            menuOpened = it
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .heightIn(max = (0.8f * LocalConfiguration.current.screenHeightDp).dp) // Apply constraint here instead
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            (1..20).forEach {
                MenuItem(
                    label = "Option $it",
                    selected = false,
                    onClick = {}
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyModalBottomSheet(
    menuOpened: Boolean,
    setMenuOpened: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = menuOpened,
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        ModalBottomSheet(
            onDismissRequest = {
                setMenuOpened(false)
            },
            sheetState = sheetState,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            content()
        }
    }
}

@Composable
fun MenuItem(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .weight(1f),
        )

        if (selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected $label",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }

    HorizontalDivider()
}
