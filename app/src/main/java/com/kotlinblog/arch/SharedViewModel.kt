package com.kotlinblog.arch

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.SystemClock
import android.util.Log
import java.util.*

class SharedViewModel : ViewModel() {

    private val LOG_TAG = "KotlinBlog.com"

    private val formattedTime = MutableLiveData<String>()
    private val timerState = TimerStateModel(false, null)

    private var timer: Timer? = null

    // Getter for formattedTime LiveData, cast to immutable value
    val time: LiveData<String> get() = formattedTime

    fun startTimer() {
        if (timer != null) {
            return
        }

        val initialTime = SystemClock.elapsedRealtime()
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val secondsPassed = (SystemClock.elapsedRealtime() - initialTime) / 1000L
                formattedTime.postValue(formatTime(secondsPassed))
            }
        }, 1000L, 1000L)

        timerState.isRunning = true
        timerState.startedAt = Calendar.getInstance().time
    }

    fun stopTimer() {
        if (timer != null) {
            timer?.cancel()
            timer = null
            formattedTime.value = formatTime(0L)
            timerState.startedAt = null
            timerState.isRunning = false
        }
    }

    fun showLog() {
        Log.d(LOG_TAG, "Is timer running now: ${timerState.isRunning}")
        Log.d(LOG_TAG, "Timer started at: ${timerState.startedAt}")
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