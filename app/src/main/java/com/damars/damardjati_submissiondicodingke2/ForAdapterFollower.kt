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

var filterFollower = ArrayList<ForDataUser>()
class ForAdapterFollower(listUser: ArrayList<ForDataUser>) : RecyclerView.Adapter<ForAdapterFollower.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageAvatar: CircleImageView = itemView.image_Avatar
        var tvOnName: TextView = itemView.tv_onName_card
        var tvOnUsername: TextView = itemView.tv_onUsername_card
        var tvOnLocations: TextView = itemView.tv_onLocation_card
        var tvOnCompany: TextView = itemView.tv_onCompany_card
    }

    init{ filterFollower = listUser}
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ForAdapterFollower.ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_users_card, viewGroup, false)
        val holderListuser = ListViewHolder(view)
        mainContext = viewGroup.context
        return holderListuser
    }

    override fun onBindViewHolder(holder: ForAdapterFollower.ListViewHolder, position: Int) {
        val dataFollower = filterFollower[position]
        holder.tvOnName.text = dataFollower.thisName
        holder.tvOnUsername.text = dataFollower.thisUsername
        Glide.with(holder.itemView.context)
                .load(dataFollower.thisAvatar)
                .apply(RequestOptions().override(225,225))
                .into(holder.imageAvatar)
        holder.tvOnLocations.text = dataFollower.thisLocation
        holder.tvOnCompany.text = dataFollower.thisCompany
        holder.itemView.setOnClickListener {//..
        }
    }

    override fun getItemCount(): Int = filterFollower.size
    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback { fun onItemClicked(ForDataUser: ForDataUser) }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
}