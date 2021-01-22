package com.damars.damardjati_submissiondicodingke2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AboutCreator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_creator)

        val foractionbar = supportActionBar
        if (foractionbar != null) {
            foractionbar.title = "About Me!"
        }
    }
}