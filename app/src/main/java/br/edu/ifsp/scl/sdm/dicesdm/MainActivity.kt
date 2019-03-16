package br.edu.ifsp.scl.sdm.dicesdm

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.startActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    val geradorRandomico: Random = Random(System.currentTimeMillis())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        // ? trata uma chamada segura caso eu não setar o app_name (null)
        supportActionBar?.title = resources.getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val abreFechaToogle: ActionBarDrawerToggle =
                ActionBarDrawerToggle(
                        this
                        , menuLateralDrawerLayout
                        , toolbar
                        , R.string.menu_aberto
                        , R.string.menu_fechado
                )
        menuLateralDrawerLayout.addDrawerListener(abreFechaToogle)
        abreFechaToogle.syncState() // mantem o ícone do botão confome status do menu.
        menuNavigationView.setNavigationItemSelectedListener { onNavigationItemSelected(it) }
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        var retorno: Boolean = false
        when (item.itemId) {
            R.id.modoTextoMenuItem -> {
                //TODO()
                retorno = true
            }
            R.id.modoGraficoMenuItem -> {
                TODO()
                retorno = true
            }
            R.id.sairMenuItem -> {
                finish()
                retorno = true
            }
        }
        menuLateralDrawerLayout.closeDrawer(GravityCompat.START)
        return retorno
    }

    override fun onBackPressed() {
        if(menuLateralDrawerLayout.isDrawerOpen(GravityCompat.START)){
            menuLateralDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed() // se o menu ja estiver recuado ele fecha o aplicativo.
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // referencia o id e qual lugar quero criar o menu, no caso o menu que vem parametrizado.
        menuInflater.inflate(R.menu.menu, menu)
        return true // se criar o menu tem que retornar verdadeiro
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.configuracoesMenuItem ->
                //startActivity(Intent(this, ConfigActivity::class.java))
                startActivity<ConfigActivity>() // biblioteca anko adicionada no gradle
        }
        return super.onOptionsItemSelected(item)
    }

    /*fun jogarDado(view: View) {
        if (view == jogarDadoButton) {
            val numDices: Int = numDicesSpinner.selectedItem.toString().toInt()
            val numFaces = numFacesEditText.text.toString().toInt()
            if (numFaces > 6) {
                resultadoImageView.visibility = View.GONE
                resultado2imageView.visibility = View.GONE
            } else {
                resultadoImageView.visibility = View.VISIBLE
                if (numDices == 2) {
                    resultado2imageView.visibility = View.VISIBLE
                } else {
                    resultado2imageView.visibility = View.GONE
                }
            }
            var resultadoText = ""
            for (i in 1..numDices) {
                val resultado = geradorRandomico.nextInt(numFaces) + 1
                resultadoText = "${resultadoText} $resultado"
                val imageView: ImageView = if (i == 1) resultadoImageView else resultado2imageView
                val resourceName = "dice_${resultado}"
                imageView.setImageResource(resources.getIdentifier(resourceName, "drawable", packageName))
                resultadoTextView.text = "A face sorteada foi a ${resultadoText}"
            }
        }
    }*/
}