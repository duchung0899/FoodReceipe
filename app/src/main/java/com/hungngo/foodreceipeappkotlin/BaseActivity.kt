package com.hungngo.foodreceipeappkotlin

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseActivity: AppCompatActivity(), CoroutineScope {
    private lateinit var mjob: Job
    override val coroutineContext:CoroutineContext
        get() = mjob +Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mjob = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        mjob.cancel()
    }
}