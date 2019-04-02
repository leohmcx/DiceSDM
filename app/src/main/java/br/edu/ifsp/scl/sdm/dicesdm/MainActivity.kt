package br.edu.ifsp.scl.sdm.dicesdm

import android.content.Intent
import android.content.Intent.ACTION_SEND;
import android.content.Intent.EXTRA_TEXT;
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.MenuItem
import br.edu.ifsp.scl.sdm.dicesdm.ConfigSingleton.Modos.MODO_GRAFICO
import br.edu.ifsp.scl.sdm.dicesdm.ConfigSingleton.Modos.MODO_TEXTO
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.startActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    val geradorRandomico: Random = Random(System.currentTimeMillis())
    // Intent que será associada ao ShareActionProvider do MenuItem
    var compartilharIntent: Intent = Intent(ACTION_SEND)

    init {
        compartilharIntent.type = "text/*"
    }

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
        substituiFragment(MODO_GRAFICO) // coloca o modo grafico como modo padrão para abertura do jogo.
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        var retorno: Boolean = false
        when (item.itemId) {
            R.id.modoTextoMenuItem -> {
                substituiFragment(MODO_TEXTO)
                retorno = true
            }
            R.id.modoGraficoMenuItem -> {
                substituiFragment(MODO_GRAFICO)
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
        if (menuLateralDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            menuLateralDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed() // se o menu ja estiver recuado ele fecha o aplicativo.
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // referencia o id e qual lugar quero criar o menu, no caso o menu que vem parametrizado.
        menuInflater.inflate(R.menu.menu, menu)
        val compartilharMenuItem: MenuItem? = menu?.findItem(R.id.compartilharMenuItem)
        val compartilharSap: ShareActionProvider = MenuItemCompat.getActionProvider(compartilharMenuItem) as ShareActionProvider

        // Setando Intent do SAP
        compartilharIntent.putExtra(EXTRA_TEXT, packageName)
        compartilharSap.setShareIntent(compartilharIntent)

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

    private fun substituiFragment(modo: String) {
        val modoJogoFragment = if (modo == MODO_GRAFICO) ModoGraficoFragment() else ModoTextoFragment()

        // Transação para substituição de Fragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentJogoFl, modoJogoFragment, modo)
        fragmentTransaction.commit()
    }
}