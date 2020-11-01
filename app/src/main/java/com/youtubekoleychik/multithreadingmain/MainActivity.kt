package com.youtubekoleychik.multithreadingmain

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var btn: Button
    private lateinit var seekBar: SeekBar

    private val TAG = "MAIN_APP_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.btn)
        seekBar = findViewById(R.id.seekBar)
        workWithBtn()
        workWithSeekBar()
    }

    private fun workWithBtn() {
        btn.setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()

            startCoroutines()
        }
    }

    private fun startCoroutines() = CoroutineScope(Dispatchers.Default).launch {
        for (i in (0..10000)){
            delay(1000)
            withContext(Dispatchers.Main){
                btn.text = i.toString()
            }
        }
    }

    fun workWithSeekBar() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                setBgInBtn(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    fun setBgInBtn(value: Int) {
        when (value) {
            0 -> btn.setBackgroundColor(resources.getColor(R.color.whiteYellow))
            1 -> btn.setBackgroundColor(resources.getColor(R.color.whiteBlue))
            2 -> btn.setBackgroundColor(resources.getColor(R.color.whiteRed))
            3 -> btn.setBackgroundColor(resources.getColor(R.color.whiteGreen))
        }
    }























    private fun dataSource() = Observable.create<String> { subscriber ->
        for (i in (0..100000)) {
            Thread.sleep(1000)
            subscriber.onNext(i.toString())
        }
    }

//    val dispose = dataSource()
//        .subscribeOn(Schedulers.newThread())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(
//            { onNext -> btn.text = onNext },
//            { onError -> Log.d(TAG, "ERROR ${onError.message}") },
//            { /* onComplete */ }
//        )

    private fun startNewThread() {

        val thread = Thread {
            Log.d(TAG, "thread start")
            btn.text = "start"
            Thread.sleep(10000)
            Log.d(TAG, "thread stop")
            btn.text = "stop"
        }
        thread.start()
    }

    inner class MyAsyncTask : AsyncTask<Void, Void, Void>() {

        override fun onPreExecute() {
            super.onPreExecute()
            btn.text = "start"
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            Thread.sleep(10000)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            btn.text = "stop"
        }
    }

}