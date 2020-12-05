package com.demo.trendinggithubrepo.repositories

import com.demo.trendinggithubrepo.network.ApiService
import com.demo.trendinggithubrepo.network.SafeApiRequest

class HomeRepository(val apiService: ApiService) : SafeApiRequest() {
}