package com.yikuni.mc.alicechatgptmcplugin.core

import com.yikuni.mc.alicechatgptmcplugin.domain.ChatgptChannel
import org.bukkit.entity.Player

object ChatgptChannelManager {
    private val channelMap = mutableMapOf<Player, ChatgptChannel>()

    fun createChannel(player: Player, prompt: String? = null): ChatgptChannel{
        val channel = ChatgptChannel(player, prompt)
        channelMap[player] = channel
        return channel
    }

    fun deleteChannel(player: Player): ChatgptChannel?{
        val channel = channelMap.remove(player)
        channel?.finish()
        return channel
    }

    fun getChannel(player: Player): ChatgptChannel?{
        return channelMap[player]
    }
}