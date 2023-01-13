@file:JvmName("ChatgptUtil")
package com.yikuni.mc.alicechatgptmcplugin.util

import cn.hutool.http.HttpUtil
import com.yikuni.mc.alicechatgptmcplugin.AliceChatgptPlugin


suspend fun createConversation(prompt: String? = null): String{
    val request = HttpUtil.createPost("${AliceChatgptPlugin.HOST}/chatgpt/create")
        .header("token", AliceChatgptPlugin.TOKEN)
    if (prompt != null) request.body(prompt)
    return request.execute().body()
}

suspend fun sendConversationMessage(id: String): String{
    val request = HttpUtil.createPost("${AliceChatgptPlugin.HOST}/chatgpt/chat")
        .header("token", AliceChatgptPlugin.TOKEN)
        .header("conversation", id)
    return request.execute().body()
}

suspend fun finishConversation(id: String){
    val request = HttpUtil.createPost("${AliceChatgptPlugin.HOST}/chatgpt/finish")
        .header("token", AliceChatgptPlugin.TOKEN)
        .header("conversation", id)
}

