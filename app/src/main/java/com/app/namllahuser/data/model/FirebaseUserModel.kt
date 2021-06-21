package com.app.namllahuser.data.model


class FirebaseUserModel() /*constructor()*/ {

    var complete_at: Long? = 0
    var duration: Long? = 0
    var is_working: Int = 0
    var order_id: Long? = 0
    var type: String? = " "
    var isWorkingA:Boolean ? =  false

    /*constructor(
         complete_at: Long? = 0,
         duration: Long? = 0,
         is_working: Boolean? = false,
         order_id: Long,
         type: String? = " "

    ):this(){
        this.complete_at = complete_at
        this.duration = duration
        this.is_working = is_working
        this.order_id   = order_id
        this.type = type
    }*/

    fun isWorking() = is_working ==1
    override fun toString(): String {
        return "FirebaseUserModel(complete_at=$complete_at, duration=$duration, is_working=$is_working, order_id=$order_id, type=$type , isWorkingA =$isWorkingA)"
    }
}