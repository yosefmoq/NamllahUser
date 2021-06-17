package com.app.namllahuser.data.model.order

data class Area(
    var id:Int,
    var title:String,
    var lat:Double,
    var lng:Double,
    var diameter:Int,
) {
    override fun toString(): String {
        return "Area(id=$id, title='$title', lat=$lat, lng=$lng, diameter=$diameter)"
    }
}