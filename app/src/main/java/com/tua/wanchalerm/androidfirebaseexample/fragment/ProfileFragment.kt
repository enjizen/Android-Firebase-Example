package com.tua.wanchalerm.androidfirebaseexample.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.tua.wanchalerm.androidfirebaseexample.R
import com.tua.wanchalerm.androidfirebaseexample.model.User
import kotlinx.android.synthetic.main.fragment_profile.*

private const val ARG_USER = "user"

class ProfileFragment : Fragment() {
    private var user: User? = null

    private var listener: OnFragmentProfileListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getParcelable(ARG_USER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user?.let {
            nameTextView.text = it.name
            emailTextView.text = it.email
            fireBaseIdTextView.text = it.uid
            profileImageView.setImageURI(it.photoUrl)
        }



        logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            listener?.onLogOutSuccess()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentProfileListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentProfileListener {
        fun onLogOutSuccess()
    }

    companion object {
        @JvmStatic
        fun newInstance(user: User) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_USER, user)
                }
            }
    }
}