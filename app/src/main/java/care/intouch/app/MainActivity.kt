package care.intouch.app

 import android.os.Bundle
 import androidx.activity.ComponentActivity
 import androidx.activity.compose.setContent
 import androidx.compose.foundation.layout.Column
 import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.foundation.layout.fillMaxWidth
 import androidx.compose.foundation.layout.padding
 import androidx.compose.material3.Button
 import androidx.compose.material3.ButtonColors
 import androidx.compose.material3.Surface
 import androidx.compose.material3.Text
 import androidx.compose.runtime.Composable
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.unit.dp
 import androidx.navigation.compose.rememberNavController
 import care.intouch.app.core.navigation.AppNavScreen
 import care.intouch.app.core.navigation.Authentication
 import care.intouch.app.core.navigation.AuthorizationRouteBranch
 import care.intouch.app.core.navigation.navhost.MainNavHost
 import care.intouch.uikit.theme.InTouchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InTouchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = InTouchTheme.colors.mainBlue
                ) {
                    Column {
                        if (BuildConfig.DEBUG) {
                            MainNavHost(navController = rememberNavController())
                        } else {
                            AppNavScreen(
                                startDestination = AuthorizationRouteBranch.route,
                                authStartDestination = Authentication.route
                            )
                        }
                    }
                }
            }
        }
    }
}


