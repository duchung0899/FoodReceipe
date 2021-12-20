package com.hungngo.foodreceipeappkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hungngo.foodreceipeappkotlin.dao.RecipeDao
import com.hungngo.foodreceipeappkotlin.entity.*
import com.hungngo.foodreceipeappkotlin.entity.converter.CategoryListConverter
import com.hungngo.foodreceipeappkotlin.entity.converter.MealListConverter

@Database(
    entities = [Recipe::class, CategoryItem::class, Category::class, MealItem::class, MealsEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CategoryListConverter::class, MealListConverter::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {
        private var INSTANT: RecipeDatabase? = null

//        fun getDatabase(context: Context): RecipeDatabase{
//            val tempInstance = INSTANT
//            if(tempInstance!= null){
//                return tempInstance
//            }
//            synchronized(this){
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    RecipeDatabase::class.java,
//                    "recipe.db"
//                ).build()
//                INSTANT = instance
//                return instance
//            }
//        }

        fun getDatabase(context: Context): RecipeDatabase {
            synchronized(this) {
                if (INSTANT == null) {
                    INSTANT = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeDatabase::class.java,
                        "recipe.db"
                    ).build()
                }
                return INSTANT!!
            }
        }
    }
}