package com.damars.damardjati_submissiondicodingke2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_for_follower.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.Exception

class ForFragmentFollower : Fragment() {

    private lateinit var adapter: ForAdapterFollower
    private val listUser: ArrayList<ForDataUser> = ArrayList()
    companion object{
        const val EXTRA_DATA = "extra_data"
        private val MainTag = ForFragmentFollower::class.java.simpleName
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ForAdapterFollower(listUser)
        listUser.clear()
        val dataFollowerUser = activity!!.intent.getParcelableExtra<ForDataUser>(EXTRA_DATA) as ForDataUser
        gettingFollowerUser(dataFollowerUser.thisUsername.toString())
    }

    private fun gettingFollowerUser(id: String) {
        progressBarOnFollower.visibility = View.VISIBLE
        val aClient = AsyncHttpClient()
        aClient.addHeader("User-Agent","request")
        aClient.addHeader("Authorization", "token 3524f98d046b487a50bc4b16d40193a616e811ac")
        aClient.get("https://api.github.com/users/$id/followers", object: AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                progressBarOnFollower.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(MainTag, result)
                try{
                    val jsonArray = JSONArray(result)
                    for(i in 0 until jsonArray.length()){
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDetailOfUser(username)
                    }
                }catch (e: Exception){
                    Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                mainProgressBar.visibility = View.INVISIBLE
                val messageError = when (statusCode) {
                    401 -> "$statusCode : Bad Requested"
                    403 -> "$statusCode : That's Forbidden"
                    404 -> "$statusCode : Sorry Not Found"
                    else -> "$statusCode : $(error.message)"
                }
                Toast.makeText(activity, messageError, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDetailOfUser(id: String) {
        progressBarOnFollower.visibility = View.VISIBLE
        val aClient = AsyncHttpClient()
        aClient.addHeader("User-Agent","request")
        aClient.addHeader("Authorization", "token 3524f98d046b487a50bc4b16d40193a616e811ac")
        aClient.get("https://api.github.com/users/$id", object: AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                if(progressBarOnFollower != null) {
                    progressBarOnFollower.visibility = View.INVISIBLE
                }
                val result = responseBody?.let { String(it) }
                if (result != null) {
                    Log.d(MainTag, result)
                    try{
                        val jsonObject = JSONObject(result)
                        val username: String = jsonObject.getString("login").toString()
                        val name: String = jsonObject.getString("name").toString()
                        val avatar: String = jsonObject.getString("avatar_url").toString()
                        val company: String = jsonObject.getString("company").toString()
                        val location: String = jsonObject.getString("location").toString()
                        val repository: String = jsonObject.getString("public_repos")
                        val follower: String = jsonObject.getString("followers")
                        val following: String = jsonObject.getString("following")
                        listUser.add(
                                ForDataUser(
                                        username, name, company, avatar, location, repository, follower, following
                                )
                        )
                        recyclerShowList()
                    }catch (e: Exception){
                        Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                mainProgressBar.visibility = View.INVISIBLE
                val messageError = when (statusCode) {
                    401 -> "$statusCode : Bad Requested"
                    403 -> "$statusCode : That's Forbidden"
                    404 -> "$statusCode : Sorry Not Found"
                    else -> "$statusCode : $(error.message)"
                }
                Toast.makeText(activity, messageError, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun recyclerShowList() {
        RecViewOnFollower.layoutManager = LinearLayoutManager(activity)
        val dataListAdapter = ForAdapterFollower(filterFollower)
        RecViewOnFollower.adapter = adapter

        dataListAdapter.setOnItemClickCallback(object : ForAdapterFollower.OnItemClickCallback{
            override fun onItemClicked(ForDataUser: ForDataUser) { //..
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_for_follower, container, false)
    }
}