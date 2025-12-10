package com.shihab.weather_app_compose.ui.screen.settingsScreen

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shihab.weather_app_compose.data.SettingsStore
import com.shihab.weather_app_compose.ui.screen.settingsScreen.components.SettingsCard
import com.shihab.weather_app_compose.ui.screen.settingsScreen.components.SettingsSectionTitle
import com.shihab.weather_app_compose.ui.screen.settingsScreen.components.SettingsTile
import kotlinx.coroutines.launch
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val settingsStore = remember { SettingsStore(context) }

    val isCelsius by settingsStore.isCelsius.collectAsState(initial = true)
    val isNotifEnabled by settingsStore.notificationsEnabled.collectAsState(initial = true)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Settings",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
        containerColor = Color(0xFFF2F4F8)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            SettingsSectionTitle("PREFERENCES")

            SettingsCard {
                SettingsTile(
                    icon = Icons.Default.Thermostat,
                    title = "Temperature Unit",
                    subtitle = if (isCelsius) "Celsius (°C)" else "Fahrenheit (°F)",
                    trailing = {
                        Switch(
                            checked = !isCelsius,
                            onCheckedChange = { isFahrenheit ->
                                scope.launch { settingsStore.saveUnit(!isFahrenheit) }
                            },
                            colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFF29B6F6))
                        )
                    })
                Divider(color = Color.LightGray.copy(alpha = 0.3f))

                SettingsTile(
                    icon = Icons.Default.Notifications,
                    title = "Weather Alerts",
                    subtitle = "Get daily summary",
                    trailing = {
                        Switch(
                            checked = isNotifEnabled,
                            onCheckedChange = { scope.launch { settingsStore.saveNotification(it) } },
                            colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFF29B6F6))
                        )
                    })
            }

            Spacer(modifier = Modifier.height(24.dp))

            SettingsSectionTitle("ABOUT & SUPPORT")

            SettingsCard {
                SettingsTile(
                    icon = Icons.Default.Lock,
                    title = "Privacy Policy",
                    subtitle = "Read our terms",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, "https://google.com".toUri())
                        context.startActivity(intent)
                    })
                Divider(color = Color.LightGray.copy(alpha = 0.3f))

                SettingsTile(
                    icon = Icons.Default.Info,
                    title = "Version",
                    subtitle = "v1.0.0 (Beta)",
                    trailing = {})
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Made with ❤️ by MD. AL-MUHEETU",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

