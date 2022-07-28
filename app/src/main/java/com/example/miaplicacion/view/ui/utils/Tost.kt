package com.example.miaplicacion.view

import android.content.Context
import android.widget.Toast

fun Context.tost(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Context.tostLong(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
