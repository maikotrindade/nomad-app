package io.github.maikotrindade.nomadrewards

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.maikotrindade.nomadrewards.ui.base.AuthenticationUI
import io.github.maikotrindade.nomadrewards.ui.base.NavigationManager.SetupNavigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : AuthenticationUI() {

    private val snackBarMessage = MutableStateFlow<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetupNavigation(
                header = { AuthHeader() },
                activity = this,
                floating = {
                    snackBarMessage.collectAsState().value?.let {
                        Toast(it)
                    }
                },
            )
        }
    }

    fun showMessage(message: String) {
        snackBarMessage.value = message
    }

    @Composable
    fun Toast(message: String) {
        var visible by remember { mutableStateOf(true) }
        val opacity = remember { Animatable(0f) }

        LaunchedEffect(Unit) {
            with(opacity) {
                animateTo(1f, animationSpec = tween(durationMillis = 400))
                delay(5000)
                animateTo(0f, animationSpec = tween(durationMillis = 400))
                visible = false
            }
        }

        if (visible) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .background(MaterialTheme.colorScheme.error.copy(alpha = opacity.value)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            }
        }
    }
}