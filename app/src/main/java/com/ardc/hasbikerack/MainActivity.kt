package com.ardc.hasbikerack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ardc.hasbikerack.ui.theme.HasBikeRackTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : ComponentActivity() {
    /**
     * Name of the last known user.
     */
    private var userName: String = "John Doe"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configure Google Sign In
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
//        val gsClient = GoogleSignIn.getClient(this, gso)
        GoogleSignIn.getLastSignedInAccount(this)?.let {
            userName = it.displayName ?: "John Doe"
        }

        setContent {
            HasBikeRackTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(userName)
                }
            }
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