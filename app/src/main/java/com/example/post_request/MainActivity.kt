package com.example.post_request

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = findViewById<EditText>(R.id.etName)
        val location = findViewById<EditText>(R.id.etLocation)
        val postButton = findViewById<ImageButton>(R.id.ibPost)

        val apiInterface = APIClient().getClient()?.create(ApiInterface::class.java)



        postButton.setOnClickListener {
            val call: Call<recipeItem>? = apiInterface?.addData(
                recipeItem(
                    location = location.text.toString(),
                    name = name.text.toString(),
                    pk = 0
                )
            )
            call?.enqueue(object : Callback<recipeItem> {
                override fun onResponse(call: Call<recipeItem>, response: Response<recipeItem>) {
                    try {
                        Toast.makeText(applicationContext, "User added to api", LENGTH_SHORT).show()
                        location.text.clear()
                        name.text.clear()
                    } catch (e: Exception) {
                        Log.d("throw_error1", "$e")
                    }
                }

                override fun onFailure(call: Call<recipeItem>, t: Throwable) {
                    Log.d("throw_error2", "$t")
                }

            })
        }

    }

}

