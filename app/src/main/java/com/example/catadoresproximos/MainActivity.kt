package com.example.catadoresproximos

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract.Contacts
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown

import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.catadoresproximos.model.Catador
import com.example.catadoresproximos.model.catadores
import com.example.catadoresproximos.model.materiais
import com.example.catadoresproximos.ui.theme.CatadoresProximosTheme
import com.example.catadoresproximos.ui.theme.CatadoresProximosTheme

import kotlinx.coroutines.flow.callbackFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatadoresProximosTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(8, 113, 19),
                    ) {
                    Greeting(catadores)
                }
            }
        }
    }
}

@Composable
fun Greeting(catadores: List<Catador>) {

    var searchText by remember { mutableStateOf("") }

    Box(modifier = Modifier.padding()) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Black)
                .clip(RoundedCornerShape(8.dp)),
            placeholder = {
                Text(
                    "Pesquisar por catador ou material...",
                    style = MaterialTheme.typography.body1.copy(color = White)
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Ícone de pesquisa",
                    tint = White
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = White,
                cursorColor = White,
                placeholderColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }

    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp, bottom = 10.dp, end = 9.dp, start = 9.dp)
            .clip(RoundedCornerShape((2.dp)))
    ) { Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Catadores Próximos",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
        }
            LazyColumn(
                modifier = Modifier
                    .padding(top = 62.dp)
            ) {
                items(catadores.filter {
                    it.nome.contains(
                        searchText,
                        ignoreCase = true
                    )
                }) { catador ->
                    Card(
                        elevation = 6.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp, horizontal = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.baseline_person_24),
                                contentDescription = "${catador.nome}",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = catador.nome,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Text(
                                    text = "${catador.endereco}",
                                    fontSize = 18.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Button(
                                        modifier = Modifier
                                            .height(35.dp)
                                            .width(140.dp),
                                        onClick = { /* ... */ },
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(8, 113, 19))
                                    ) {
                                        Text(text = "Solicite", color = White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AddressSelectorPopup(
        catadores: List<String>,
        onAddressSelected: (String) -> Unit
    ) {
        var expanded by remember { mutableStateOf(false) }
        var selectedAddress by remember { mutableStateOf("") }

        Column {
            IconButton(
                onClick = { expanded = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Expand"
                )
            }
            if (expanded) {
                AlertDialog(
                    onDismissRequest = { expanded = false },
                    title = { Text(text = "Selecione um endereço") },
                    text = {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            catadores.forEach { address ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedAddress = address
                                        expanded = false
                                        onAddressSelected(selectedAddress)
                                    }
                                ) {
                                    Text(text = address)
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                expanded = false
                                onAddressSelected(selectedAddress)
                            }
                        ) {
                            Text(text = "Selecionar")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { expanded = false }
                        ) {
                            Text(text = "Cancelar")
                        }
                    }
                )
            }
        }
    }
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    CatadoresProximosTheme() {
        Greeting(catadores)
    }
}
