package com.hungngo.foodreceipeappkotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.hungngo.foodreceipeappkotlin.database.RecipeDatabase
import com.hungngo.foodreceipeappkotlin.entity.Category
import com.hungngo.foodreceipeappkotlin.entity.Meal
import com.hungngo.foodreceipeappkotlin.entity.MealItem
import com.hungngo.foodreceipeappkotlin.retrofit.FoodRecipeService
import com.hungngo.foodreceipeappkotlin.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class SplashActivity : BaseActivity(), EasyPermissions.RationaleCallbacks,
    EasyPermissions.PermissionCallbacks {
    private lateinit var mCompositeDisposable: CompositeDisposable
    private var READ_STORAGE_PERM = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mCompositeDisposable = CompositeDisposable()

        readStorageTask()

        btn_start.setOnClickListener {
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun getCategories() {
        mCompositeDisposable.add(
            RetrofitClient.retrofitInstance!!.create(FoodRecipeService::class.java).getCategoryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    for (arr in it!!.categorieitems!!) {
                        getMeal(arr.strcategory)
                    }
                    insertDataIntoRoomDb(it)
                },{
                    Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                })
        )
    }


    private fun getMeal(categoryName: String) {
        mCompositeDisposable.add(
            RetrofitClient.retrofitInstance!!.create(FoodRecipeService::class.java)
                .getMealList(categoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    insertMealDataIntoRoomDb(categoryName, it)
                }, {
                    loader.visibility = View.INVISIBLE
                    Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                })
        )
    }

    private fun insertDataIntoRoomDb(category: Category?) {
        launch {
            this.let {
                for (arr in category!!.categorieitems!!) {
                    RecipeDatabase.getDatabase(this@SplashActivity)
                        .recipeDao().insertCategory(arr)
                }
            }
        }
    }

    private fun insertMealDataIntoRoomDb(categoryName: String, meal: Meal?) {
        launch {
            this.let {
                for (arr in meal!!.mealsItem!!) {
                    val mealItemModel = MealItem(
                        arr.id,
                        arr.idMeal,
                        categoryName,
                        arr.strMeal,
                        arr.strMealThumb
                    )
                    RecipeDatabase.getDatabase(this@SplashActivity)
                        .recipeDao().insertMeal(mealItemModel)
                    Log.d("mealData", arr.toString())
                }
                btn_start.visibility = View.VISIBLE
            }
        }
    }

    private fun clearDataBase() {
        launch {
            this.let {
                RecipeDatabase.getDatabase(this@SplashActivity).recipeDao().clearDB()
            }
        }
    }

    private fun hasReadStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun readStorageTask() {
        if (hasReadStoragePermission()) {
            clearDataBase()
            getCategories()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your storage,",
                READ_STORAGE_PERM,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }
}