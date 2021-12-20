package com.hungngo.foodreceipeappkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hungngo.foodreceipeappkotlin.R
import com.hungngo.foodreceipeappkotlin.entity.CategoryItem
import com.hungngo.foodreceipeappkotlin.entity.Recipe

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    var listener : clickListener? = null
    lateinit var context: Context
    var listCategory = ArrayList<CategoryItem>()

    fun setData(listCategory: ArrayList<CategoryItem>){
        this.listCategory = listCategory
        notifyDataSetChanged()
    }

    fun setOnclick(listener: clickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryItem = listCategory[position]
        holder.tvName.text = categoryItem.strcategory
        Glide.with(context).load(categoryItem.strcategorythumb).into(holder.imgCategory)

        holder.itemView.setOnClickListener {
            listener?.onClick(categoryItem.strcategory)
        }
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imgCategory: ImageView = itemView.findViewById(R.id.img_dish)
        val tvName: TextView = itemView.findViewById(R.id.tv_cat_name)
    }

    interface clickListener{
        fun onClick(categoryName: String)
    }
}