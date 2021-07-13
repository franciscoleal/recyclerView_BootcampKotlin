package com.example.bootcampkotlinviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bootcampkotlin.ClickItemContactListener
import com.example.bootcampkotlin.Contact
import com.example.bootcampkotlin.R

//classe que gerencia nossa lista
//aqui recebo como parametro o metodo ClickItemContactListener
// no construtor do ContactAdapter, para conseguirmos utiliza-la em nosso
//viewHolder
class ContactAdapter(var listener:ClickItemContactListener) : RecyclerView.Adapter<ContactAdapter.ContactAdapterViewHolder>(){

    //armazenar nossaa lista de contatos em uma variavel privada declarada como tipo mutableList
    //com uma classe modelo Contact
    private val list:MutableList<Contact> = mutableListOf()

    //responsavel por criar cada item visual na nossa tela - membro da classe Adapter
    // parece ser igual ao onBindViewHolder, mas não é
    //criação de view, momento que estou criando meu elemento visual, componentes do android
    //e tem o momento que eu populo ele. Portanto, o onBindViewHolder foi popular o nosso item, popular nossa view
    //aqui estamos criando a nossa view, um passo antes, inflando ele e posteriormente popula-lo
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapterViewHolder {
        //começamos nossa view para popular
        //O que é inflate - dizer quem é o meu layout,
        // qual o meu arquivo XML responsavel por mostrar cada
        // item na minha tela - cada card
        // possa no inflate o layout contact_item.xml do nosso card
       val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactAdapterViewHolder(view, list, listener)
    }

    //quantidade de itens existe na nossa lista - membro da classe Adapter
    //é o requisito da classe adapter, ele deseja saber quantos itens tem na nossa lista
    //classe adpater gerenencia os itens da nossa lista
    override fun getItemCount(): Int {
        //retorna o tamanho da minha lista
        return list.size
    }


    //responsavel por rodar em cada item do seu array, obter aquele valor e preencher ele na tela
    //famoso bind, vai popular seu item na lista do recyclerView de fato
    //quando o adapter, ele por sua vez esta fazendo a manipulação da sua lista, para poder renderizar na sua tela,  a cada item que renderiza na tela, que vai ser mostrado na lista,
    //passará por esse método - membro da classe Adapter
    override fun onBindViewHolder(holder: ContactAdapterViewHolder, position: Int) {
        //identificar que existe um metodo bind, acessamos nossa lista,
        // e precisamos saber a posição do item nesse momento para conseguir
        // obter o objeto que esta dentro dessa lista
        //temos uma propriedade desse que metodo que é o position, consegue saber a posição que o recycler
        //esta rodando naquele momento, e partir dai conseguimos obter um item da nossa lista
        holder.bind(list[position])
    }

    //recebe yma lista
    fun updateList(list: List<Contact>){
        //limpar a lista interna antes
        this.list.clear()
        //vamos popular a lista com o parametro que acabamos de receber
        this.list.addAll(list)
        // o que esse metodo faz? notifica meu adapter, que a lista que o
        // adapter utiliza para fazer a renderização foi modifica
        // assim ele consegue popular novamente a minha lista
        notifyDataSetChanged()
    }

    //uma innerclass que gerenciará tudo que tem na nossa lista
    // classe responsavel por gerenciar cada item, o adapter gerencia cada lista
    //nela é onde teremos a declaração de cada elemento que tem no nosso card da nossa lista
    //textView para o nome do contato, textView para o telefone e um ImageView para a foto do contato

    class ContactAdapterViewHolder(itemView: View, var list: List<Contact>, var listener: ClickItemContactListener) : RecyclerView.ViewHolder(itemView){
        //vamos agora obter nosso card no ViewHolder, a fim de conseguir popular cada um deles
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        private val ivPhotograph: ImageView = itemView.findViewById(R.id.iv_photograph)

        // criar o click no meu item
        //quando o usuário tocar em minha lista - evento de click no view do meu elemento.
        init {
            // quando o usuário tocar em um item da minha lista, o método setOnClickListener será chamado
            // um evento de click em cima do item da minha lista
            itemView.setOnClickListener{
                // que irá chamar o método listener.clickItemContact da minha interface que está implementada
                //na minha Mainactivity, que por sua vez vai executar a instrução que ja declaramos na Main
                listener.clickItemContact(list[adapterPosition])
            }
        }

        //é um contato
        //esse metodo bind vai receber como paramento a class Contact,
        // que é basicamente o tipo de cada item da nossa lista
        //espera um objeto do tipo Contact que tiramos da nossa lista.
        //esse método é o que vai popular os dados
        fun bind(contact: Contact){
            //atributo de cada item da minha lista
            tvName.text = contact.name
            tvPhone.text = contact.phone
        }
    }

}