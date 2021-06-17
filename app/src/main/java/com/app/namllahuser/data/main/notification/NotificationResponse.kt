package com.app.namllahuser.data.main.notification

import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.model.NotificationDto

data class NotificationResponse(
    val data: MutableList<NotificationDto>
):BaseResponse() {
    override fun toString(): String {
        return "NotificationResponse(data=$data)"
    }
}