package fr.Shakapark.TheCube;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class CommandsEvent implements Listener {

	public CommandsEvent(TheCube theCube) {}
	
// ============================================================================================================================================================================= //
// ================================================================================= Variables ================================================================================= //
// ============================================================================================================================================================================= //	

	private ArrayList<Player> OnPlayer = new ArrayList<Player>();	
	private ArrayList<Player> SpectPlayer = new ArrayList<Player>();
	private ArrayList<Player> Player = new ArrayList<Player>();
	private ArrayList<Player> NorPlayer = new ArrayList<Player>();
	private ArrayList<Player> BadPlayer = new ArrayList<Player>();
	
	private LinkedList<Location> TPSpawn = new LinkedList<Location>();
	
	private double x0 = -257, y0 = 24, z0 = -362, x1 = -257, y1 = 29, z1 = -320, x2 = -266, y2 = 32, z2 = -337, x3 = -290, y3 = 37, z3 = -320, x4 = -258, y4 = 55, z4 = -362;
	private double xs = -334, ys = 75, zs = -437, xc = -285, yc = 50, zc = -341, xf = -256, yf = 47, zf = -331, xt = -255, yt = 47, zt = -331;
	
	private Location loc0 = new Location(Bukkit.getServer().getWorlds().get(0), x0, y0, z0);
	private Location loc1 = new Location(Bukkit.getServer().getWorlds().get(0), x1, y1, z1);
	private Location loc2 = new Location(Bukkit.getServer().getWorlds().get(0), x2, y2, z2);
	private Location loc3 = new Location(Bukkit.getServer().getWorlds().get(0), x3, y3, z3);
	private Location loc4 = new Location(Bukkit.getServer().getWorlds().get(0), x4, y4, z4);
	private Location locspawn = new Location(Bukkit.getServer().getWorlds().get(0),xs,ys,zs);
	private Location loccenter = new Location(Bukkit.getServer().getWorlds().get(0),xc,yc,zc);
	
	private ItemStack goldsword = new ItemStack(Material.GOLD_SWORD);
	private ItemStack porte = new ItemStack(Material.IRON_DOOR);
	private ItemStack boutton = new ItemStack(Material.STONE_BUTTON);
	private ItemStack craneM = new ItemStack(Material.SKULL_ITEM, 1, (byte)1);
	
	private ItemStack[] itemStackMonstre1 = {new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(craneM)};
	private ItemStack[] itemStackMonstre2 = {new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.SKULL_ITEM)};
	private ItemStack[] itemStackVide = {new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)};
	
	private ItemMeta joinplayer = goldsword.getItemMeta();
	private ItemMeta start = boutton.getItemMeta();
	private ItemMeta quit = porte.getItemMeta();
	
	@SuppressWarnings("deprecation")
	private Potion potionDegat = new Potion(PotionType.INSTANT_DAMAGE, 1, true);
	
	protected static boolean gamerunning = false;
	private boolean damageplayer0 = false;
	private boolean damageplayer1 = false;
	private boolean damageplayer2 = false;
	private boolean damageplayer3 = false;
	

	
// ============================================================================================================================================================================= //
// ================================================================================= Fonctions ================================================================================= //
// ============================================================================================================================================================================= //	

	
	
	public void setTPSpawn(){
		TPSpawn.add(loc0);
		TPSpawn.add(loc1);
		TPSpawn.add(loc2);
		TPSpawn.add(loc3);
		TPSpawn.add(loc4);
	}
	
	
	
	public void setItemMetaSpawn(){
		joinplayer.setDisplayName("Join player queue");
		joinplayer.spigot().setUnbreakable(true);
		joinplayer.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		quit.setDisplayName("Leave player queue");
		start.setDisplayName("Start game");
		goldsword.setItemMeta(joinplayer);
		porte.setItemMeta(quit);
		boutton.setItemMeta(start);
	}
	
	
	
	public void giveItemStackSpawn(Player p){
		p.getInventory().setItem(0, goldsword);
		p.getInventory().setItem(1, porte);
		p.getInventory().setItem(8, boutton);
		p.updateInventory();
	}
	
	
	
	public void setCleanInventory(Player p){
		p.getInventory().clear();
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setExhaustion(5F);
		p.setExp(0L+0F);
		p.setLevel(0);
		p.getInventory().setArmorContents(itemStackVide);
		p.updateInventory();
	}
	
	
	
	public void setMonster(Player p){
		if(BadPlayer.get(0) == p) p.getInventory().setArmorContents(itemStackMonstre1);
		else p.getInventory().setArmorContents(itemStackMonstre2);
		p.getInventory().setItem(0, potionDegat.toItemStack(1));
		p.sendMessage(ChatColor.GREEN+"You’re infested ! You must infest the other players before they find the exit!");
		p.updateInventory();
	}

	
	
