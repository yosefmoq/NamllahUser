package com.app.namllahuser.data.model.order

data class Payment(
    var id:Int,
    var title:String,
    var description:String,
    var code:String
) {
    override fun toString(): String {
        return "Payment(id=$id, title='$title', description='$description', code='$code')"
    }
}