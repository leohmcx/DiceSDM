package br.edu.ifsp.scl.sdm.dicesdm

import android.support.v4.app.Fragment
import android.view.View
import java.util.*

abstract class ModoJogoFragment: Fragment() {
    val geradorRandomico: Random
    init{geradorRandomico = Random(System.currentTimeMillis())}
    abstract fun jogarDado(view: View)
}