package com.codingchallenge.nycschools.list

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codingchallenge.nycschools.R

typealias OnSchoolItemClicked = (SchoolItemUiState) -> Unit

@Composable
fun SchoolListScreen(
    onSchoolItemClicked: OnSchoolItemClicked,
    schoolListViewModel: SchoolListViewModel = viewModel()
) {
    val viewState by schoolListViewModel.uiState.collectAsState()
    SchoolListItemsScrollableComponent(onSchoolItemClicked, viewState)
}

@Composable
fun SchoolListItemsScrollableComponent(
    onSchoolItemClicked: OnSchoolItemClicked,
    uiState: SchoolListUiState
) {

    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
        when (uiState) {
            is SchoolListUiState.Success -> {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(uiState.schoolItems) { school ->
                        SchoolListItem(onSchoolItemClicked = onSchoolItemClicked, school = school)
                    }
                }
            }
            is SchoolListUiState.Error -> {
                Snackbar {
                    Text(text = uiState.exception.message.toString())
                }
            }
            SchoolListUiState.Empty -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.no_school)
                )

            }
            SchoolListUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            }
        }
    }
}

@Preview
@Composable
fun SchoolListItem(
    onSchoolItemClicked: OnSchoolItemClicked? = null,
    @PreviewParameter(FakeSchoolItemUiStateProvider::class) school: SchoolItemUiState
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable { onSchoolItemClicked?.invoke(school) },
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp,
        backgroundColor = Color.LightGray
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text(
                school.name, style = TextStyle(
                    color = Color.DarkGray,
                    fontSize = 18.sp
                )
            )

            if (school.primaryAddressLine1.isNotBlank() && school.city.isNotBlank() && school.zip.isNotBlank()) {
                Text(
                    "${school.primaryAddressLine1}, ${school.city}, ${school.zip}",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (school.phoneNumber.isNotBlank()) {
                    val call = stringResource(R.string.call)
                    IconButton(onClick = { showToast(context, "$call ${school.phoneNumber}") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_local_phone_24),
                            contentDescription = null
                        )
                    }
                }

                if (school.email.isNotBlank()) {
                    val email = stringResource(R.string.email)
                    IconButton(onClick = { showToast(context, "$email ${school.email}") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_alternate_email_24),
                            contentDescription = null,
                        )
                    }

                }

                if (school.website.isNotBlank()) {
                    val website = stringResource(R.string.website)
                    IconButton(onClick = { showToast(context, "$website ${school.website}") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_computer_24),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

private fun showToast(context: Context, toastString: String) {
    Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show()
}

class FakeSchoolItemUiStateProvider : PreviewParameterProvider<SchoolItemUiState> {
    private val fakeSchoolItems = listOf(
        SchoolItemUiState(
            "dbn",
            "Avengers Academy",
            "1234567890",
            "email",
            "web",
            "Address one",
            "00000",
            "Wakanda"
        )
    )
    override val values = fakeSchoolItems.asSequence()
    override val count: Int = values.count()

}

