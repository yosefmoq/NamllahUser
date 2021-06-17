package com.app.namllahuser.data.model.order

data class Status(
    var id:Int,
    var title:String
) {
    override fun toString(): String {
        return "Status(id=$id, title='$title')"
    }
}