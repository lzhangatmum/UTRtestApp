package com.example.github.utrtestapp.bean

data class UserBean(
    var name: String,
    var id: Long,
    var avatar_url: String,
    var location: String,

    var subscriptions_url: String,
    var followers: String,
    var received_events: String,
    var repos_url :String
)