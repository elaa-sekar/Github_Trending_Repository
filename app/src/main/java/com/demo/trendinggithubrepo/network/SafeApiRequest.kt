package com.demo.trendinggithubrepo.network

import com.demo.trendinggithubrepo.utils.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        val response = call.invoke()

        if (response.isSuccessful) {
            //Timber.d("SafeApiRequest success")
            return response.body()!!
        } else {
            //Timber.d("SafeApiRequest error")
            val error = response.errorBody()?.string()

            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("error"))
                } catch (e: JSONException) {
                    message.append("Error body not found.")
                }
                message.append("\n")
            }
            val errorMessage = "Error code : ${response.code()}\n$message"
            when (response.code()) {
                400 -> throw BadRequestException(errorMessage)
                401 -> throw AuthenticationFailureException(errorMessage)
                403 -> throw ForbiddenResourceException(errorMessage)
                404 -> throw UrlNotFoundException(errorMessage)
                405 -> throw NotSupportedOrNotAllowedException(errorMessage)
                429 -> throw TooManyRequestsException(errorMessage)
                500 -> throw ApiServerException(errorMessage)
                503 -> throw ServiceUnavailableException(errorMessage)
                else -> throw UnknownApiException(errorMessage)
            }
        }
    }
}