package io.github.maikotrindade.nomadrewards.ui.screens.wallet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WalletScreen(viewModel: WalletViewModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text("WalletScreen")
    }
}