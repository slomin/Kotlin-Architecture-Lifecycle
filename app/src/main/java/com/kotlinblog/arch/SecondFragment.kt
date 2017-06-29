package com.kotlinblog.arch

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Getting instance of the SharedViewModel
        val sharedViewModel = ViewModelProviders.of(activity).get(SharedViewModel::class.java)
        btnStart.setOnClickListener { sharedViewModel.startTimer() }
        btnStop.setOnClickListener { sharedViewModel.stopTimer() }
        btnToast.setOnClickListener { sharedViewModel.showLog() }
    }

}
