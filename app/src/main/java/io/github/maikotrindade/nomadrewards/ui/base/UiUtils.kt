package io.github.maikotrindade.nomadrewards.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.maikotrindade.nomadrewards.ui.theme.NomadRewardsTheme

@Composable
fun ScreenContent(
    header: @Composable () -> Unit,
    content: @Composable () -> Unit,
) = NomadRewardsTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        header()
        Divider(color = Color.LightGray, thickness = 1.dp)
        Column(modifier = Modifier.weight(1f)) {
            content()
        }
    }
}
