package com.codingchallenge.nycschools.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.codingchallenge.nycschools.list.SchoolItemUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolDetailActivity : AppCompatActivity() {

    private val schoolDetailViewModel: SchoolDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbn = intent.extras?.getString(KEY_DETAILS_DBN) ?: ""
        schoolDetailViewModel.getSchoolDetails(dbn)
        setContent {
            SchoolDetailScreen()
        }
    }

    companion object {
        private const val KEY_DETAILS_DBN = "dbn"
        fun launch(context: Context, schoolItemUiState: SchoolItemUiState) {
            val intent = Intent(context, SchoolDetailActivity::class.java)
            intent.putExtra(KEY_DETAILS_DBN, schoolItemUiState.dbn)
            context.startActivity(intent)
        }
    }
}
