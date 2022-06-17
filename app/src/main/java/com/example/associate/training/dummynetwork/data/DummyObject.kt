package com.example.associate.training.dummynetwork.data

import com.google.gson.annotations.SerializedName

class DummyObject {

}

data class DummyData (
    @SerializedName("id"        ) var id        : String? = null,
    @SerializedName("title"     ) var title     : String? = null,
    @SerializedName("firstName" ) var firstName : String? = null,
    @SerializedName("lastName"  ) var lastName  : String? = null,
    @SerializedName("picture"   ) var picture   : String? = null
)

data class DummyUsers (
    @SerializedName("data"  ) var data  : ArrayList<DummyData> = arrayListOf(),
    @SerializedName("total" ) var total : Int?            = null,
    @SerializedName("page"  ) var page  : Int?            = null,
    @SerializedName("limit" ) var limit : Int?            = null
)