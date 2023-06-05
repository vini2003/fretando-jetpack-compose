import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalComposeUiApi
@Composable
fun RequestComponent() {
    Column(
        modifier = Modifier.width(580.dp)
    ) {
        TopAppBar(
            title = { Text("Create Request") },
        )

        AddressComponent("Origin Address")
        Spacer(Modifier.height(16.dp))
        AddressComponent("Destination Address")
        Spacer(Modifier.height(16.dp))
        CargoComponent()
    }
}
