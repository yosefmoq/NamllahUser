package com.app.namllahuser.data.main.slider

import com.app.namllahuser.data.model.UserImagesData

data class SliderData(
    var id:Int,
    var image:UserImagesData,
    var title:String,
    var description:String,
    var type:String
)