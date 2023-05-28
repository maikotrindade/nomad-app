package io.github.maikotrindade.nomadrewards.ui.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.maikotrindade.nomadrewards.R
import io.github.maikotrindade.nomadrewards.model.toModel
import io.github.maikotrindade.nomadrewards.network.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class AuthenticationUI : ComponentActivity(), KoinComponent {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val service: UserService by inject()
    val userState = MutableStateFlow<FirebaseUser?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val signOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, signOptions)
        googleSignInClient.signOut()
        auth = Firebase.auth
        userState.value = auth.currentUser
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUser(currentUser)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
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
                if (task.isSuccessful && auth.currentUser != null) {
                    val user = auth.currentUser!!
                    updateUser(user)
                    lifecycleScope.launch(Dispatchers.IO) {
                        service.upsertUser(user.toModel())
                    }
                } else {
                    updateUser(null)
                }
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        Firebase.auth.signOut()
        userState.value = null
    }

    private fun updateUser(user: FirebaseUser?) {
        userState.value = user
    }

    @Composable
    internal fun AuthHeader(user: FirebaseUser?) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Nomad", color = MaterialTheme.colorScheme.onPrimary)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { if (user == null) signIn() else signOut() }
            ) {
                Text(
                    if (user == null) "sign in" else "sign out",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }

    companion object {
        private const val TAG = "AuthenticationUI"
        private const val RC_SIGN_IN = 9001
    }
}