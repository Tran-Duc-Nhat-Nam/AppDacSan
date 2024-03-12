package com.example.appcsn.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.appcsn.ui.navgraph.SettingGraph
import com.example.appcsn.ui.widget.PageHeader
import com.example.appcsn.viewmodel.BaseViewModel.Companion.dsNavItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Destination<SettingGraph>()
@Composable
fun TrangCaiDatGiaoDien(
    navigator: DestinationsNavigator,
    dataStore: DataStore<Preferences>
) {

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val key = intPreferencesKey("brightness")
    val brightnessFlow: Flow<Int> = dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[key] ?: 0
        }
    var brightness by remember {
        mutableStateOf(0)
    }

    var isChonMode by remember {
        mutableStateOf(false)
    }

    BackHandler {
        dsNavItem[2].backStack.removeLast()
        navigator.navigate(dsNavItem[2].backStack.last())
    }

    LaunchedEffect(Unit) {
        brightnessFlow.collect {
            brightness = it
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        PageHeader(text = "Cài đặt")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Giao diện tối",
                modifier = Modifier
                    .padding(15.dp)
            )
            ExposedDropdownMenuBox(
                expanded = isChonMode,
                onExpandedChange = {
                    isChonMode = !isChonMode
                }
            ) {
                OutlinedTextField(
                    value = when {
                        (brightness == 1) -> "Sáng"
                        (brightness == 2) -> "Tối"
                        else -> "Mặc định hệ thống"
                    },
                    readOnly = true,
                    onValueChange = {},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = isChonMode
                        )
                    },
                    shape = RoundedCornerShape(25.dp),
                    modifier = Modifier
                        .padding(10.dp)
                        .menuAnchor()
                )
                DropdownMenu(expanded = isChonMode, onDismissRequest = { isChonMode = false }) {
                    DropdownMenuItem(text = { Text(text = "Mặc định hệ thống") }, onClick = {
                        coroutineScope.launch {
                            dataStore.edit { settings ->
                                settings[key] = 0
                            }
                            isChonMode = false
                        }
                    })
                    DropdownMenuItem(text = { Text(text = "Sáng") }, onClick = {
                        coroutineScope.launch {
                            dataStore.edit { settings ->
                                settings[key] = 1
                            }
                            isChonMode = false
                        }
                    })
                    DropdownMenuItem(text = { Text(text = "Tối") }, onClick = {
                        coroutineScope.launch {
                            dataStore.edit { settings ->
                                settings[key] = 2
                            }
                            isChonMode = false
                        }
                    })
                }
            }
        }
        HorizontalDivider()
    }
}