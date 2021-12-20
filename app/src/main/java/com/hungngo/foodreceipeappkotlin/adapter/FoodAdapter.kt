package com.hungngo.foodreceipeappkotlin.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hungngo.foodreceipeappkotlin.DetailActivity
import com.hungngo.foodreceipeappkotlin.R
import com.hungngo.foodreceipeappkotlin.entity.MealItem

class FoodAdapter: RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    var listener: OnClickListener? = null
    var listFood = ArrayList<MealItem>()
    var context: Context? = null
    fun setData(listFood: ArrayList<MealItem>){
        this.listFood = listFood
        notifyDataSetChanged()
    }

    fun setOnclick(listener: OnClickListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context  = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = listFood[position]
        holder.tvName.text = food.strMeal
        Glide.with(context!!).load(food.strMealThumb).into(holder.imgCategory)

        holder.itemView.setOnClickListener {
            listener?.onClick(food.idMeal)
        }
    }

    override fun getItemCount(): Int {
        return listFood.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imgCategory: ImageView = itemView.findViewById(R.id.img_dish)
        val tvName: TextView = itemView.findViewById(R.id.tv_dish_name)
    }

    interface OnClickListener{
        fun onClick(id: String)
    }
}