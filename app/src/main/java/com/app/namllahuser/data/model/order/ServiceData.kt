package com.app.namllahuser.data.model.order

import com.app.namllahuser.data.model.ServiceDto

data class ServiceData(
    var data:MutableList<ServiceDto>
) {
    override fun toString(): String {
        return "ServiceResponse(data=$data)"
    }

}