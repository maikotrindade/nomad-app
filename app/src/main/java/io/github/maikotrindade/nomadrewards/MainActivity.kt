package io.github.maikotrindade.nomadrewards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.maikotrindade.nomadrewards.ui.base.BaseContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()
            val helloWorld by viewModel.helloWorldState.collectAsState()

            MainScreen(helloWorld)
        }
    }

    @Composable
    private fun MainScreen(helloWorld: String) {
        BaseContent {
            Text(helloWorld)
        }
    }
}