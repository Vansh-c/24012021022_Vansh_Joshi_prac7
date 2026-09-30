package com.example.mad_24012021022_prac7


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.mad_24012021022_prac7.Person

class ContactAdapter(val personlist: ArrayList<Person>, var context: Context) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private val db = DBHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val person = personlist[position]
        holder.tvContactName.text = person.name
        holder.tvContactPhone.text = person.phoneNo
        holder.tvgmail.text = person.emailId
        holder.tvadress.text = person.address

        holder.btnDelete.tag = person.id
        holder.btnDelete.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                val p1 = personlist[currentPosition]
                db.deletePerson(p1)
                personlist.removeAt(currentPosition)
                notifyItemRemoved(currentPosition)
            }
        }

        holder.btnupdate.tag = person.id
        holder.btnupdate.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                val p1 = personlist[currentPosition]
                val intent = Intent(context, UpdateContact::class.java)
                intent.putExtra("id", p1.id)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return personlist.size
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContactName: TextView = itemView.findViewById(R.id.p_name)
        val tvContactPhone: TextView = itemView.findViewById(R.id.p_phone)
        val tvgmail: TextView = itemView.findViewById(R.id.p_gmail)
        val tvadress: TextView = itemView.findViewById(R.id.p_address)
        val btnDelete: FloatingActionButton = itemView.findViewById(R.id.floatingActionButton)
        val btnupdate: Button = itemView.findViewById(R.id.update)
    }
}