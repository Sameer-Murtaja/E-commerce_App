package com.unit_one.e_commerceapp.util

import android.util.Log
import com.unit_one.e_commerceapp.data.model.ProductResponse
import com.unit_one.e_commerceapp.data.model.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response


fun <T> wrapWithFlow(function: suspend () -> Response<T>): Flow<State<T?>> {
    return flow {
        emit(State.Loading)
        try {
            val result = function()
            if (result.isSuccessful) {
                emit(State.Success(result.body()))
            } else {
                emit(State.Error(result.message()))
                Log.e("TAG", "Api Error result: ${result.message()}")
            }
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
            Log.e("TAG", "Api Error e: ${e.message}")
        }
    }
}


fun <T> wrapWithFlow(
    function: suspend (id: Int) -> Response<T>,
    value: Int? = -1
): Flow<State<T?>> {
    return flow {
        emit(State.Loading)
        try {
            val result = function(value ?: -1)
            if (result.isSuccessful) {
                emit(State.Success(result.body()))
            } else {
                emit(State.Error(result.message()))
                Log.e("TAG", "Api Error result: ${result.message()}")
            }
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
            Log.e("TAG", "Api Error e: ${e.message}")
        }
    }
}


fun <T> wrapWithFlow(
    function: suspend (id: Int, body: ProductResponse) -> Response<T>,
    value: Int? = -1,
    body: ProductResponse,
): Flow<State<T?>> {
    return flow {
        emit(State.Loading)
        try {
            val result = function(value ?: -1, body)
            if (result.isSuccessful) {
                emit(State.Success(result.body()))
            } else {
                emit(State.Error(result.message()))
                Log.e("TAG", "Api Error result: ${result.message()}")
            }
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
            Log.e("TAG", "Api Error e: ${e.message}")
        }
    }
}
