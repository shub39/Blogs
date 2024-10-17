package com.shub39.blogs.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BlogDao {
    @Query("SELECT * FROM blog_posts")
    suspend fun getBlogPosts(): List<BlogPost>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlogPost(blogPost: BlogPost)
}