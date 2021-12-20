package com.hungngo.foodreceipeappkotlin.retrofit

import com.hungngo.foodreceipeappkotlin.entity.Category
import com.hungngo.foodreceipeappkotlin.entity.Meal
import com.hungngo.foodreceipeappkotlin.entity.MealResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface FoodRecipeService {
    @GET("categories.php")
    fun getCategoryList(): Observable<Category>

    @GET("filter.php")
    fun getMealList(@Query("c") category: String): Observable<Meal>

    @GET("lookup.php")
    fun getSpecificItem(@Query("i") id: String): Observable<MealResponse>
}