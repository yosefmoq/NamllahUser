package com.app.namllahuser.data.model

data class ServiceProviderDto(
    var id:Int? = 0,
    var name:String? ="",
    var image:UserImagesData,
    var rate:Double?=0.0,
    var category:String?="Plumbing"
) {
    override fun toString(): String {
        return "ServiceProviderDto(id=$id, name=$name, image=$image)"
    }
}