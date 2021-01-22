package com.damars.damardjati_submissiondicodingke2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.list_users_card.view.*

var filterFollowing = ArrayList<ForDataUser>()
class ForAdapterFollowing(listUser: ArrayList<ForDataUser>): RecyclerView.Adapter<ForAdapterFollowing.ListViewHolder>(){

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageAvatar: CircleImageView = itemView.image_Avatar
        var tvOnName: TextView = itemView.tv_onName_card
        var tvOnUsername: TextView = itemView.tv_onUsername_card
        var tvOnCompany: TextView = itemView.tv_onCompany_card
        var tvOnLocation: TextView = itemView.tv_onLocation_card
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ForAdapterFollowing.ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_users_card, viewGroup, false)
        val holderListUser = ListViewHolder(view)
        mainContext = viewGroup.context
        return holderListUser
    }

    override fun onBindViewHolder(holder: ForAdapterFollowing.ListViewHolder, position: Int) {
        val dataFollowing = filterFollowing[position]
        holder.tvOnName.text = dataFollowing.thisName
        holder.tvOnUsername.text = dataFollowing.thisUsername
        Glide.with(holder.itemView.context)
                .load(dataFollowing.thisAvatar)
                .apply(RequestOptions().override(225,225))
                .into(holder.imageAvatar)
        holder.tvOnLocation.text = dataFollowing.thisLocation
        holder.tvOnCompany.text = dataFollowing.thisCompany
        holder.itemView.setOnClickListener { //..
        }
    }
    init { filterFollowing = listUser}
    override fun getItemCount(): Int = filterFollowing.size

    private var onItemClickCallback: OnItemClickCallback? = null
    interface OnItemClickCallback { fun onItemClicked(ForDataUser: ForDataUser) }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
}