// ============================================================================================================================================================================= //
// ================================================================================= Command =================================================================================== //
// ============================================================================================================================================================================= //	
	
	
	
	@EventHandler
	public void onCommandes(PlayerCommandPreprocessEvent e){
		
		Player p = e.getPlayer();
		String msg = e.getMessage();
		String[] args = msg.split(" ");		
	
		
		
// =============================================================================================================================================== //		
// ==================================================================== Test ===================================================================== //		
// =============================================================================================================================================== //
		
		
		
		if(args[0].equalsIgnoreCase("/afficheonplayer")){
			e.setCancelled(true);
			for(int i=0;i<OnPlayer.size();i++){
				Player name = OnPlayer.get(i);
				System.out.println(name + "  " + i);
			}
		}
		
		if(args[0].equalsIgnoreCase("/afficheplayer")){
			e.setCancelled(true);
			for(int i=0;i<Player.size();i++){
				Player name = Player.get(i);
				System.out.println(name + "  " + i);
			}
		}
		
		if(args[0].equalsIgnoreCase("/affichebadplayer")){
			e.setCancelled(true);
			for(int i=0;i<BadPlayer.size();i++){
				Player name = BadPlayer.get(i);
				System.out.println(name + "  " + i);
			}
		}
		
		if(args[0].equalsIgnoreCase("/affichenorplayer")){
			e.setCancelled(true);
			for(int i=0;i<NorPlayer.size();i++){
				Player name = NorPlayer.get(i);
				System.out.println(name + "  " + i);
			}
		}

		
		
// =============================================================================================================================================== //		
// =============================================================== Arret de Partie =============================================================== //				
// =============================================================================================================================================== //				
		
		
		
		if(args[0].equalsIgnoreCase("/leave")){
			e.setCancelled(true);
			if(gamerunning==true){
				if(NorPlayer.contains(p)){
					NorPlayer.remove(p);
					p.setGameMode(GameMode.SPECTATOR);
					p.teleport(loccenter);
					p.getServer().broadcastMessage(ChatColor.BLUE+"[TheCube] : "+p.getName()+" has gave up !");
					if(NorPlayer.isEmpty()){
						p.getServer().getWorlds().get(0).setDifficulty(Difficulty.PEACEFUL);
						gamerunning=false;
						BadPlayer.clear();
						Player.clear();
						SpectPlayer.clear();
						for(int i=0;i<OnPlayer.size();i++){
							OnPlayer.get(i).teleport(locspawn);
							OnPlayer.get(i).setGameMode(GameMode.ADVENTURE);
							setCleanInventory(OnPlayer.get(i));
							}
						p.getServer().broadcastMessage(ChatColor.BLUE+"[TheCube] : Game Over");
					}
				}else{
					if(BadPlayer.contains(p)){
						BadPlayer.remove(p);
						p.setGameMode(GameMode.SPECTATOR);
						p.teleport(loccenter);
						p.getServer().broadcastMessage(ChatColor.BLUE+"[TheCube] : "+p.getName()+" has gave up !");
						if(BadPlayer.isEmpty()){
							p.getServer().getWorlds().get(0).setDifficulty(Difficulty.PEACEFUL);
							gamerunning=false;
							BadPlayer.clear();
							Player.clear();
							SpectPlayer.clear();
							for(int i=0;i<OnPlayer.size();i++){
								OnPlayer.get(i).teleport(locspawn);
								OnPlayer.get(i).setGameMode(GameMode.ADVENTURE);
								setCleanInventory(OnPlayer.get(i));
							}
							p.getServer().broadcastMessage(ChatColor.BLUE+"[TheCube] : Game Over !");
						}	
					}else{
						p.sendMessage(ChatColor.RED+"You aren’t a player !!");
					}
				}
			}else{
				p.sendMessage(ChatColor.RED+"No game currently playing");
			}
		}
		
		
				
		if(args[0].equalsIgnoreCase("/end")){
			e.setCancelled(true);
			if(gamerunning==true && p.isOp()){
					NorPlayer.clear();
					BadPlayer.clear();
					Player.clear();
					SpectPlayer.clear();
				Location locspawn = new Location(p.getWorld(),xs,ys,zs);
				for(int i=0;i<OnPlayer.size();i++){
					Player ptp2 = OnPlayer.get(i);
					ptp2.setGameMode(GameMode.ADVENTURE);
					ptp2.teleport(locspawn);
					setCleanInventory(ptp2);
					giveItemStackSpawn(ptp2);
				}
				gamerunning=false;
				p.getServer().getWorlds().get(0).setDifficulty(Difficulty.PEACEFUL);
				p.getServer().broadcastMessage(ChatColor.BLUE+"[TheCube] : Game Over");
			}else{
				p.sendMessage(ChatColor.RED+"No game currently playing");
			}
		}
	}

	
	
