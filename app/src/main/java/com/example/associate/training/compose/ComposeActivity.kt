package com.example.associate.training.compose
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ComposeActivity: ComponentActivity() {
    private val viewModelA: ComposeAViewModel by viewModels()
    private val viewModelB: ComposeBViewModel by viewModels()
    private val TAG = "ComposeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // MessageCard(" Android Tim ")
            TwoButtons(viewModelA)
        }
        lifecycleScope.launch {
            viewModelA.infoFlow.collect { key ->
                Log.i(TAG, "viewModelB.sendEvent()")
                viewModelB.sendEvent()
            }
        }

        lifecycleScope.launch {
            viewModelB.infoFlow.collect { key ->
                Log.i(TAG, "From: $key")
            }
        }
    }
}
@Composable
fun TwoButtons(viewModelA: ComposeAViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            Log.i("ComposeActivity", "click A")
            viewModelA.sendEventOutside()
        }) {
            Text(text = "Button 1")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Handle button 2 click */ }) {
            Text(text = "Button 2")
        }
    }
}
@Composable
fun MessageCard(name: String) {
    Text(text = "Hello $name!")
}

@Preview
@Composable
fun PreviewMessageCard12() {
    MessageCard("Android")
}
