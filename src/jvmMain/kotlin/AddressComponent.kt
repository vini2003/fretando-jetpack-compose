import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import util.misc.Countries

@ExperimentalComposeUiApi
@Composable
fun AddressComponent(label: String = "Address") {
    val focusRequesters = remember { List(7) { FocusRequester() } }

    val street = remember { mutableStateOf("") }
    val streetError = remember { mutableStateOf<String?>(null) }

    val number = remember { mutableStateOf("") }
    val numberError = remember { mutableStateOf<String?>(null) }

    val city = remember { mutableStateOf("") }
    val cityError = remember { mutableStateOf<String?>(null) }

    val state = remember { mutableStateOf("") }
    val stateError = remember { mutableStateOf<String?>(null) }

    val postalCode = remember { mutableStateOf("") }
    val postalCodeError = remember { mutableStateOf<String?>(null) }

    var country by remember { mutableStateOf("") }
    var countryError by remember { mutableStateOf<String?>(null) }

    val notes = remember { mutableStateOf("") }

    var dropdownMenuExpanded by remember { mutableStateOf(false) }

    var dropdownMenuTextFieldSize by remember { mutableStateOf(Size.Zero) }

    val dropdownMenuIcon = if (dropdownMenuExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        Text(label, fontSize = 18.sp, color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium), modifier = Modifier.padding(8.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)

        Column (
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomTextField(
                    value = street,
                    error = streetError,
                    label = "Street",
                    modifier = Modifier
                        .defaultMinSize(minHeight = 16.dp)
                        .width(440.dp)
                        .focusRequester(focusRequesters[0]),
                    nextFocusRequester = focusRequesters[1]
                )
                CustomTextField(
                    value = number,
                    error = numberError,
                    label = "Number",
                    modifier = Modifier
                        .defaultMinSize(minHeight = 16.dp)
                        .width(120.dp)
                        .focusRequester(focusRequesters[1]),
                    nextFocusRequester = focusRequesters[2]
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomTextField(
                    value = city,
                    error = cityError,
                    label = "City",
                    modifier = Modifier
                        .defaultMinSize(minHeight = 16.dp)
                        .width(380.dp)
                        .focusRequester(focusRequesters[2]),
                    nextFocusRequester = focusRequesters[3]
                )

                CustomTextField(
                    value = state,
                    error = stateError,
                    label = "State",
                    modifier = Modifier
                        .defaultMinSize(minHeight = 16.dp)
                        .width(180.dp)
                        .focusRequester(focusRequesters[3]),
                    nextFocusRequester = focusRequesters[4]
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomTextField(
                    value = postalCode,
                    error = postalCodeError,
                    label = "Postal Code",
                    modifier = Modifier
                        .defaultMinSize(minHeight = 16.dp)
                        .width(278.dp)
                        .focusRequester(focusRequesters[4]),
                    nextFocusRequester = focusRequesters[5]
                )

                Box {
                    OutlinedTextField(
                        value = country,
                        onValueChange = { country = it; dropdownMenuExpanded = true },
                        isError = countryError != null,
                        modifier = Modifier
                            .defaultMinSize(minHeight = 16.dp)
                            .onGloballyPositioned { coordinates ->
                                dropdownMenuTextFieldSize = coordinates.size.toSize()
                            }
                            .focusRequester(focusRequesters[5])
                            .onKeyEvent { keyEvent ->
                                if (keyEvent.key == Key.Tab && keyEvent.type == KeyEventType.KeyUp) {
                                    focusRequesters[6].requestFocus()
                                    true
                                } else {
                                    false
                                }
                            },
                        label = { countryError?.let { Text(it, color = MaterialTheme.colors.error, style = TextStyle(fontSize = 13.sp)) } ?: Text("Country") },
                        trailingIcon = {
                            Icon(
                                dropdownMenuIcon,
                                "Dropdown Arrow",
                                Modifier.clickable { dropdownMenuExpanded = !dropdownMenuExpanded }
                            )
                        }
                    )

                    DropdownMenu(
                        focusable = false,
                        expanded = dropdownMenuExpanded,
                        onDismissRequest = { dropdownMenuExpanded = false },
                        modifier = Modifier
                            .defaultMinSize(minHeight = 16.dp)
                            .heightIn(max = 200.dp)
                            .width(with(LocalDensity.current) { dropdownMenuTextFieldSize.width.toDp() })
                    ) {
                        Countries.filter { it.startsWith(country, ignoreCase = true) }.forEach { c ->
                            DropdownMenuItem(onClick = {
                                country = c
                                dropdownMenuExpanded = false
                            }) {
                                Text(text = c)
                            }
                        }
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomTextField(
                    value = notes,
                    error = mutableStateOf(null),
                    label = "Notes",
                    modifier = Modifier
                        .defaultMinSize(minHeight = 16.dp)
                        .width(568.dp)
                        .focusRequester(focusRequesters[6]),
                    nextFocusRequester = focusRequesters[0]
                )
            }
        }
    }
}

