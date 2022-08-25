package com.example.mygithubapp.repository.retrofit

import com.example.mygithubapp.data.UserInfomation
import java.io.IOException

class GithubAccessRepository(
    private val service: GithubAccessService = GithubAccessService.create()
) {
    @Suppress("BlockingMethodInNonBlockingContext")
    fun getUserList(query: String): List<UserInfomation>? {
        try {
            val response = service.getUserList(createQueryString(query)).execute()
            return if (response.isSuccessful) {
                response.body()?.items
            } else {
                null
            }
        } catch(e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun createQueryString(query: String): String = query
}