package com.sashi.sessionanalytics.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by Sashi Manandhar on 01/12/2024.
 * Senior Android Developer
 * sashimdr8@gmail.com
 **/

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}