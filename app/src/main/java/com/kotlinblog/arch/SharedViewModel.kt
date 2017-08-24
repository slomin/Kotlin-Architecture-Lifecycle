package com.kotlinblog.arch

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.SystemClock
import android.util.Log
import java.util.*

class SharedViewModel : ViewModel() {

    private val LOG_TAG = "KotlinBlog.com"

    private var mTimer: Timer? = null

    private val mFormattedTime = MutableLiveData<String>()
    private val mTimerState = TimerStateModel(false, null)

    // Getter for mFormattedTime LiveData, cast to immutable value
    val formattedTime: LiveData<String> get() = mFormattedTime

    fun startTimer() {
        if (mTimer != null) {
            return
        }

        val initialTime = SystemClock.elapsedRealtime()
        mTimer = Timer()
        mTimer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val secondsPassed = (SystemClock.elapsedRealtime() - initialTime) / 1000L
                mFormattedTime.postValue(formatTime(secondsPassed))
            }
        }, 1000L, 1000L)

        mTimerState.isRunning = true
        mTimerState.startedAt = Calendar.getInstance().time
    }

    fun stopTimer() {
        if (mTimer != null) {
            mTimer?.cancel()
            mTimer = null
            mFormattedTime.value = formatTime(0L)
            mTimerState.startedAt = null
            mTimerState.isRunning = false
        }
    }

    fun showLog() {
        Log.d(LOG_TAG, "Is mTimer running now: ${mTimerState.isRunning}")
        Log.d(LOG_TAG, "Timer started at: ${mTimerState.startedAt}")
    }

    private fun formatTime(elapsedTime: Long): String {
        val seconds = elapsedTime % 3600 % 60
        val minutes = elapsedTime % 3600 / 60
        val hours = elapsedTime / 3600

        val hh = if (hours < 10) "0$hours" else hours.toString()
        val mm = if (minutes < 10) "0$minutes" else minutes.toString()
        val ss = if (seconds < 10) "0$seconds" else seconds.toString()

        return "$hh:$mm:$ss"
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("KotlinBlog.com", "onCleared() called, Do all the ViewModel's teardown here")
    }
}