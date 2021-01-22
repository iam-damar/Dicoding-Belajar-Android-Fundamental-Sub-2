package com.damars.damardjati_submissiondicodingke2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object { private val TAG = MainActivity::class.java.simpleName }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this,"Hai, Selamat Datang", Toast.LENGTH_SHORT).show()
        supportActionBar?.title = "Searching Github Users"
        userSearch()
        gettingUser()
        configRecyclerView()
        adapter = MainAdapterUser(listData)
    }
    private fun configRecyclerView() {
        mainReView.layoutManager = LinearLayoutManager(mainReView.context)
        mainReView.setHasFixedSize(true)
        mainReView.addItemDecoration(
                DividerItemDecoration(
                        mainReView.context,
                        DividerItemDecoration.VERTICAL)
        )
    }
    private fun gettingUser() {
        mainProgressBar.visibility = View.VISIBLE
        val aClient = AsyncHttpClient()
        aClient.addHeader("User-Agent", "request")
        aClient.addHeader("Authorization", "token 3524f98d046b487a50bc4b16d40193a616e811ac")
        aClient.get("https://api.github.com/users", object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                mainProgressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try{
                    val jsonArray = JSONArray(result)
                    for(i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDetailOfUsers(username)
                    }
                }catch (e: Exception){
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@MainActivity, messageError, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun userSearch() {
        mainSearchUser.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                if(query.isEmpty()){
                    Toast.makeText(this@MainActivity, "User not Found", Toast.LENGTH_SHORT).show()
                    return true
                }else {
                    listData.clear()
                    gettingSearchUser(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean { return false }
        })
    }
    private fun gettingSearchUser(id: String) {
        mainProgressBar.visibility = View.INVISIBLE
        val aClients = AsyncHttpClient()
        aClients.addHeader("User-Agent","request")
        aClients.addHeader("Authorization","token 3524f98d046b487a50bc4b16d40193a616e811ac")
        aClients.get("https://api.github.com/search/users?q=$id", object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                mainProgressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try{
                    val jsonArray = JSONObject(result)
                    val item = jsonArray.getJSONArray("items")
                    for(i in 0 until item.length()){
                        val jsonObject = item.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDetailOfUsers(username)
                    }
                }catch (e: Exception){
                    Toast.makeText(this@MainActivity, e.message,Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@MainActivity, messageError, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun getDetailOfUsers(id: String) {
        mainProgressBar.visibility = View.VISIBLE
        val aClient = AsyncHttpClient()
        aClient.addHeader("User-Agent", "request")
        aClient.addHeader("Authorization", "token 3524f98d046b487a50bc4b16d40193a616e811ac")
        aClient.get("https://api.github.com/users/$id", object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                mainProgressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try{
                    val jsonObject = JSONObject(result)
                    val username: String = jsonObject.getString("login").toString()
                    val name: String = jsonObject.getString("name").toString()
                    val company: String = jsonObject.getString("company").toString()
                    val avatar: String = jsonObject.getString("avatar_url").toString()
                    val location: String = jsonObject.getString("location").toString()
                    val repository: String? = jsonObject.getString("public_repos")
                    val following: String? = jsonObject.getString("following")
                    val follower: String? = jsonObject.getString("followers")
                    listData.add(
                            ForDataUser(
                                username,
                                name,
                                company,
                                avatar,
                                location,
                                repository,
                                follower,
                                following)
                    )
                    recyclerShowList()
                }catch (e: Exception){
                    Toast.makeText(this@MainActivity, e.message,Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                mainProgressBar.visibility = View.INVISIBLE
                val messageError = when (statusCode) {
                    401 -> "$statusCode : Bad Requested"
                    403 -> "$statusCode : That's Forbidden"
                    404 -> "$statusCode : Sorry Not Found"
                    else -> "$statusCode : $(error.message)"
                }
                Toast.makeText(this@MainActivity, messageError, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.others_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.setChange_Action) {
            val moveIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(moveIntent)
        }else{
            moveMode(item.itemId)
        }
        return super.onOptionsItemSelected(item)
    }
    private fun moveMode(clicked: Int){
        when(clicked){
            R.id.item_ribbon -> {
                val moveToAbout  = Intent(this@MainActivity, AboutCreator::class.java)
                startActivity(moveToAbout)
            }
        }
    }
    private fun recyclerShowList() {
        mainReView.layoutManager = LinearLayoutManager(this)
        val dataListAdapter = MainAdapterUser(filterListUser)
        mainReView.adapter = adapter

        dataListAdapter.setOnItemClickCallback(object : MainAdapterUser.OnItemClickCallback{
            override fun onItemClicked(dataUsers: ForDataUser) {
                selectedUserShow(dataUsers)
            }
        })
    }

    private fun selectedUserShow(dataUser: ForDataUser) {
        ForDataUser(
                dataUser.thisUsername,
                dataUser.thisName,
                dataUser.thisCompany,
                dataUser.thisAvatar,
                dataUser.thisLocation,
                dataUser.thisRepository,
                dataUser.thisFollower,
                dataUser.thisFollowing
        )
        val intent = Intent(this@MainActivity, ForDetailUser::class.java)
        intent.putExtra(ForDetailUser.EXTRA_DATA, dataUser)

        this@MainActivity.startActivity(intent)
        Toast.makeText(this,"${dataUser.thisUsername}", Toast.LENGTH_SHORT).show()
    }
    private lateinit var adapter: MainAdapterUser
    private var listData: ArrayList<ForDataUser> = ArrayList()
}