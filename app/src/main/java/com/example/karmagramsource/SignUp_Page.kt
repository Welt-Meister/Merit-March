package com.example.karmagramsource

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp_Page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page)

        val newname = findViewById<EditText>(R.id.name)
        val newusername = findViewById<EditText>(R.id.username)
        val pwd = findViewById<EditText>(R.id.password)
        val repwd = findViewById<EditText>(R.id.Repassword)
        val signupButton = findViewById<Button>(R.id.signUpButton)

        signupButton.setOnClickListener {
            val name = newname.text.toString()
            val username = newusername.text.toString()
            val password = pwd.text.toString()
            val repassword = repwd.text.toString()

            if (password != repassword) {
                Toast.makeText(this, "The passwords do not match", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val request = SignupRequest(name, username, password)
            ApiClient.apiService.signup(request).enqueue(object : Callback<SignupResponse> {
                override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@SignUp_Page, "Signup successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignUp_Page, Login_Page::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignUp_Page, "Signup failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Toast.makeText(this@SignUp_Page, "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
                    val error = t.message
                    if (error != null) {
                        Log.e("TAG", error)
                    }
                }
            })
        }
    }
}