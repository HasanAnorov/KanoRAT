package com.ierusalem.androrat.core.utils

sealed class Resource <T> (
    val data :T? =null,
    val errorMessage:String? =null
) {
    class Loading <T> (data: T? = null) : Resource<T>(data)
    class Success<T>(data:T): Resource<T>(data)
    class Failure<T>(errorMessage: String) : Resource<T>(null,errorMessage)

    override fun toString(): String {
        return when (this) {
            is Success<*> ->"Success[data=$data]"
            is Loading<T> ->"Loading"
            is Failure ->"Error[exception=$errorMessage]"
        }
    }
}