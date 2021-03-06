package com.doordash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doordash.fragments.RestaurantsListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView,RestaurantsListFragment()).commitAllowingStateLoss()
        }
    }
}
