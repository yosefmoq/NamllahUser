package com.app.namllahuser.data.main.slider

import com.app.namllahuser.data.base.BaseResponse
import com.google.android.material.slider.Slider

data class SliderResponse(
    val data:MutableList<SliderData>
):BaseResponse() {
}