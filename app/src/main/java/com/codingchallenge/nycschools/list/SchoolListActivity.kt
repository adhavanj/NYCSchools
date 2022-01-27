package com.codingchallenge.nycschools.list

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.codingchallenge.nycschools.details.SchoolDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolListScreen(onSchoolItemClicked = { SchoolDetailActivity.launch(context = this, schoolItemUiState = it)})
        }
    }
}