package com.damars.damardjati_submissiondicodingke2

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.for_detail_user.*

class ForDetailUser : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.for_detail_user)
        setDataUser()
        supportActionBar?.title="Detail of User"
        configurePageView()
    }
    private fun configurePageView() {
        val adapterPageSections = AdapterPageSections(this, supportFragmentManager)
        tv_detailViewPager.adapter = adapterPageSections
        tv_tabs.setupWithViewPager(tv_detailViewPager)
        supportActionBar?.elevation = 0f
    }
    companion object{ const val EXTRA_DATA = "extra_data" }
    private fun setDataUser() {
        val dataUser = intent.getParcelableExtra<ForDataUser>(EXTRA_DATA) as ForDataUser
        Glide.with(this)
                .load(dataUser.thisAvatar)
                .into(tv_onImgAvatar_detail)
        tv_onUsername_detail.text = dataUser.thisUsername
        tv_onNama_detail.text = dataUser.thisName
        tv_onCompany_detail.text = getString(R.string.company, dataUser.thisCompany)
        tv_onRepository_detail.text = getString(R.string.repository, dataUser.thisRepository)
        tv_onLocation_detail.text = getString(R.string.location, dataUser.thisLocation)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.others_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.setChange_Action){
            val moveIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(moveIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}
