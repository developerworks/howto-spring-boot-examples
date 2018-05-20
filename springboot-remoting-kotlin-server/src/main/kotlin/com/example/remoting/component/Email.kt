package com.example.remoting.component

import java.io.Serializable

class Email() : Serializable {
    private lateinit var to: String
    private lateinit var body: String

    constructor(to: String, body: String) : this() {
        this.to = to
        this.body = body
    }

    fun getTo(): String {
        return this.to
    }

    fun setTo(to: String) {
        this.to = to
    }

    fun getBody(): String {
        return this.body
    }

    fun setBody(body: String) {
        this.body = body
    }

    override fun toString(): String {
        return String.format("Email{to=%s, body=%s}", getTo(), getBody())
    }
}