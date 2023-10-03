package com.easynewsvideomaker.easynewsvideomaker.modelClass

class SignupUserModel {

    var userName: String? = null
    var channelName: String? = null
    var mobilNumber: String? = null
    var email: String? = null
    var password: String? = null
    var uid: String? = null

    constructor() {}

    constructor(
        userName: String,
        channelName: String,
        mobilNumber: String,
        email: String,
        password: String,
        uid: String
    ) {
        this.userName = userName
        this.channelName = channelName
        this.mobilNumber = mobilNumber
        this.email = email
        this.password = password
        this.uid = uid
    }

}