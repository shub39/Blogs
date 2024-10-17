package com.shub39.blogs.vm

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shub39.blogs.data.BlogDatabase
import com.shub39.blogs.data.BlogPost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class BlogVM(
    application: Application,
) : ViewModel() {

    private val api: BlogApi
    private val blogDatabase = BlogDatabase.getDatabase(application)
    private val blogDao = blogDatabase.blogDao()
    private val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var currentPage = 1
    private var _blogs = MutableStateFlow<List<BlogPost>>(emptyList())

    val blogs: StateFlow<List<BlogPost>> get() = _blogs

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://blog.vrid.in/wp-json/wp/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(BlogApi::class.java)

        viewModelScope.launch {
            _blogs.value = blogDao.getBlogPosts()

            if (_blogs.value.isEmpty()) {
                loadMoreBlogs()
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        return connectivityManager.activeNetwork != null
    }

    fun loadMoreBlogs() {
        viewModelScope.launch {
            if (!isNetworkAvailable()) return@launch

            val newBlogs = api.getPosts(perPage = 10, page = currentPage)
            _blogs.value = _blogs.value + newBlogs
            currentPage++

            for (blog in newBlogs) {
                blogDao.insertBlogPost(blog)
            }
        }
    }

}

interface BlogApi {
    @GET("posts")
    suspend fun getPosts(@Query("per_page") perPage: Int, @Query("page") page: Int): List<BlogPost>
}