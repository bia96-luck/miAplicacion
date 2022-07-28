package com.example.miaplicacion.constants

class Constants {

    companion object {

        const val DATABASE_NAME:String = "cardsDB"
        const val COLUMN_NAME_ID:String = "cards_id"
        const val COLUMN_NAME_DATE:String = "cards_date"
        const val COLUMN_NAME_RAW_VALUE:String = "raw_value"
        const val COLUMN_NAME_BARCODE_TYPE:String = "barcode_type"
        const val COLUMN_NAME_STORE_NAME:String = "store_name"
        const val COLUMN_NAME_STORE_NOTES:String = "store_notes"
        const val COLUMN_NAME_LAST_USE_DATE:String = "last_use_date"
        const val COLUMN_NAME_USE_TIMES:String = "use_times"
        const val COLUMN_NAME_STORE_TYPE:String = "store_type"
        const val COLUMN_NAME_CARDS_FAV:String = "cards_fav"

        // format string values
        const val FORMAT_EAN_13: Int = 32
        const val FORMAT_EAN_8: Int = 64
        const val FORMAT_QR_CODE: Int = 256
        const val FORMAT_AZTEC: Int = 4090
        const val FORMAT_CODABAR: Int = 8
        const val FORMAT_CODE_128: Int = 1
        const val FORMAT_CODE_39: Int = 2
        const val FORMAT_CODE_93: Int = 4
        const val FORMAT_DATA_MATRIX: Int = 16
        const val FORMAT_ITF: Int = 128
        const val FORMAT_PDF417: Int = 2048
        const val FORMAT_UPC_A: Int = 512
        const val FORMAT_UPC_E: Int = 1024

    }

}