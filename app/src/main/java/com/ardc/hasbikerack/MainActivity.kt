package com.ardc.hasbikerack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ardc.hasbikerack.domain.entities.UserInformation
import com.ardc.hasbikerack.ui.theme.HasBikeRackTheme
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    /**
     * Mutable state for this Activity.
     */
    private lateinit var activityState: MutableState<UserInformation?>

    /**
     * Exposes the current user - for testing purposes.
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val currentUser
        get() = activityState.value

    /**
     * Contract for launching (and reacting) to FirebaseUI's Auth routines.
     */
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            activityState = remember {
                mutableStateOf(null)
            }

            // Attempt to recover last signed in user
            GoogleSignIn.getLastSignedInAccount(this)?.let {
                it.displayName?.let { name ->
                    activityState.value = UserInformation(name)
                } ?: run {
                    activityState.value = UserInformation.Cyclist
                }
            }

            HasBikeRackTheme {
                val colModifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = colModifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        activityState.value?.let {
                            Greeting(it.name)
                            SignOutButton()
                        } ?: run {
                            Text(text = stringResource(id = R.string.signin_welcome_text))
                            SignInButton()
                        }
                    }
                }
            }
        }
    }

    /**
     * Composes a button to sign users out.
     */
    @Composable
    private fun SignOutButton() {
        Button(onClick = {
            AuthUI.getInstance()
                .signOut(this).addOnCompleteListener {
                    activityState.value = null
                }
        }) {
            Text(text = stringResource(id = R.string.signout_button_text))
        }
    }

    /**
     * Composes a button to sign users in.
     */
    @Composable
    private fun SignInButton() {
        Button(onClick = {
            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

            // Create and launch sign-in intent
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            signInLauncher.launch(signInIntent)
        }) {
            Text(text = stringResource(id = R.string.common_signin_button_text))
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            FirebaseAuth.getInstance().currentUser?.let {
                it.displayName.let { name ->
                    activityState.value = if (name == null)
                        UserInformation.Cyclist else UserInformation(name)
                }
            }
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello, $name!!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HasBikeRackTheme {
        Greeting("Android")
    }
}