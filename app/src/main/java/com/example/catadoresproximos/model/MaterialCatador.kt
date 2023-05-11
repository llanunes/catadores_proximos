package com.example.catadoresproximos.model

data class Material(
    val id: Int,
    val nome: String
)

val materiais = listOf(
    Material(1, "Vidro"),
    Material(1, "Papel"),
    Material(1, "Madeira"),
    Material(1, "Plástico"),
)

data class Catador(
    val id: Int,
    val nome: String,
    val endereco: String
)

val catadores = listOf(
    Catador(1, "Larissa", "Jandira"),
    Catador(1, "Ana", "Osasco"),
    Catador(1, "Lidia", "São Paulo"),
    Catador(1, "Eduardo", "Osasco"),
    Catador(1, "Miguel", "São Paulo"),
    Catador(1, "Marcel", "Jandira"),
    Catador(1, "Cristiano", "Jandira")
)
