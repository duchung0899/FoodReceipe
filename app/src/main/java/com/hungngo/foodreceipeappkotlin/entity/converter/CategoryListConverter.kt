package com.hungngo.foodreceipeappkotlin.entity.converter

import androidx.room.Entity
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hungngo.foodreceipeappkotlin.entity.CategoryItem


class CategoryListConverter {
    @TypeConverter
    fun fromCategoryList(category: List<CategoryItem>):String?{
        if (category == null){
            return (null)
        }else{
            val gson = Gson()
            val type = object : TypeToken<CategoryItem>(){
            }.type
            return gson.toJson(category,type)
        }
    }

    @TypeConverter
    fun toCategoryList ( categoryString: String):List<CategoryItem>?{
        if (categoryString == null){
            return (null)
        }else{
            val gson = Gson()
            val type = object : TypeToken<CategoryItem>(){
            }.type
            return  gson.fromJson(categoryString,type)
        }
    }
}