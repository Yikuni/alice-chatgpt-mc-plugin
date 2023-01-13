package com.yikuni.mc.alicechatgptmcplugin.core

import com.yikuni.mc.reflect.annotation.YikuniEvent
import com.yikuni.mc.rumiyalib.utils.sender
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

@YikuniEvent
class ChatgptMessageListener: Listener {
    @EventHandler(priority = EventPriority.HIGH)
    fun onMessage(event: AsyncPlayerChatEvent){
        val channel = ChatgptChannelManager.getChannel(event.player)?:return
        if (channel.paused) return
        event.isCancelled = true
        val sender = event.player.sender()
        if (!channel.replied){
            // 还没回复
            sender.warn("姬弃人思考中...")
        }else{
            event.player.sendMessage("${ChatColor.GREEN}${event.player.name}: ${event.message}")
            channel.chat(event.message)
        }
    }
}