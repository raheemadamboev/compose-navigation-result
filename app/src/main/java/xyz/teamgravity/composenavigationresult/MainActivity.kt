package xyz.teamgravity.composenavigationresult

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.teamgravity.composenavigationresult.ui.theme.ComposeNavigationResultTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigationResultTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val controller = rememberNavController()

                    NavHost(
                        navController = controller,
                        startDestination = "screen1"
                    ) {
                        composable(
                            route = "screen1"
                        ) { entry ->
                            Screen1(
                                result = entry.savedStateHandle["result"],
                                onNavigate = {
                                    controller.navigate("screen2")
                                }
                            )
                        }
                        composable(
                            route = "screen2"
                        ) {
                            Screen2(
                                onNavigateBack = { result ->
                                    controller.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("result", result)
                                    controller.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Screen1(
    result: String?,
    onNavigate: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (result != null) Text(text = result)
        Button(onClick = onNavigate) {
            Text(text = stringResource(id = R.string.go_to_screen2))
        }
    }
}

@Composable
fun Screen2(
    onNavigateBack: (result: String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        var result by remember { mutableStateOf("") }

        OutlinedTextField(
            value = result,
            onValueChange = { result = it }
        )
        Button(
            onClick = {
                onNavigateBack(result)
            }
        ) {
            Text(text = stringResource(id = R.string.navigate_back_with_result))
        }
    }
}