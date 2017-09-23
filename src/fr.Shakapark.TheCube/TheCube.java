package fr.Shakapark.TheCube;

import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class TheCube extends JavaPlugin implements Listener {
	
	public static boolean pvp=true;

	public void onEnable() {
		System.out.println("Plugin TheCube started");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new CommandsEvent(this), this);

		getServer().getWorlds().get(0).setGameRuleValue("doDaylightCycle", "false");
		getServer().getWorlds().get(0).setTime(6000L);
		getServer().getWorlds().get(0).setStorm(false);
		getServer().getWorlds().get(0).setDifficulty(Difficulty.PEACEFUL);
	}
	
	public void setWaterAnimalSpawnLimit(int l){ l=0;
	}
	
	public void setMonsterSpawnLimit(int l){l=0;
	}

	public void onDisable() {
		System.out.println("Plugin TheCube stopped");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String msg, String[] args) {
		try{
			if(msg.equalsIgnoreCase("pvp")){
	
				if(sender instanceof Player && sender.isOp()){
					if(!CommandsEvent.gamerunning){
						if(args[0]!=""){
							switch(args[0]){
								
							case "true":
								pvp=true;
								sender.getServer().broadcastMessage(ChatColor.BLUE+"[TheCube]: PvP enabled");
								break;
								
							case "false":
								pvp=false;
								sender.getServer().broadcastMessage(ChatColor.BLUE+"[TheCube]: PvP disabled");
								break;
								
							default:
								sender.sendMessage(ChatColor.RED+"/pvp [true/false]");
							}
						}else sender.sendMessage(ChatColor.RED+"/pvp [true/false]");
					}else sender.sendMessage(ChatColor.RED+"[TheCube]: A game is running");
				}
				
				return true;
				
			}else{
				return false;
			}
		}catch(Exception e){
			sender.sendMessage(ChatColor.RED+"/pvp [true/false]");
			return false;
		}
		
	}
	
}


