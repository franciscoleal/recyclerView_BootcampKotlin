package com.example.bootcampkotlin


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class ContactDetail : AppCompatActivity() {
    //armazenar os dados do nosso contato
    private var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        initToolbar()
        getExtras()
        bindViews()
    }

    private fun initToolbar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        //exibe o botão voltar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //receber os valores via EXSTRAS, vindo da tela anterior
    private fun getExtras(){
        // os dados serão salvos na variavel contact
        // passando a chave: EXTRA_CONTACT referente aos dados que veio nessa
        // intent, ou seja, com isso, estou obtendo os dados
        // da tela que abriu a Detail
        contact = intent.getParcelableExtra(EXTRA_CONTACT)
    }

    //setar os dados na nossa tela
    private fun bindViews(){
        //obtendo os campos name e phone, fará os binds
        findViewById<TextView>(R.id.tv_name).text = contact?.name
        findViewById<TextView>(R.id.tv_phone).text = contact?.phone
    }

    //
    companion object {
        const val EXTRA_CONTACT:String = "EXTRA_CONTACT"
    }

    //responsavel por voltar para tela anterior ao clicar no botão voltar
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}