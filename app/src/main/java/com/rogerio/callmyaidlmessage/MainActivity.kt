package com.rogerio.callmyaidlmessage

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.rogerio.aidlservicesample.IServiceAIDLInterface
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var aidlInterface: IServiceAIDLInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSend.setOnClickListener {
            val text = when(edtText.text.isEmpty()) {
                true -> ""
                false -> edtText.text.toString()
            }
            aidlInterface?.sendMessage(text)
        }
        val intent = Intent("com.rogerio.aidlservicesample.SHOWMESSAGE")
        intent.setPackage("com.rogerio.aidlservicesample")
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            aidlInterface = IServiceAIDLInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            aidlInterface  =  null
        }
    }

}
