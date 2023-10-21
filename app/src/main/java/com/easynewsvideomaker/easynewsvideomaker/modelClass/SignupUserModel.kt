package com.easynewsvideomaker.easynewsvideomaker.modelClass

class SignupUserModel {

    var userName: String? = null
    var mobilNumber: String? = null
    var email: String? = null
    var password: String? = null
    var login_device_name: String? = null
    var uid: String? = null

    constructor() {}

    constructor(
        userName: String,
        mobilNumber: String,
        email: String,
        password: String,
        login_device_name: String,
        uid: String
    ) {
        this.userName = userName
        this.mobilNumber = mobilNumber
        this.email = email
        this.password = password
        this.login_device_name = login_device_name
        this.uid = uid
    }

}