package dev.mobiledeveloper.composetestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import dev.mobiledeveloper.composetestapp.ui.theme.ComposeTestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = SampleViewModel()

        setContent {
            ComposeTestAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SampleScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun SampleScreen(viewModel: SampleViewModel) {
    val viewState = viewModel.viewState.collectAsState().value

    SampleView(viewState = viewState, onEventHandle = {
        viewModel.obtainEvent(it)
    })

    LaunchedEffect(key1 = Unit) {
        viewModel.obtainEvent(SampleEvent.ScreenLoaded)
    }
}

@Composable
fun SampleView(viewState: SampleViewState, onEventHandle: (SampleEvent) -> Unit) {
    LazyColumn {
        viewState.data.forEach {
            item {
                SampleUserCell(model = it)
            }
        }
    }
}

@Composable
fun SampleUserCell(model: SampleUserCellModel) {
    Row(modifier = Modifier.padding(16.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(model.avatar)
                .crossfade(true)
                .build(),
            contentDescription = model.avatar,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(60.dp).clip(CircleShape)
        )


        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = model.username, fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Text(model.description)
        }
    }
}