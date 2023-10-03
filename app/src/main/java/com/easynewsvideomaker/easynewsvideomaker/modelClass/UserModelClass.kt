package com.easynewsvideomaker.easynewsvideomaker.modelClass

class UserModelClass {

    var userName: String? = null
    var channelName: String? = null
    var mobilNumber: String? = null
    var email: String? = null
    var password: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var packageType: String? = null
    var uid: String? = null

    constructor() {}

    constructor(
        userName: String,
        channelName: String,
        mobilNumber: String,
        email: String,
        password: String,
        startDate: String,
        endDate: String,
        packageType: String,
        uid: String
    ) {
        this.userName = userName
        this.channelName = channelName
        this.mobilNumber = mobilNumber
        this.email = email
        this.password = password
        this.startDate = startDate
        this.endDate = endDate
        this.packageType = packageType
        this.uid = uid
    }

}