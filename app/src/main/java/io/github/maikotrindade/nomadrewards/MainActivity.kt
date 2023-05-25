package io.github.maikotrindade.nomadrewards

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.maikotrindade.nomadrewards.ui.base.BaseContent
import io.github.maikotrindade.nomadrewards.ui.flow.profile.ProfileScreen
import io.github.maikotrindade.nomadrewards.ui.flow.welcome.WelcomeScreen

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        setContent {
            setupNavigation()
//            val viewModel: MainViewModel = viewModel()
//            val helloWorld by viewModel.helloWorldState.collectAsState()
//            MainScreen(helloWorld)
        }
    }

    @Composable
    fun setupNavigation(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = "WelcomeScreen"
        ) {
            composable("WelcomeScreen") { WelcomeScreen() }
            composable("ProfileScreen") { ProfileScreen() }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        Firebase.auth.signOut()
    }

    private fun updateUI(user: FirebaseUser?) {
        Log.d(TAG, "SUCCESS UpdateUI: " + user?.displayName)
    }

    @Composable
    private fun MainScreen(helloWorld: String) {
        BaseContent {
            Text(helloWorld)

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { signIn() }
            ) {
                Text("Sign-in with Google")

            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { signOut() }
            ) {
                Text("Sign out")
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val RC_SIGN_IN = 9001
    }
}