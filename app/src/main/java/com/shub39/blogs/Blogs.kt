package com.shub39.blogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.shub39.blogs.presentation.ui.Blog
import com.shub39.blogs.presentation.ui.BlogsList
import com.shub39.blogs.vm.BlogVM
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.*

@Composable
fun Blogs(
    vm: BlogVM = koinViewModel()
) {
    val navController = rememberNavController()
    val blogs by vm.blogs.collectAsState()

    NavHost(
        navController = navController,
        startDestination = BlogList
    ) {
        composable<BlogList> {
            BlogsList(
                blogs = blogs,
                onRefresh = {
                    vm.loadMoreBlogs()
                },
                onClick = {
                    navController.navigate(BlogScreen(it))
                }
            )
        }

        composable<BlogScreen> {
            val args = it.toRoute<BlogScreen>()

            Blog(args.link)
        }
    }
}

@Serializable
object BlogList

@Serializable
data class BlogScreen(
    val link: String
)