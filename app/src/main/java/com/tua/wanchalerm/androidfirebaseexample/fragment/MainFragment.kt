package com.tua.wanchalerm.androidfirebaseexample.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.tua.wanchalerm.androidfirebaseexample.R
import com.tua.wanchalerm.androidfirebaseexample.model.User
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {


    private lateinit var mAuth : FirebaseAuth
    private lateinit var mAuthListener: AuthStateListener
    private var listener: OnFragmentMainListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

       /* mAuthListener = AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                // User is signed in
            } else {
                // User is signed out
            }
            // ...
        }*/

        mAuthListener = AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser

            if (user != null) {
                // User is signed in
                val name = user.displayName
                val email = user.email
                val photoUrl: Uri? = user.photoUrl
                val uid = user.uid

                User(name = name,email = email,photoUrl = photoUrl, uid = uid).run {
                    listener?.onLoginSuccess(this)
                }

            } else {
                // User is signed out
                Toast.makeText(context, "User is signed out", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            signInWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
            if (!task.isSuccessful) {
                Log.w("MainFrgment", "signInWithEmail, ${task.exception?.message}");
               // Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(context, "Authentication Success.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentMainListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentMainListener {
        fun onLoginSuccess(user: User)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
}