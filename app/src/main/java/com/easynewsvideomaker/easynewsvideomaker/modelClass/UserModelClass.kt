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
    var userBlock: String? = null
    var uid: String? = null
    var channelLogo: String? = null
    var channelName: String? = null
    var repoterName: String? = null
    var facebookLink: String? = null
    var twitterLink: String? = null
    var instagramLink: String? = null
    var youtubeLink: String? = null
    var websiteLink: String? = null

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
        userBlock: String,
        uid: String,
        channelLogo: String,
        channelName: String,
        repoterName: String,
        facebookLink: String,
        twitterLink: String,
        instagramLink: String,
        youtubeLink: String,
        websiteLink: String
    ) {
        this.userName = userName
        this.mobilNumber = mobilNumber
        this.email = email
        this.password = password
        this.startDate = startDate
        this.endDate = endDate
        this.packageType = packageType
        this.login_device_name = login_device_name
        this.userBlock = userBlock
        this.uid = uid
//
        this.channelLogo=channelLogo
        this.channelName=channelName
        this.repoterName=repoterName
        this.facebookLink=facebookLink
        this.twitterLink=twitterLink
        this.instagramLink=instagramLink
        this.youtubeLink=youtubeLink
        this.websiteLink=websiteLink
    }

}