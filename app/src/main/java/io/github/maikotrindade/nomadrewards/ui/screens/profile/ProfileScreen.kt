package io.github.maikotrindade.nomadrewards.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.maikotrindade.nomadrewards.R
import io.github.maikotrindade.nomadrewards.model.User

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    userEmail: String,
    showMessage: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchUserByEmail(userEmail)
    }

    val message by viewModel.showMessage.collectAsState()
    LaunchedEffect(message) {
        message?.let { showMessage(it) }
    }

    val user by viewModel.user.collectAsState()
    user?.let {
        UserPage(it)
    }
}

@Composable
private fun UserPage(user: User) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(R.drawable.art_profile),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = user.name,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = user.email,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 16.sp
        )
    }
}


