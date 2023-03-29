package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var moviesAdapter : MoviesAdapter
    private val movieList = ArrayList<Movies>()
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.RecyclerView.layoutManager = layoutManager
        moviesAdapter = MoviesAdapter(movieList)
        binding.RecyclerView.adapter = moviesAdapter

        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                // Perform the network request on the IO dispatcher
                val client = OkHttpClient()

                val request = Request.Builder()
                    .url("https://imdb-top-100-movies.p.rapidapi.com/")
                    .get()
                    .addHeader(
                        "X-RapidAPI-Key",
                        "2d312d5232mshbd7c7e423272dc0p12736ajsnfdc1318069dd"
                    )
                    .addHeader("X-RapidAPI-Host", "imdb-top-100-movies.p.rapidapi.com")
                    .build()

                val response = client.newCall(request).execute()

                // Parse the response and update UI on the main thread
                val responseBody = response.body()?.string()
                if (responseBody != null) {
                    val moviesJsonArray = JSONArray(responseBody)
                    for (i in 0 until moviesJsonArray.length()) {
                        val movieJsonObject = moviesJsonArray.getJSONObject(i)
                        val movie = Movies(
                            movieJsonObject.getString("title"),
                            movieJsonObject.getString("director"),
                            movieJsonObject.getString("rating"),
                            movieJsonObject.getString("image"),


                            )
                        movieList.add(movie)
                        Log.d("MainActivity", "Added movie: $movie")
                    }
                }
            }
            // Update the UI on the main thread
            moviesAdapter.notifyDataSetChanged()
            println("oguzhan")

        }
    }
}
