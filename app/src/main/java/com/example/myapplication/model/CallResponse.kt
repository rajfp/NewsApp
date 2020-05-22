package com.example.myapplication.model


    data class CallResponse<out T>(val status: Status, val data: T?, val message: String?, val code: String?) {
        companion object {

            private const val defaultCode : String = "-1";

            fun <T> success(data: T?): CallResponse<T>
            {
                return CallResponse(Status.SUCCESS, data, null,defaultCode )
            }

            fun <T> error(t: Throwable): CallResponse<T>
            {

                return CallResponse(Status.ERROR, null, t.message, defaultCode)
            }


        }

        enum class Status
        {
            SUCCESS ,ERROR
        }
    }

