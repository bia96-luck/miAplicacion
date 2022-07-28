package com.example.miaplicacion.usecases

import android.graphics.Bitmap
import android.graphics.Color
import com.example.miaplicacion.constants.Constants
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

class CreateBitMap {

    companion object {
        fun createBitmap(
            content: String,
            format: String
        ): Bitmap {
            val writer = MultiFormatWriter()
            var bitMatrix: BitMatrix? = null
            when (format.toInt()) {
                Constants.FORMAT_EAN_13 -> bitMatrix = writer.encode(
                    content, BarcodeFormat.EAN_13, 512,512
                )
                Constants.FORMAT_EAN_8 -> bitMatrix = writer.encode(
                    content, BarcodeFormat.EAN_8, 512, 512
                )
                Constants.FORMAT_QR_CODE -> bitMatrix = writer.encode(
                    content, BarcodeFormat.QR_CODE, 512, 512
                )
                Constants.FORMAT_AZTEC -> bitMatrix = writer.encode(
                    content, BarcodeFormat.AZTEC, 512, 512
                )
                Constants.FORMAT_CODABAR -> bitMatrix = writer.encode(
                    content, BarcodeFormat.CODABAR, 512, 512
                )
                Constants.FORMAT_CODE_128 -> bitMatrix = writer.encode(
                    content, BarcodeFormat.CODE_128, 512, 512
                )
                Constants.FORMAT_CODE_39 -> bitMatrix = writer.encode(
                    content, BarcodeFormat.CODE_39, 512, 512
                )
                Constants.FORMAT_CODE_93 -> bitMatrix = writer.encode(
                    content, BarcodeFormat.CODE_93, 512, 512
                )
                Constants.FORMAT_DATA_MATRIX -> bitMatrix = writer.encode(
                    content, BarcodeFormat.DATA_MATRIX, 512, 512
                )
                Constants.FORMAT_ITF -> bitMatrix = writer.encode(
                    content, BarcodeFormat.ITF, 512, 512
                )
                Constants.FORMAT_PDF417 -> bitMatrix = writer.encode(
                    content, BarcodeFormat.PDF_417, 512, 512
                )
                Constants.FORMAT_UPC_A -> bitMatrix = writer.encode(
                    content, BarcodeFormat.UPC_A, 512, 512
                )
                Constants.FORMAT_UPC_E -> bitMatrix = writer.encode(
                    content, BarcodeFormat.UPC_E, 512, 512
                )
            }
            val width: Int = bitMatrix!!.width
            val height: Int = bitMatrix.height
            val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            return bitmap
        }
    }

}