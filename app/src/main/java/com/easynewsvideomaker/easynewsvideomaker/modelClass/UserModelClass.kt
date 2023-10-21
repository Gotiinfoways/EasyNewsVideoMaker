package com.easynewsvideomaker.easynewsvideomaker.modelClass

class UserModelClass {

    var userName: String? = null
    var mobilNumber: String? = null
    var email: String? = null
    var password: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var packageType: String? = null
    var login_device_name: String? = null
    var uid: String? = null

    constructor() {}

    constructor(
        userName: String,
        mobilNumber: String,
        email: String,
        password: String,
        startDate: String,
        endDate: String,
        packageType: String,
        login_device_name: String,
        uid: String
    ) {
        this.userName = userName
        this.mobilNumber = mobilNumber
        this.email = email
        this.password = password
        this.startDate = startDate
        this.endDate = endDate
        this.packageType = packageType
        this.login_device_name = login_device_name
        this.uid = uid
    }

}