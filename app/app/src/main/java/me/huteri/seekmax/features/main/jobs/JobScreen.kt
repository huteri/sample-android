package me.huteri.seekmax.features.main.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.huteri.seekmax.model.Job
import me.huteri.seekmax.ui.theme.SecondaryColor

@Preview
@Composable
fun JobsScreen() {

    val viewModel = hiltViewModel<JobsViewModel>()
    val state by viewModel.state.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().background(SecondaryColor),
    ) {

        if (state.isLoading) {
            CircularProgressIndicator(
            )
        } else {
            if (state.jobs?.isEmpty() == false) {
                JobList(state.jobs!!)
            } else {
                //TODO render empty view
            }

        }
    }
}


@Composable
fun JobList(jobs: List<Job>) {
    LazyColumn {
        items(jobs.size) { position ->
            JobListItem(jobs[position])
        }
    }
}

@Composable
fun JobListItem(job: Job) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = job.positionTitle ?: "",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = job.company?.name ?: "", style = MaterialTheme.typography.caption)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = job.description ?: "", style = MaterialTheme.typography.body2)
        }
    }
}

