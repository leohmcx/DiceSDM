package br.edu.ifsp.scl.sdm.dicesdm

object ConfigSingleton {
    val config: Config = Config()
    // Usado para determinar qual Fragment deve preencher a tela princial
    object Modos {
        const val MODO_GRAFICO = "MODO_GRAFICO"
        const val MODO_TEXTO = "MODO_TEXTO"
    }
}