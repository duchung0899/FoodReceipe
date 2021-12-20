package com.hungngo.foodreceipeappkotlin.entity.converter

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hungngo.foodreceipeappkotlin.entity.MealItem

class MealListConverter {
    @TypeConverter
    fun fromCategoryList(category: List<MealItem>):String?{
        if (category == null){
            return (null)
        }else{
            val gson = Gson()
            val type = object : TypeToken<MealItem>(){

            }.type
            return gson.toJson(category,type)
        }
    }

    @TypeConverter
    fun toCategoryList ( categoryString: String):List<MealItem>?{
        if (categoryString == null){
            return (null)
        }else{
            val gson = Gson()
            val type = object :TypeToken<MealItem>(){

            }.type
            return  gson.fromJson(categoryString,type)
        }
    }
}