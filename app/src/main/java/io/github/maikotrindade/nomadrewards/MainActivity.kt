package io.github.maikotrindade.nomadrewards

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import io.github.maikotrindade.nomadrewards.ui.base.AuthenticationUI
import io.github.maikotrindade.nomadrewards.ui.base.NavigationManager.SetupNavigation

class MainActivity : AuthenticationUI() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetupNavigation(
                header = { AuthHeader(userState.collectAsState().value) },
                activity = this)
        }
    }
}
