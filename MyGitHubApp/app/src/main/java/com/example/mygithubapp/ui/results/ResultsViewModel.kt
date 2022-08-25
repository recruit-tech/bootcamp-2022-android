package com.example.mygithubapp.ui.results

import androidx.lifecycle.*
import com.example.mygithubapp.data.UserInfomation
import com.example.mygithubapp.repository.retrofit.GithubAccessRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class ResultsViewModel : ViewModel() {
    var query: String = ""
    private val repository = GithubAccessRepository()
    var userList: LiveData<List<UserInfomation>> = liveData(Dispatchers.IO) {
        emit(repository.getUserList(query.uppercase()) ?: listOf())
    }
}