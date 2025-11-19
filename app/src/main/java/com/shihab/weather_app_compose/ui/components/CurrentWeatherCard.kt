package com.shihab.weather_app_compose.ui.components
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shihab.weather_app_compose.data.CurrentWeather
// ui/components/CurrentWeatherCard.kt ফাইলে


fun getWeatherIcon( iconCode: ImageVector ): ImageVector {
    return when (iconCode) {
        Icons.Default.WbSunny -> Icons.Default.WbSunny
        Icons.Default.Cloud -> Icons.Default.Cloud
        Icons.Default.FlashOn -> Icons.Default.FlashOn
        Icons.Default.WaterDrop -> Icons.Default.WaterDrop
        Icons.Default.AcUnit -> Icons.Default.AcUnit
        else -> Icons.Default.WbSunny // ডিফল্ট আইকন
    }
}

@Composable
fun CurrentWeatherCard(weather: CurrentWeather) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = weather.city, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = getWeatherIcon(weather.iconVector),
                    contentDescription = weather.description,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "${weather.temperature}°C",
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = weather.description, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                WeatherDetail(label = "Humidity", value = "${weather.humidity}%")
                WeatherDetail(label = "Wind", value = "${weather.windSpeed} km/h")
            }
        }
    }
}

@Composable
fun WeatherDetail(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}