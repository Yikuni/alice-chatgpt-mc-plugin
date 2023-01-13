package com.yikuni.mc.alicechatgptmcplugin.domain

import com.yikuni.mc.alicechatgptmcplugin.util.createConversation
import com.yikuni.mc.alicechatgptmcplugin.util.finishConversation
import com.yikuni.mc.alicechatgptmcplugin.util.sendConversationMessage
import com.yikuni.mc.rumiyalib.utils.sender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class ChatgptChannel(private val player: Player, prompt: String? = null) {
    init {
        CoroutineScope(Dispatchers.Default).launch {
            conversationId = createConversation(prompt)
        }
    }
    lateinit var conversationId: String
    var replied = true
    var paused = false

    fun chat(message: String){
        replied = false
        CoroutineScope(Dispatchers.Default).launch {
            val reply = sendConversationMessage(message)
            player.sendMessage("${ChatColor.DARK_GREEN}AI: $reply")
            replied = true
        }
    }

    fun finish(){
        CoroutineScope(Dispatchers.Default).launch {
            finishConversation(conversationId)
            player.sender().info("会话已结束")
        }
    }

}