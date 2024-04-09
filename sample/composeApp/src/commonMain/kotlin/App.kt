import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import imagepreviewcmplibrary.sample.composeapp.generated.resources.Res
import imagepreviewcmplibrary.sample.composeapp.generated.resources.compose_multiplatform
import imagepreviewcmplibrary.sample.composeapp.generated.resources.organiks_egg
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import rg.teka.image_preview_cmp_library.BuildKonfig


@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    ImagePreview(painterResource(Res.drawable.organiks_egg))
                    ImagePreview(painterResource(Res.drawable.organiks_egg))
                    val password = BuildKonfig.SONATYPE_PASSWORD
                    val username = BuildKonfig.SONATYPE_USERNAME

                    Text("Compose: $greeting ")
                    Text("env: $password ")
                    Text("env: $username ")
                }
            }
        }
    }
}