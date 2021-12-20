package com.hungngo.foodreceipeappkotlin.dao

import androidx.room.*
import com.hungngo.foodreceipeappkotlin.entity.*

@Dao
interface RecipeDao {
    @Query("SELECT * FROM CategoryItem ORDER BY id DESC")
    suspend fun getAllCategory(): List<CategoryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryItem: CategoryItem)

    @Query("DELETE FROM CategoryItem")
    suspend fun clearDB()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealItems: MealItem?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealEntity(mealEntity: MealsEntity)

    @Query("SELECT * FROM MealItem WHERE categoryName = :categoryName ORDER BY id DESC")
    suspend fun getSpecificMealList(categoryName:String) : List<MealItem>
}