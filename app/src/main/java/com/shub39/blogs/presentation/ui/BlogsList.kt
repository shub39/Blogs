package com.shub39.blogs.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shub39.blogs.data.BlogPost

@Composable
fun BlogsList(
    blogs: List<BlogPost>,
    onRefresh: () -> Unit,
    onClick: (String) -> Unit
) {

    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        items(blogs) {
            Card(
                onClick = { onClick(it.link) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = it.title.rendered,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        item {
            LaunchedEffect(Unit) {
                onRefresh()
            }
        }
    }

}