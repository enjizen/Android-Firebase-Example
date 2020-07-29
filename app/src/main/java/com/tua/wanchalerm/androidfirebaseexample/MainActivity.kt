package com.tua.wanchalerm.androidfirebaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tua.wanchalerm.androidfirebaseexample.fragment.MainFragment
import com.tua.wanchalerm.androidfirebaseexample.fragment.ProfileFragment
import com.tua.wanchalerm.androidfirebaseexample.model.User

class MainActivity : AppCompatActivity(), MainFragment.OnFragmentMainListener, ProfileFragment.OnFragmentProfileListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.contentContainner, MainFragment.newInstance())
                    .commit()
        }
    }

    override fun onLoginSuccess(user: User) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentContainner, ProfileFragment.newInstance(user))
            .commit()
    }

    override fun onLogOutSuccess() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentContainner, MainFragment.newInstance())
            .commit()
    }
}