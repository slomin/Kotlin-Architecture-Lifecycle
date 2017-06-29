package com.kotlinblog.arch

import android.arch.lifecycle.LifecycleActivity
import android.os.Bundle

class SharedActivity : LifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
