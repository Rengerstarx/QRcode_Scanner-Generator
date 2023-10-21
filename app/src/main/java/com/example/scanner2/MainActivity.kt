package com.example.scanner2

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class MainActivity : AppCompatActivity() {
    var im: ImageView? = null
    var bGenerate: Button? = null
    var bScanner: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        im = findViewById(R.id.imageView)
        bGenerate = findViewById(R.id.button)
        bScanner = findViewById(R.id.bScan)
        bScanner?.setOnClickListener {
            startActivity(Intent(this, ScannerActivity::class.java))
        }
        bGenerate?.setOnClickListener {
            var textQR=""
            MaterialDialog(this)
                .title(text = "QR-code Генератор")
                .message(text = "Введите текст который необходимо преобразовать в QR-code")
                .input(hint = "Текст для генерации"){dialog, text ->
                    textQR= text.toString()
                    if(textQR!="")
                        generateQrCode(textQR)
                    else
                        Toast.makeText(this, "Поле не может быть пустым", Toast.LENGTH_LONG).show()
                }.positiveButton(text = "Подтвердить") { text ->
                }.negativeButton(text = "Отмена") {

                }.show()
        }
    }

    private fun generateQrCode(text: String){
        try {
            val barcodeEncode = BarcodeEncoder()
            val bitmap : Bitmap = barcodeEncode.encodeBitmap(text, BarcodeFormat.QR_CODE, 750, 750)
            im?.setImageBitmap(bitmap)
        } catch (e: WriterException){}
    }
}