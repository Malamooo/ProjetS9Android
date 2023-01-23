package com.example.projets9.api

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.projets9.R
import com.example.projets9.led.LedStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class APIActivity : AppCompatActivity() {

    private lateinit var ledStatus: LedStatus
    private var toggleLed2: Button? = null
    private var ledStatus2: ImageView? = null

    companion object {
        const val PI_IDENTIFIER = "PI_IDENTIFIER"
        fun getStartIntent(context: Context, piIdentifier: String?): Intent {
            return Intent(context, APIActivity::class.java).apply {
                putExtra(PI_IDENTIFIER, piIdentifier)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apiactivity)

        supportActionBar?.title = "Commander"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggleLed2?.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                runCatching {
                    ledStatus = APIService.instance.writeStatus(ledStatus.reverseStatus())
                    setVisualState()
                }
            }
        }
    }

    private fun getStatus() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                val readStatus = APIService.instance.readStatus(ledStatus.identifier)
                ledStatus.setStatus(readStatus.status)
                setVisualState()
            }
        }
    }

    override fun onResume(){
        super.onResume()
        getStatus()
    }

    private fun setVisualState(){
        if ( ledStatus.status){
            ledStatus2?.setImageResource(R.drawable.led_on)
        } else {
            ledStatus2?.setImageResource(R.drawable.led_off)
        }
    }
}