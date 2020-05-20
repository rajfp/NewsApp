package com.example.myapplication.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class NewsResponseModel(
    val status: String,
    val totalResults: Int,
    val articles: List<Articles>
)

data class Source(val id: String?, val name: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Source> {
        override fun createFromParcel(parcel: Parcel): Source {
            return Source(parcel)
        }

        override fun newArray(size: Int): Array<Source?> {
            return arrayOfNulls(size)
        }
    }
}

data class Articles(
    val source: Source, val author: String?, val title: String?, val description: String?,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("publishedAt") val publishedAt: String?, val content: String?,
    @SerializedName("url") val url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Source::class.java.classLoader)!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(source, flags)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
        parcel.writeString(content)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Articles> {
        override fun createFromParcel(parcel: Parcel): Articles {
            return Articles(parcel)
        }

        override fun newArray(size: Int): Array<Articles?> {
            return arrayOfNulls(size)
        }
    }
}
