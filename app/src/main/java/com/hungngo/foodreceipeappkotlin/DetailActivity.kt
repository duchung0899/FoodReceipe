package com.hungngo.foodreceipeappkotlin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.hungngo.foodreceipeappkotlin.database.RecipeDatabase
import com.hungngo.foodreceipeappkotlin.entity.MealItem
import com.hungngo.foodreceipeappkotlin.entity.MealResponse
import com.hungngo.foodreceipeappkotlin.entity.MealsEntity
import com.hungngo.foodreceipeappkotlin.retrofit.FoodRecipeService
import com.hungngo.foodreceipeappkotlin.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.launch

class DetailActivity : BaseActivity() {
    var mealEntity: MealsEntity? = null
    private var markColor = true
    private var youtubeLink: String = ""
    private lateinit var mCompositeDisposable: CompositeDisposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mCompositeDisposable = CompositeDisposable()

        val idMeal = intent.getStringExtra("IDMEAL")!!

        getSpecificMealFromService(idMeal)


        btn_youtube.setOnClickListener{
            Log.d("TAG", "onCreate: $youtubeLink")
            val uri = Uri.parse(youtubeLink)
            val youtubeIntent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(youtubeIntent)
        }

        imgToolbarBtnBack.setOnClickListener{
            finish()
        }

        imgToolbarBtnFav.setOnClickListener {
            if(markColor){
                it.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.red))
                markColor = false
//                launch {
//                    RecipeDatabase.getDatabase(this@DetailActivity).recipeDao().insertMeal(mealEntity!!)
//                }
            }else{
                it.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.darkGray))
                markColor = true
            }
        }
    }

    private fun getSpecificMealFromService(idMeal: String) {
        mCompositeDisposable.add(
            RetrofitClient.retrofitInstance!!.create(FoodRecipeService::class.java)
                .getSpecificItem(idMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mealEntity = it.mealsEntity[0]
                    showInfoToUser(it)
                }, {
                    Log.d("TAG", "getSpecificMealFromService: ${it.message}")
                })
        )
    }

    private fun showInfoToUser(it: MealResponse?) {
        Glide.with(this).load(it!!.mealsEntity[0].strmealthumb).into(img_dish_detail)
        tv_meal_name.text = it.mealsEntity[0].strmeal

        val ingredient = "${it.mealsEntity[0].stringredient1}      ${it.mealsEntity[0].strmeasure1}\n" +
                "${it.mealsEntity[0].stringredient2}      ${it.mealsEntity[0].strmeasure2}\n" +
                "${it.mealsEntity[0].stringredient3}      ${it.mealsEntity[0].strmeasure3}\n" +
                "${it.mealsEntity[0].stringredient4}      ${it.mealsEntity[0].strmeasure4}\n" +
                "${it.mealsEntity[0].stringredient5}      ${it.mealsEntity[0].strmeasure5}\n" +
                "${it.mealsEntity[0].stringredient6}      ${it.mealsEntity[0].strmeasure6}\n" +
                "${it.mealsEntity[0].stringredient7}      ${it.mealsEntity[0].strmeasure7}\n" +
                "${it.mealsEntity[0].stringredient8}      ${it.mealsEntity[0].strmeasure8}\n" +
                "${it.mealsEntity[0].stringredient9}      ${it.mealsEntity[0].strmeasure9}\n" +
                "${it.mealsEntity[0].stringredient10}      ${it.mealsEntity[0].strmeasure10}\n" +
                "${it.mealsEntity[0].stringredient11}      ${it.mealsEntity[0].strmeasure11}\n" +
                "${it.mealsEntity[0].stringredient12}      ${it.mealsEntity[0].strmeasure12}\n" +
                "${it.mealsEntity[0].stringredient13}      ${it.mealsEntity[0].strmeasure13}\n" +
                "${it.mealsEntity[0].stringredient14}      ${it.mealsEntity[0].strmeasure14}\n" +
                "${it.mealsEntity[0].stringredient15}      ${it.mealsEntity[0].strmeasure15}\n" +
                "${it.mealsEntity[0].stringredient16}      ${it.mealsEntity[0].strmeasure16}\n" +
                "${it.mealsEntity[0].stringredient17}      ${it.mealsEntity[0].strmeasure17}\n" +
                "${it.mealsEntity[0].stringredient18}      ${it.mealsEntity[0].strmeasure18}\n" +
                "${it.mealsEntity[0].stringredient19}      ${it.mealsEntity[0].strmeasure19}\n" +
                "${it.mealsEntity[0].stringredient20}      ${it.mealsEntity[0].strmeasure20}\n"

        tv_ingredients.text = ingredient
        tv_instructions.text = it.mealsEntity[0].strinstructions

        if(it.mealsEntity[0].stryoutube.isNotEmpty()){
            youtubeLink = it.mealsEntity[0].stryoutube
        }else{
            btn_youtube.visibility = View.GONE
        }
    }
}