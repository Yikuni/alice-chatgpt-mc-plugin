package com.yikuni.mc.alicechatgptmcplugin.command

import com.yikuni.mc.alicechatgptmcplugin.core.ChatgptChannelManager
import com.yikuni.mc.reflect.annotation.YikuniCommand
import com.yikuni.mc.rumiyalib.utils.sender
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

@YikuniCommand(value = "chatgpt", permission = "chatgpt")
class ChatgptCommand: CommandExecutor, TabCompleter {
    private val usage = "${ChatColor.YELLOW} --------${ChatColor.WHITE} Help Chatgpt ${ChatColor.YELLOW}--------\n" +
            "${ChatColor.GRAY} Below is a help list of Raffle command \n" +
            "${ChatColor.GRAY}/chatgpt   进入AI聊天频道\n" +
            "${ChatColor.GRAY}/chatgpt create <设定>   创建特定设定的聊天\n" +
            "${ChatColor.GRAY}/chatgpt pause   暂停聊天\n" +
            "${ChatColor.GRAY}/chatgpt continue   继续聊天\n" +
            "${ChatColor.GRAY}/chatgpt finish   结束聊天"

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        return if (args.isEmpty()){
            val channel = ChatgptChannelManager.getChannel(sender)
            if (channel == null){
                // 还没有频道, 自动创建
                createChannel(sender)
            }else{
                channel.paused = false
                sender.sender().success("进入会话")
            }
            true
        }else{
             when(args[0]){
                "create" ->{
                    createChannel(sender, if (args.size == 1) null else args[1])
                    true
                }
                "help" ->{
                    sender.sender().info(usage)
                    return true
                }
                "pause" ->{
                    val channel = ChatgptChannelManager.getChannel(sender)
                    if (channel == null){
                        sender.sender().error("未检测到有效的会话")
                    }else{
                        channel.paused = true
                        sender.sender().success("会话暂停成功")
                    }
                    true
                }
                 "continue" ->{
                     val channel = ChatgptChannelManager.getChannel(sender)
                     if (channel == null){
                         sender.sender().error("未检测到有效的会话")
                     }else{
                         channel.paused = false
                         sender.sender().success("进入会话")
                     }
                     true
                 }
                "finish" ->{
                    val channel = ChatgptChannelManager.getChannel(sender)
                    if (channel == null){
                        sender.sender().error("未检测到有效的会话")
                    }else{
                        ChatgptChannelManager.deleteChannel(sender)
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        if (args.size == 1){
            return arrayOf("create", "help", "pause", "finish", "continue").filter { it.startsWith(args[0]) }.toMutableList()
        }
        return null
    }

    private fun createChannel(player: Player, prompt: String? = null){
        val channel = ChatgptChannelManager.getChannel(player)
        if (channel == null){
            player.sender().success("创建会话成功,自动进入会话")
            ChatgptChannelManager.createChannel(player)
        }else{
            ChatgptChannelManager.deleteChannel(player)
            ChatgptChannelManager.createChannel(player, prompt)
            player.sender().success("自动结束未完成的会话,进入新会话")
        }
    }
}