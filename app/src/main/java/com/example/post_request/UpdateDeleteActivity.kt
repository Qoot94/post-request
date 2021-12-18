package com.example.post_request

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDeleteActivity : AppCompatActivity() {
    private lateinit var apiInterface: ApiInterface
    lateinit var name: EditText
    lateinit var location: EditText
    lateinit var pk: EditText
    var updatedName = ""
    var updatedLocation = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)
        //set up UI
        val editButton = findViewById<ImageButton>(R.id.ibUpdate)
        val delButton = findViewById<ImageButton>(R.id.ibDel)
        val helpButton = findViewById<Button>(R.id.btHelp)
        val helpBox = findViewById<LinearLayout>(R.id.linearLayout1)


        pk = findViewById<EditText>(R.id.etPK)
        name = findViewById<EditText>(R.id.etUpdatedName)
        location = findViewById<EditText>(R.id.etUpdatedLocation)

        apiInterface = APIClient().getClient()?.create(ApiInterface::class.java)!!

        //button interactions
        editButton.setOnClickListener {
            if ((pk.text.isNotEmpty() && name.text.isNotEmpty()) || (pk.text.isNotEmpty() && location.text.isNotEmpty())) {
                updateData()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(
                    applicationContext,
                    "please enter a valid pk, name or location value",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        delButton.setOnClickListener {
            if (pk.text.isNotEmpty()) {
                deleteData()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(
                    applicationContext,
                    "please enter a valid pk value",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        helpButton.setOnClickListener {
            helpBox.visibility = View.VISIBLE

        }

    }


    private fun updateData() {
        val userID = pk.text.toString().toInt()
        //update/edit request
        apiInterface.updateData(
            userID, RecipeItem(
                location = location.text.toString(),
                name = name.text.toString(),
                pk = userID
            )
        ).enqueue(object : Callback<RecipeItem> {
            override fun onResponse(call: Call<RecipeItem>, response: Response<RecipeItem>) {
                Toast.makeText(
                    applicationContext,
                    "User updated to api $userID",
                    Toast.LENGTH_SHORT
                ).show()
                location.text.clear()
                name.text.clear()
            }

            override fun onFailure(call: Call<RecipeItem>, t: Throwable) {
                Log.d("error-update", "$t")
            }
        })
    }

    private fun deleteData() {
        val userID = pk.text.toString().toInt()
        //delete request
        apiInterface?.deleteData(userID).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(
                    applicationContext, "User $userID deleted to api",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("error-delete", "$t")
            }

        })
    }
}