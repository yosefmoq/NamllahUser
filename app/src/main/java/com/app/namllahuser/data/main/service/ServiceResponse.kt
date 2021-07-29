package com.app.namllahuser.data.main.service

import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.model.ServiceDto
import com.app.namllahuser.data.model.order.ServiceData

data class ServiceResponse(
    var data:MutableList<ServiceDto>
):BaseResponse() {
}