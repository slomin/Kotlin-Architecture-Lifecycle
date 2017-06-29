package com.kotlinblog.arch

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_first, container, false)
        observe()
        return rootView
    }

    fun observe() {
        // Creating an observer
        val timeObserver = Observer<String> { formattedTime ->
            if (formattedTime != null) {
                tvTimer.text = formattedTime
            }
        }

        // Getting instance of the SharedViewModel
        val sharedViewModel = ViewModelProviders.of(activity).get(SharedViewModel::class.java)

        // Subscribing to the observer
        sharedViewModel.time.observe(activity as LifecycleOwner, timeObserver)
    }

}
