package com.example.bootcampkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bootcampkotlin.ContactDetail.Companion.EXTRA_CONTACT
import com.example.bootcampkotlinviews.ContactAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// minha classe atual já implementa o parametro que é pedido pela ContactAdapter, que no caso é ClickItemContactListener
// ou seja é interface que ela pede. colocando como this
class MainActivity : AppCompatActivity(), ClickItemContactListener {
    //declarando uma variavel de classe
    private val rvList: RecyclerView by lazy {
        //alem da classe adapter, o proprio componente recyclerView
        findViewById(R.id.rv_list)
    }

    //declarando nosso adapter - instanciado
    private val adapter = ContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)

        // vai primeiro fazer a chamada da nossa API - simulação
        initDrawer()
        // vai salvar no nosso arquivo preferencias
        fetchListContact()
        // e posteriormente consumimos esses dados do arquivo de preferencias
        // e aplica na tela
        bindViews()


    }

    // vamos simular uma consulta de API, vai fazer uma busca de dados
    // no momento que chamarmos esse metodo, vai criar uma lista e vai salvar no
    // sharedpreferences
    private fun fetchListContact() {
        val list = arrayListOf(
            Contact(
                "Franscisco Leal",
                "(00) 0000-0000",
                "img.png"
            ),
            Contact(
                "Bruna D'Alessio",
                "(00) 9999-0000",
                "img.png"
            )
        )
        getInstanceSharedPreferences().edit {
            val json = Gson().toJson(list)
            //vai converter um objeto de class list em uma string json
            putString("contacts", json)
            // aqui garantimos o retorno da lista
            commit()
        }
    }

    //
    private fun getInstanceSharedPreferences(): SharedPreferences {
        return getSharedPreferences("com.example.bootcampkotlin.PREFERENCES", Context.MODE_PRIVATE)
    }


    //
    private fun initDrawer()
    {
        //nossa view root
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        //referenciar nossa toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //aqui começamos a implementação do nosso toogle, ação de abrir e fechar esse drawerLayout
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        //chamamos o drawerLayout aplicando o Listener do toogle
        drawerLayout.addDrawerListener(toggle)
        //de fato sincronizar o evento do toogle de abrir e fechar com drawerLayout
        toggle.syncState()
    }

    private fun bindViews(){
        //o adpter sera usado nesse recyclerView
        rvList.adapter = adapter
        // qual sera o viewgroup, saber como ele vai se estruturar, como é a forma dele
        // precisa basicamente de um contexto
        rvList.layoutManager = LinearLayoutManager(this)
        updateList()
    }

    // obtenção da nossa lista de contatos, do nosso objeto de preferencia de usuário
    private fun getListContacts(): List<Contact>{
        // obter a instancia do sharedPreferences,
        // getString para nossa lista contacts, obtem uma string do objeto de preferencias de usuario
        // ele necessita que passe uma chave como parametro inicial, e um valor padrão caso não exista ou seja nulo
        var list = getInstanceSharedPreferences().getString("contacts", "[]")
        //vamos fazer a conversão dessa string para objeto de classe
        val turnsType = object : TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(list, turnsType)
    }

    private fun updateList(){
        //após ter instanciado nosso adapter,
        // vamos chamar o método updateList do ContactAdpter.kt que faz a alteração da lista
        //vamos criar uma lista mocada, fake
        val list = getListContacts()
        adapter.updateList(list)
    }

    //encapsular o nosso toast
    private fun showToast(message: String){
        // criamos um toast, passando o contexto, a mensagem, e o tempo
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    //criamos nossa estrutura de menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //inflaremos esse nosso menu que acabamos de criar
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    // esse método serve como uma especie de listener, sempre que o usuário tocar em um item do menu
    // a gente conseguirá identificar em qual item que ele tocou naquele momento,
    // e assim executar uma determinada ação
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //id do item que foi tocado,
        // aqui é o momento que vamos conseguir
        // saber qual o id que foi clicado, ou seja,
        // qual o item que foi clicado a partir do menu que foi tocado
        return when (item.itemId){
            R.id.item_menu_1 -> {
                // vamos fazer a demonstração de um toast, demonstrando que ele foi tocado
                showToast("Exibindo item de menu 1")
                //passamos um boolean que é o que o método espera
                true
            }
            R.id.item_menu_2 -> {
                showToast("Exibindo item de menu 2")
                true
                // se não execute o padrão desse método
            } else -> super.onOptionsItemSelected(item)
        }
    }
    //trafego de dados entre essas telas no android com serializar e desserealizar
    //quando eu tocar em cada item da lista, eu devo abrir minha activity
    override fun clickItemContact(contact: Contact){
        //instrução inicial para iniciar minha activity ContactDetail
        val intent = Intent(this, ContactDetail::class.java)
        // esse é um método chave/valor, no qual a gente coloca a chave e valor para identifica-lo
        // conseguindo recuperar pela mesma chave e valor que foi passado
        // trafegando chave: EXTRA_CONTACT e valor: contact
        intent.putExtra(EXTRA_CONTACT, contact)
        // ao tocar nesse item, ele vai abrir minha tela
        startActivity(intent)
    }
}