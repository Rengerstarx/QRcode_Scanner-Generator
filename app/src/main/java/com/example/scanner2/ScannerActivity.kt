package com.example.scanner2

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class ScannerActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {

    private lateinit var zbView: ZBarScannerView
    private var isFlash = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //zbView.formats
        setContentView(R.layout.activity_scanner)
        checkCameraPermission()
        zbView=findViewById(R.id.zbView)
        zbView.setAutoFocus(true)
        /*findViewById<ImageButton>(R.id.flash).setOnClickListener {
            flashka()
        }*/
    }

    fun flashka(){
        if(isFlash){
            zbView.flash=false
            isFlash=false
        }else{
            zbView.flash=true
            isFlash=true
        }
    }

    override fun onResume() {
        super.onResume()
        zbView.setResultHandler(this)
        zbView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zbView.stopCamera()
        zbView.flash=false
        isFlash=false
    }

    override fun handleResult(result: Result?) {
        val intent = Intent(this,TextActivity::class.java)
        intent.putExtra("QRCODE", result?.contents)
        zbView.flash=false
        isFlash=false
        startActivity(intent)
    }

    private fun checkCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA), 12)

        } else {

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 12){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }else{
                finish()
            }
        }
    }
}