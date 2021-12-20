package com.hungngo.foodreceipeappkotlin.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MealResponse(
    @Expose
    @SerializedName("meals")
    var mealsEntity: List<MealsEntity>
)

