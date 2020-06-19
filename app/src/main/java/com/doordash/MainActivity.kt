package com.doordash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doordash.fragments.LoginFragment
import com.doordash.fragments.RestaurantsListFragment
import com.doordash.repository.MainRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null){
            val token = MainRepository.getToken(MainRepository.SP_USER_INFO)
            if(token != null){
                supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView,RestaurantsListFragment()).commitAllowingStateLoss()
            }
            else{
                supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView,LoginFragment()).commitAllowingStateLoss()
            }
        }
    }
}
