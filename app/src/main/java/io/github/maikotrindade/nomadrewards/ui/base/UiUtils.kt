package io.github.maikotrindade.nomadrewards.ui.base

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.maikotrindade.nomadrewards.MainActivity
import io.github.maikotrindade.nomadrewards.ui.theme.NomadRewardsTheme

@Composable
fun MainActivity.BaseContent(content: @Composable () -> Unit) = NomadRewardsTheme {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        content()
    }
}
