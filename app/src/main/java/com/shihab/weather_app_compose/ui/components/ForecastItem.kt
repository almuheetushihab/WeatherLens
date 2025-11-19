package com.shihab.weather_app_compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shihab.weather_app_compose.data.DailyForecast

@Composable
fun ForecastItem(forecast: DailyForecast) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = forecast.day,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(2f)
        )

        Image(
            painter = painterResource(id = forecast.iconResId),
            contentDescription = forecast.description,
            modifier = Modifier.size(32.dp).weight(1f)
        )

        Text(
            text = forecast.description,
            fontSize = 16.sp,
            modifier = Modifier.weight(4f)
        )

        Row(modifier = Modifier.weight(2f), horizontalArrangement = Arrangement.End) {
            Text(
                text = "${forecast.maxTemp}°",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${forecast.minTemp}°",
                fontSize = 18.sp,
                color = androidx.compose.ui.graphics.Color.Gray
            )
        }
    }
}