package com.example.karmagramsource

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class Login_Page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        val enteredUSname = findViewById<EditText>(R.id.username)
        val enteredPWD = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signupButton = findViewById<Button>(R.id.signup)

        signupButton.setOnClickListener {
            val intent = Intent(this@Login_Page, SignUp_Page::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val username = enteredUSname.text.toString()
            val password = enteredPWD.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser(username, password)
            } else {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        lifecycleScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    ApiClient.apiService.getUser(username)
                }

                if (user.pwd == password) {
                    val intent = Intent(this@Login_Page, Home_Page::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@Login_Page, "Invalid password", Toast.LENGTH_LONG).show()
                }

            } catch (e: HttpException) {
                // Handle HTTP error
                Toast.makeText(this@Login_Page, "User not found", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                // Handle other errors
                Toast.makeText(this@Login_Page, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}