// ============================================================================================================================================================================= //
// ================================================================================== Event ==================================================================================== //
// ============================================================================================================================================================================= //	


	
	@EventHandler
	public void onPluginEnable(PluginEnableEvent e){
		setTPSpawn();
		setItemMetaSpawn();
		OnPlayer.addAll(Bukkit.getOnlinePlayers());
	}
	
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		OnPlayer.add(p);
		if(gamerunning==false){
			setCleanInventory(p);
			giveItemStackSpawn(p);
		
			p.setGameMode(GameMode.ADVENTURE);
			p.sendMessage(ChatColor.BLUE+"[TheCube]: Welcome into TheCube !");
			p.teleport(locspawn);
		}else{
			if(!Player.contains(p)){
				p.setGameMode(GameMode.SPECTATOR);
				p.sendMessage(ChatColor.BLUE+"[TheCube]: A game is Running");
				p.teleport(loccenter);
			}
		}
	}
	
	
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		OnPlayer.remove(e.getPlayer());
	}
	
	

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		Player p = (Player)e.getEntity();
		if(gamerunning==true){
			if(BadPlayer.contains(p)){
				if(!(e.getCause()==DamageCause.VOID)) e.setCancelled(true);
			}
		}else e.setCancelled(true);
	}
	

	
	@EventHandler
	public void onEntityDamageByEntentity(EntityDamageByEntityEvent e){
		
		Player pv = (Player)e.getEntity();
		Player pm = (Player)e.getDamager();
		if(gamerunning==true){
			if(BadPlayer.contains(pv)){
				if(NorPlayer.contains(pm) && pm.getItemInHand().getType()==Material.IRON_AXE){
					int r = (int)(Math.random()*TPSpawn.size());
					Location loc = TPSpawn.get(r);
					pv.teleport(loc);
					pm.getInventory().remove(pm.getItemInHand());
				}else{
					e.setCancelled(true);
				}
			}	
			if(NorPlayer.contains(pv) && BadPlayer.contains(pm)){
				for(int i=0;i<NorPlayer.size();i++){
					if(NorPlayer.get(i)==pv){
						if(i==0){
							damageplayer0 = true;
						}else{
							if(i==1){
								damageplayer1 = true;
							}else{
								if(i==2){
									damageplayer2 = true;
								}else{
									if(i==3){
										damageplayer3 = true;
									}	
								}	
							}
					    }
					}
				}
			}		
		}	
	}
		
	

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();

		if(e.getMaterial()==Material.GOLD_SWORD && gamerunning==false && (e.getAction()==Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK)){
			if(Player.contains(p)){
				p.sendMessage(ChatColor.RED+"You’re already in the queue.");
			}else{
				Player.add(p);
				p.getServer().broadcastMessage(ChatColor.GREEN+"[TheCube] : " + p.getName() + " has joined the game.");
			}	
		}
		
		if(e.getMaterial()==Material.IRON_DOOR && gamerunning==false && (e.getAction()==Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK)){
			if(Player.contains(p)){
				Player.remove(p);
				p.getServer().broadcastMessage(ChatColor.GREEN+"[TheCube] : " + p.getName() + " has left the game.");
			}else{
				p.sendMessage(ChatColor.RED+"You aren’t a player");
			}
		}
		
		if(e.getMaterial()==Material.STONE_BUTTON && gamerunning==false && (e.getAction()==Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK)){
				
			if(TheCube.pvp){
				
				if(Player.size()<2 || Player.size()>5){
					p.sendMessage(ChatColor.RED+"There is more or not enough players in the game !");
				}else{
					p.getServer().broadcastMessage(ChatColor.BLUE+"[TheCube] : Game Start !");
					p.getServer().getWorlds().get(0).setDifficulty(Difficulty.HARD);
					
					for(int i=0;i<Player.size();i++){
						int r = (int)(Math.random()*TPSpawn.size());
						Location loc = TPSpawn.get(r);
						TPSpawn.remove(loc);
						Player ptp = Player.get(i);
						ptp.teleport(loc);
						ptp.setGameMode(GameMode.ADVENTURE);
						setCleanInventory(ptp);
					}
					
					int random = (int)(Math.random()*Player.size());
					Player Monster = Player.get(random);
					BadPlayer.add(Monster);
					for(int i=0;i<Player.size();i++){
						Player temp = Player.get(i);
						if(temp != Monster) NorPlayer.add(temp);
					}
					for(int i=0;i<NorPlayer.size();i++){
						Player pN = NorPlayer.get(i);
						pN.sendMessage(ChatColor.GREEN+"You’re not infested ! Find the exit before the monster finds you !!");
					}
	
					setMonster(Monster);
				
					for(int i=0;i<OnPlayer.size();i++){
						Player pO = OnPlayer.get(i);
						if(!Player.contains(pO)){
							SpectPlayer.add(pO);
						}
					}
					for(int i=0;i<SpectPlayer.size();i++){
						SpectPlayer.get(i).setGameMode(GameMode.SPECTATOR);
						SpectPlayer.get(i).teleport(loccenter);
					}
					gamerunning = true;
	
					setTPSpawn();
				}
			}else{
				
				if(Player.size()>5){
					p.sendMessage(ChatColor.RED+"There is more players in the game !");
				}else{
					p.getServer().broadcastMessage(ChatColor.BLUE+"[TheCube] : Game Start !");
					p.getServer().getWorlds().get(0).setDifficulty(Difficulty.HARD);
					
					for(int i=0;i<Player.size();i++){
						int r = (int)(Math.random()*TPSpawn.size());
						Location loc = TPSpawn.get(r);
						TPSpawn.remove(loc);
						Player ptp = Player.get(i);
						NorPlayer.add(ptp);
						ptp.sendMessage(ChatColor.GREEN+"[TheCube]: Find the exit !!");
						ptp.teleport(loc);
						ptp.setGameMode(GameMode.ADVENTURE);
						setCleanInventory(ptp);
					}
					for(int i=0;i<OnPlayer.size();i++){
						Player pO = OnPlayer.get(i);
						if(!Player.contains(pO)){
							SpectPlayer.add(pO);
						}
					}
					for(int i=0;i<SpectPlayer.size();i++){
						SpectPlayer.get(i).setGameMode(GameMode.SPECTATOR);
						SpectPlayer.get(i).teleport(loccenter);
					}
					gamerunning = true;
					setTPSpawn();
				}
				
			}
		}
		
		if(e.getMaterial()==Material.FLINT_AND_STEEL && gamerunning==true && e.getAction()==Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType()==Material.NETHERRACK){
			e.getClickedBlock().getRelative(0, 1, 0).setType(Material.FIRE);
		}
		
		if(gamerunning==true && NorPlayer.contains(p) && e.getAction()==Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getX()==-252 && e.getClickedBlock().getY()==48 && e.getClickedBlock().getZ()==-332){
			p.setGameMode(GameMode.SPECTATOR);
			NorPlayer.remove(p);
			Player.remove(p);
			SpectPlayer.add(p);
			p.getServer().broadcastMessage(ChatColor.BLUE+"[TheCube]: "+p.getName()+" has find the exit.");
			if(NorPlayer.isEmpty()){
				p.getServer().getWorlds().get(0).setDifficulty(Difficulty.PEACEFUL);
				gamerunning=false;
				BadPlayer.clear();
				Player.clear();
				SpectPlayer.clear();
				for(int i=0;i<OnPlayer.size();i++){
					OnPlayer.get(i).teleport(locspawn);
					OnPlayer.get(i).setGameMode(GameMode.ADVENTURE);
					setCleanInventory(OnPlayer.get(i));
					giveItemStackSpawn(OnPlayer.get(i));
				}
				p.getServer().broadcastMessage(ChatColor.BLUE+"[TheCube] : Game Over !");
			}
		}
	}
	
	
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		if(NorPlayer.contains(p) && gamerunning==true){
			for(int i=0;i<NorPlayer.size();i++){
				if(NorPlayer.get(i)==p){
					if(i==0 && damageplayer0 == true){
						BadPlayer.add(p);
						NorPlayer.remove(p);
						e.setRespawnLocation(loc);
						p.sendMessage(ChatColor.GREEN+"You’re infested ! You must infest the other players before they find the exit!");
						p.getInventory().setArmorContents(itemStackMonstre2);
					}else{
						if(i==1 && damageplayer1 == true){
							BadPlayer.add(p);
							NorPlayer.remove(p);
							e.setRespawnLocation(loc);
							p.sendMessage(ChatColor.GREEN+"You’re infested ! You must infest the other players before they find the exit!");
							p.getInventory().setArmorContents(itemStackMonstre2);
						}else{
							if(i==2 && damageplayer2 == true){
								BadPlayer.add(p);
								NorPlayer.remove(p);
								e.setRespawnLocation(loc);
								p.sendMessage(ChatColor.GREEN+"You’re infested ! You must infest the other players before they find the exit!");
								p.getInventory().setArmorContents(itemStackMonstre2);
							}else{
								if(i==3 && damageplayer3 == true){
									BadPlayer.add(p);
									NorPlayer.remove(p);
									e.setRespawnLocation(loc);
									p.sendMessage(ChatColor.GREEN+"You’re infested ! You must infest the other players before they find the exit!");
									p.getInventory().setArmorContents(itemStackMonstre2);
								}else{
									int r = (int)(Math.random()*5);
									Location l = TPSpawn.get(r);
									e.setRespawnLocation(l);
								}
							}
						}
					}
				}
			}
			if(NorPlayer.isEmpty()){
				p.getServer().getWorlds().get(0).setDifficulty(Difficulty.PEACEFUL);
				gamerunning=false;
				BadPlayer.clear();
				Player.clear();
				SpectPlayer.clear();
				for(int i=0;i<OnPlayer.size();i++){
					OnPlayer.get(i).teleport(locspawn);
					OnPlayer.get(i).setGameMode(GameMode.ADVENTURE);
					setCleanInventory(OnPlayer.get(i));
					giveItemStackSpawn(OnPlayer.get(i));
				}
				e.setRespawnLocation(locspawn);
				p.getServer().broadcastMessage(ChatColor.BLUE+"[TheCube] : Game Over !");
			}
		}
		if(BadPlayer.contains(p) && gamerunning==true){
			int r = (int)(Math.random()*TPSpawn.size());
			Location locRespawn = TPSpawn.get(r);
			e.setRespawnLocation(locRespawn);
			if(BadPlayer.get(0)==p){
				p.getInventory().setArmorContents(itemStackMonstre1);
			}else{
				p.getInventory().setArmorContents(itemStackMonstre2);
			}
		}
	}

	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		if(e.getCurrentItem().getType()==Material.SKULL_ITEM) e.setCancelled(true);
		if(e.getCurrentItem().getType()==Material.GOLD_SWORD && e.getSlot()==0) e.setCancelled(true);
		if(e.getCurrentItem().getType()==Material.IRON_DOOR && e.getSlot()==1) e.setCancelled(true);
		if(e.getCurrentItem().getType()==Material.STONE_BUTTON && e.getSlot()==8) e.setCancelled(true);
	}
	
	
	
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent e){
		if(e.getRegainReason()==RegainReason.SATIATED) e.setCancelled(true);
	}
	
	
	
	@EventHandler
	public void onPlayerAchievmentAwarded(PlayerAchievementAwardedEvent e){
		e.setCancelled(true);
	}
 
	
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e){
		e.setCancelled(true);
	}

}
