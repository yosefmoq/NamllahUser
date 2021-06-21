package com.app.namllahuser.data.main.serviceProviders

import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.model.ServiceProviderData
import com.app.namllahuser.data.model.ServiceProviderDto

data class ServiceProviderResponse (
    var data:MutableList<ServiceProviderDto>
):BaseResponse() {
}