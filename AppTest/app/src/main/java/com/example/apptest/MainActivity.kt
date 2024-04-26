package com.example.apptest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptest.ui.theme.AppTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Trường Đại học thủy lợi", "Trường Đại học Bách khoa Hà Nội")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, university: String, modifier: Modifier = Modifier) {
    Surface(
        color = Color.Gray,
        modifier = Modifier.padding(16.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),  // Đảm bảo bố cục của `Column` chiếm đầy đủ chiều rộng
        ){
            Text(
                text = university,
                color = Color.Blue,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Khoa Công nghệ Thông tin",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Ngành: Hệ thống thông tin",
                )

                Text(
                    text = "Lớp: 63HTTT1",
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Họ tên: $name",
                )

                Text(
                    text = "MSV: 2151160535"
                )
            }
        }
    }
}

@Composable
fun GreetingImage(message: String, from: String) {
    val image = painterResource(R.drawable.avatar)
    Image(painter = image, contentDescription = "Avatar")
}

@Preview(
    showBackground =  true,
    showSystemUi = true,
    name = "Samsung Galaxy A33 5G"
)
@Composable
fun GreetingPreview() {
    AppTestTheme {
        // Greeting("Hạ Quang Dũng", "Trường Đại học Thủy lợi", Modifier.padding(12.dp))
        GreetingImage(
            message = "Happy Birthday Sam!",
            from = "From Emma"
        )
    }
}
