package io.github.maikotrindade.nomadrewards.ui.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.maikotrindade.nomadrewards.MainActivity
import io.github.maikotrindade.nomadrewards.ui.theme.NomadRewardsTheme

@Composable
fun MainActivity.BaseContent(content: @Composable () -> Unit) = NomadRewardsTheme {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)) {
            content()
        }
    }
}
