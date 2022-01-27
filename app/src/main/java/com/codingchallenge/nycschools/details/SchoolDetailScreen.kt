package com.codingchallenge.nycschools.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codingchallenge.nycschools.R

@Composable
fun SchoolDetailScreen(
    schoolDetailViewModel: SchoolDetailViewModel = viewModel()
) {
    val viewState by schoolDetailViewModel.uiState.collectAsState()
    SchoolDetailScrollableComponent(viewState)

}

@Composable
fun SchoolDetailScrollableComponent(viewState: SchoolDetailScreenUiState) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        when (viewState) {
            is SchoolDetailScreenUiState.Success -> {
                SchoolDetails(viewState.schoolDetails)
            }
            is SchoolDetailScreenUiState.Error -> {
                Snackbar {
                    Text(text = viewState.exception.message.toString())
                }
            }
            SchoolDetailScreenUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            }
        }
    }
}


@Composable
fun SchoolDetails(schoolDetails: SchoolDetailUiState) {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(4.dp)) {
        val modifier = Modifier.padding(top = 5.dp)
        Text(
            text = schoolDetails.name,
            style = TextStyle(color = Color.Black, fontSize = 20.sp),
            modifier = modifier
        )
        Text(
            text = schoolDetails.overviewParagraph,
            style = TextStyle(color = Color.Gray, fontSize = 14.sp),
            modifier = modifier
        )

        if (schoolDetails.numOfSatTestTakers.isBlank() && schoolDetails.satCriticalReadingAvgScore.isBlank() && schoolDetails.satWritingAvgScore.isBlank() && schoolDetails.satMathAvgScore.isBlank()) {
            Text(
                text = stringResource(id = R.string.no_sat),
                style = TextStyle(color = Color.Black, fontSize = 16.sp),
                modifier = modifier
            )
        } else {
            Text(
                text = "${stringResource(id = R.string.sat_takers)} ${schoolDetails.numOfSatTestTakers}",
                style = TextStyle(color = Color.Black, fontSize = 16.sp),
                modifier = modifier
            )
            Text(
                text = "${stringResource(id = R.string.read_avg)} ${schoolDetails.satCriticalReadingAvgScore}",
                style = TextStyle(color = Color.Black, fontSize = 16.sp),
                modifier = modifier
            )
            Text(
                text = "${stringResource(id = R.string.write_avg)} ${schoolDetails.satWritingAvgScore}",
                style = TextStyle(color = Color.Black, fontSize = 16.sp),
                modifier = modifier
            )
            Text(
                text = "${stringResource(id = R.string.math_avg)} ${schoolDetails.satMathAvgScore}",
                style = TextStyle(color = Color.Black, fontSize = 16.sp),
                modifier = modifier
            )
        }
    }
}