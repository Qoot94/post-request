package com.example.post_request

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var myRV: RecyclerView
    lateinit var rvAdapter: RVAdapter
    lateinit var apiInterface: ApiInterface
    lateinit var name: EditText
    lateinit var location: EditText
    var userID: Int = 0
    var usersList = ArrayList<RecipeItem>()

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set up recyclerView
        myRV = findViewById(R.id.rvMain)
        rvAdapter = RVAdapter(usersList)
        myRV.adapter = rvAdapter
        myRV.layoutManager = LinearLayoutManager(this)

        //set up UI
        name = findViewById<EditText>(R.id.etName)
        location = findViewById<EditText>(R.id.etLocation)
        val postButton = findViewById<ImageButton>(R.id.ibPost)
        val secondMainButton = findViewById<FloatingActionButton>(R.id.fabGoTo2)

        apiInterface = APIClient().getClient()?.create(ApiInterface::class.java)!!
        gettingData()

        //post request
        postButton.setOnClickListener {
            val call: Call<RecipeItem> = apiInterface.addData(
                RecipeItem(
                    location = location.text.toString(),
                    name = name.text.toString(),
                    pk = 0
                )
            )
            call.enqueue(object : Callback<RecipeItem> {
                override fun onResponse(call: Call<RecipeItem>, response: Response<RecipeItem>) {
                    try {
                        Toast.makeText(applicationContext, "User added to api", LENGTH_SHORT).show()
                        location.text.clear()
                        name.text.clear()
                        gettingData()
                        rvAdapter.notifyDataSetChanged()
                        myRV.scrollToPosition(usersList.size - 1)

                    } catch (e: Exception) {
                        Log.d("error-post1", "$e")
                    }
                }

                override fun onFailure(call: Call<RecipeItem>, t: Throwable) {
                    Log.d("error-post2", "$t")
                }
            })
        }

        //fab action
        secondMainButton.setOnClickListener {
            startActivity(Intent(this, UpdateDeleteActivity::class.java))

        }
    }

    //API functions:CRUD
    private fun gettingData() {
        //get request
        apiInterface.getData().enqueue(object : Callback<recipe> {
            override fun onResponse(call: Call<recipe>, response: Response<recipe>) {
                //retrieve all data needed
                val response = response.body()!!
                println(response)
                for (i in 0 until response!!.size) {
                    val nameAPI = response[i].name
                    val locationAPi = response[i].location
                    userID = response[i].pk
                    val recipeObject = RecipeItem(locationAPi, nameAPI, userID)
                    usersList.add(recipeObject)
                    Log.d("GET-success", "get request success")
                    rvAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<recipe>, t: Throwable) {
                Log.d("GET-error", "$t")
            }

        })
    }

    private fun updateData() {
        //update/edit request
        apiInterface.updateData(
            userID, RecipeItem(
                location = location.text.toString(),
                name = name.text.toString(),
                pk = 0
            )
        ).enqueue(object : Callback<RecipeItem> {
            override fun onResponse(call: Call<RecipeItem>, response: Response<RecipeItem>) {
                Toast.makeText(applicationContext, "User updated to api", LENGTH_SHORT).show()
                location.text.clear()
                name.text.clear()
            }

            override fun onFailure(call: Call<RecipeItem>, t: Throwable) {
                Log.d("error-update", "$t")
            }
        })
    }

    private fun deleteData() {
        //delete request
        apiInterface?.deleteData(userID).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(applicationContext, "User $userID deleted to api", LENGTH_SHORT)
                    .show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("error-delete", "$t")
            }

        })
    }

}

