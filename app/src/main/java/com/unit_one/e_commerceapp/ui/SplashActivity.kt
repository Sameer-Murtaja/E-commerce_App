package com.unit_one.e_commerceapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.data.repository.UserRepository
import com.unit_one.e_commerceapp.databinding.ActivitySplashBinding
import com.unit_one.e_commerceapp.ui.base.BaseActivity
import com.unit_one.e_commerceapp.ui.base.BaseViewModel
import com.unit_one.e_commerceapp.ui.select_language.SelectLanguageActivity
import com.unit_one.e_commerceapp.util.Constants

class SplashActivity : BaseActivity<ActivitySplashBinding, BaseViewModel>(
    R.layout.activity_splash,
    BaseViewModel::class.java
) {
    private val auth = Firebase.auth
    lateinit var myIntent: Intent


    override fun setup() {
        animateLogo()

        setNextActivity()

        switchActivityAfterDelay()
    }

    private fun animateLogo() {
        binding.imageLogo.requestLayout()
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        binding.imageLogo.startAnimation(slideAnimation)
    }

    private fun setNextActivity() {
        if (intent.extras?.getString(Constants.nextActivity) == Constants.basket) {
            myIntent = Intent(this, MainActivity::class.java)
            myIntent.putExtra(Constants.nextActivity, Constants.basket)
        } else if (auth.currentUser != null) {
            myIntent = Intent(this, MainActivity::class.java)
            UserRepository().getUserFromFirebase()
        } else {
            myIntent = Intent(this, SelectLanguageActivity::class.java)
        }
    }


    private fun switchActivityAfterDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(myIntent)
            finish()
        }, 2000)
    }


    private fun printFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                val token = it.result //this is the token retrieved
                Log.e("token", "onCreate: $token")
            }
        }
    }


    override fun addCallbacks() {

    }

}