package com.hungngo.foodreceipeappkotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hungngo.foodreceipeappkotlin.adapter.CategoryAdapter
import com.hungngo.foodreceipeappkotlin.adapter.FoodAdapter
import com.hungngo.foodreceipeappkotlin.database.RecipeDatabase
import com.hungngo.foodreceipeappkotlin.entity.Category
import com.hungngo.foodreceipeappkotlin.entity.CategoryItem
import com.hungngo.foodreceipeappkotlin.entity.MealItem
import com.hungngo.foodreceipeappkotlin.entity.Recipe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import com.hungngo.foodreceipeappkotlin.retrofit.FoodRecipeService
import com.hungngo.foodreceipeappkotlin.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.food_item.*
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : BaseActivity() {
    lateinit var mCompositeDisposal: CompositeDisposable
    var listCategory = ArrayList<CategoryItem>()
    var listFood = ArrayList<MealItem>()

    val categoryAdapter = CategoryAdapter()
    val foodAdapter = FoodAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mCompositeDisposal = CompositeDisposable()

//        getMainCategoryFromService()

        getDataFromDB()

        categoryAdapter.setOnclick(categoryListener)
        foodAdapter.setOnclick(foodListener)

        rvc_category.adapter = categoryAdapter
        rvc_category.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvc_food.adapter = foodAdapter
        rvc_food.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private val categoryListener = object : CategoryAdapter.clickListener{
        override fun onClick(categoryName: String) {
//            getMealFromService(categoryName)
            getMealFromDB(categoryName)
        }
    }

    private val foodListener = object : FoodAdapter.OnClickListener{
        override fun onClick(id: String) {
            Intent(this@HomeActivity, DetailActivity::class.java).also {
                it.putExtra("IDMEAL", id)
                startActivity(it)
            }
        }
    }

//    private fun getMainCategoryFromService(){
//        mCompositeDisposal.add(
//            RetrofitClient.retrofitInstance!!.create(FoodRecipeService::class.java)
//                .getCategoryList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    listCategory = it.categorieitems as ArrayList<CategoryItem>
//                    categoryAdapter.setData(listCategory)
//                    getMealFromService(listCategory[0].strcategory)
//                }, {
//                    Log.d("TAG", "getMainCategoryFromService: ${it.message}")
//                    Toast.makeText(this@HomeActivity, "Connect to Service failed", Toast.LENGTH_SHORT).show()
//                })
//        )
//    }
//
//    private fun getMealFromService(categoryName: String){
//        tv_category.text = "$categoryName Category"
//        mCompositeDisposal.add(
//            RetrofitClient.retrofitInstance!!.create(FoodRecipeService::class.java)
//                .getMealList(categoryName)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    listFood = it.mealsItem as ArrayList<MealItem>
//                    foodAdapter.setData(listFood)
//                },{
//                    Log.d("TAG", "getMeealFromService: ${it.message}")
//                    Toast.makeText(this@HomeActivity, "Connect to Service failed", Toast.LENGTH_SHORT).show()
//                })
//        )
//    }

    private fun getDataFromDB(){
        launch {
            this.let {
                val categories = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getAllCategory()
                listCategory = categories as ArrayList<CategoryItem>
                categoryAdapter.setData(listCategory)
            }
        }
    }

    private fun getMealFromDB(categoryName: String){
        tv_category.text = "$categoryName Category"
        launch {
            this.let {
                listFood = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao()
                    .getSpecificMealList(categoryName) as ArrayList<MealItem>
                foodAdapter.setData(listFood)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposal.clear()
    }
}