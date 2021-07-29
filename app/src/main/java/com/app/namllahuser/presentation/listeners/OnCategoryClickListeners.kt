package com.app.namllahuser.presentation.listeners

import com.app.namllahuser.data.model.ServiceDto

interface OnCategoryClickListeners {
    fun onCategoryClick(serviceDto: ServiceDto)
}