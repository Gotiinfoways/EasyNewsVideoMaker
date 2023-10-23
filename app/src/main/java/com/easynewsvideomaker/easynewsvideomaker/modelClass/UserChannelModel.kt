package com.easynewsvideomaker.easynewsvideomaker.modelClass

class UserChannelModel{
    var channelLogo:String?=null
    var channelName: String? = null
    var repoterName: String? = null
    var facebookLink: String? = null
    var twitterLink: String? = null
    var instagramLink: String? = null
    var youtubeLink: String? = null
    var websiteLink: String? = null

    constructor() {}

    constructor(
        channelLogo: String,
        channelName: String,
        repoterName: String,
        facebookLink: String,
        twitterLink: String,
        instagramLink: String,
        youtubeLink: String,
        websiteLink: String
    ) {
        this.channelLogo = channelLogo
        this.channelName = channelName
        this.repoterName = repoterName
        this.facebookLink = facebookLink
        this.twitterLink = twitterLink
        this.instagramLink = instagramLink
        this.youtubeLink = youtubeLink
        this.websiteLink = websiteLink
    }
}