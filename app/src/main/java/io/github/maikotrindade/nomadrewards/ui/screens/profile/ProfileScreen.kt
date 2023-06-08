package io.github.maikotrindade.nomadrewards.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.maikotrindade.nomadrewards.R
import io.github.maikotrindade.nomadrewards.model.User
import io.github.maikotrindade.nomadrewards.ui.base.ComposeUtils

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    showMessage: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchUserByEmail()
    }

    val message by viewModel.showMessage.collectAsState()
    LaunchedEffect(message) {
        message?.let { showMessage(it) }
    }

    val isLoading by viewModel.isLoading.collectAsState()
    Column(Modifier.fillMaxSize()) {
        if (!isLoading) {
            val user by viewModel.user.collectAsState()
            user?.let {
                UserPage(it, viewModel)
            }
        } else {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ComposeUtils.LoadingAnimation()
            }
        }
    }
}

@Composable
private fun UserPage(user: User, viewModel: ProfileViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(R.drawable.art_profile),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = user.name,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = user.email,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        val context = LocalContext.current
        Row(
            modifier = Modifier.clickable(
                onClick = { viewModel.copyClipboard(context, user.privateKey.orEmpty()) }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                text = "Private Key: " + user.privateKey.orEmpty(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Icon(
                painter = painterResource(R.drawable.ic_copy),
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.clickable(
                onClick = {
                    viewModel.copyClipboard(
                        context = context,
                        content = "https://sepolia.etherscan.io/address/${user.address.orEmpty()}"
                    )
                }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                text = "Address (3therSc@n): " + user.address.orEmpty(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Icon(
                painter = painterResource(R.drawable.ic_copy),
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = null
            )
        }
    }
}


