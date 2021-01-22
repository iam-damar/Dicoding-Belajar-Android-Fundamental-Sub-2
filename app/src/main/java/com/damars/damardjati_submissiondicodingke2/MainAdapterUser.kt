package com.damars.damardjati_submissiondicodingke2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.list_users_card.view.*
import java.util.*
import kotlin.collections.ArrayList

lateinit var mainContext: Context
var filterListUser = ArrayList<ForDataUser>()
class MainAdapterUser(private var listData: ArrayList<ForDataUser>) : RecyclerView.Adapter<MainAdapterUser.ListViewHolder>(), Filterable {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_users_card, viewGroup, false)
        val viewUser = ListViewHolder(view)
        mainContext = viewGroup.context
        return viewUser
    }

    override fun onBindViewHolder(holder: MainAdapterUser.ListViewHolder, position: Int) {
        val data = filterListUser[position]
        Glide.with(holder.itemView.context)
                .load(data.thisAvatar)
                .apply(RequestOptions().override(225,225))
                .into(holder.imageAvatar)
        holder.tvOnName.text = data.thisName
        holder.tvOnUsername.text = data.thisUsername
        holder.tvOnLocation.text = data.thisLocation
        holder.tvOnCompany.text = data.thisCompany
        holder.itemView.setOnClickListener {
            val dataUser = ForDataUser(
                    data.thisUsername,
                    data.thisName,
                    data.thisCompany,
                    data.thisAvatar,
                    data.thisLocation,
                    data.thisRepository,
                    data.thisFollower,
                    data.thisFollowing
            )
            val detailIntent = Intent(mainContext, ForDetailUser::class.java)
            detailIntent.putExtra(ForDetailUser.EXTRA_DATA, dataUser)
            mainContext.startActivity(detailIntent)
        }
    }
    init { filterListUser = listData }
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageAvatar: CircleImageView = itemView.image_Avatar
        var tvOnName: TextView = itemView.tv_onName_card
        var tvOnUsername: TextView = itemView.tv_onUsername_card
        var tvOnLocation: TextView = itemView.tv_onLocation_card
        var tvOnCompany: TextView = itemView.tv_onCompany_card
    }

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(dataUsers: ForDataUser)
    }

    override fun getItemCount(): Int = filterListUser.size
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val searchString = constraint.toString()
                filterListUser = if(searchString.isEmpty()) {
                    listData
                }else {
                    val listResult = ArrayList<ForDataUser>()
                    for(row in filterListUser){
                        if((row.thisUsername.toString().toLowerCase(Locale.ROOT)
                                .contains(searchString.toLowerCase(Locale.ROOT)))
                        ) {
                            listResult.add(
                                ForDataUser(
                                    row.thisUsername,
                                    row.thisName,
                                    row.thisCompany,
                                    row.thisAvatar,
                                    row.thisLocation,
                                    row.thisRepository,
                                    row.thisFollower,
                                    row.thisFollowing)
                            )
                        }
                    }
                    listResult }
                    val resultOnFilter = FilterResults()
                    resultOnFilter.values = filterListUser
                    return resultOnFilter
            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filterListUser = results.values as ArrayList<ForDataUser>
                notifyDataSetChanged()
            }
        }
    }
}