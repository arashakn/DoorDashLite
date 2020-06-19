package com.doordash.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.doordash.R
import com.doordash.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.images_list_fragment.*
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: RestaurantsListViewModel // we need better naming since it's shared between all fragments

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {

            viewModel =
                ViewModelProvider(it).get(RestaurantsListViewModel::class.java) ////makes the ViewModel scoop to activity rather than fragment
            //viewModel.fetchAllRestaurant()
            observeViewModels()
            btnLogin.setOnClickListener {
                val email = etEmail.text
                val pass = etPassword.text
                if (email.toString().isEmailValid() && !pass.toString()
                        .isNullOrEmpty()
                ) { // we can have better rules for password and email
                    Log.d("btnLogin", "email -> $email , pass $pass")
                    viewModel.fetchUserInfo(email = email.toString(), password = pass.toString())
                }
            }
        }

    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this)
            .matches()
    }

    private fun observeViewModels() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            if (it.token != null) {
                activity?.let {
                    it.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, RestaurantsListFragment())
                        .addToBackStack(null)
                        .commitAllowingStateLoss()
                }

            }
        }
        )
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
//                    Toast.makeText(activity, "Network Error!", Toast.LENGTH_LONG)
//                        .show() // In case of network error, a message will be displayed to the user
                }
            }
        })
    }

}
