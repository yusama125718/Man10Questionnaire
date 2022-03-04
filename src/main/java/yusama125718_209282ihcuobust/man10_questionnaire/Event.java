package yusama125718_209282ihcuobust.man10_questionnaire;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.event.ClickEvent.runCommand;
import static net.kyori.adventure.text.event.ClickEvent.suggestCommand;
import static yusama125718_209282ihcuobust.man10_questionnaire.Man10_Questionnaire.*;

public class Event implements Listener
{
    @EventHandler
    public void onInvClick(final InventoryClickEvent e)
    {
        String clickid = null;
        MySQLManager mysql = new MySQLManager(mque,"mquestion");
        final ItemStack clickedItem = e.getCurrentItem();
        Player p=(Player) e.getWhoClicked();
        if (e.getView().getTitle().equals("質問リスト"))
        {
            e.setCancelled(true);
            if (clickedItem == null || clickedItem.getType().equals(Material.AIR)||clickedItem.getType().equals(Material.WHITE_STAINED_GLASS_PANE)) return;
            boolean click = false;
            aaa: for (String string : odai.keySet())
            {
                if (Objects.requireNonNull(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName()).equals(string))
                {
                    click = true;
                    clickid = string;
                    break aaa;
                }
            }
            if (click)
            {
                if (odaitype.get(clickid)==0)
                {
                    p.closeInventory();
                    Inventory inv = Bukkit.createInventory(null,27,"回答");
                    for (int i=0;i<27;i++)
                    {
                        inv.setItem(i,Material.WHITE_STAINED_GLASS_PANE,"");
                    }
                    inv.setItem(12,Material.BLUE_STAINED_GLASS_PANE,"§lはい");
                    inv.setItem(16,Material.RED_STAINED_GLASS_PANE,"§lいいえ");
                    p.openInventory(inv);
                }
                else if (odaitype.get(clickid)==1)
                {
                    p.closeInventory();
                    p.sendMessage("§e§l[§9§lMQuestion§e§l]§r"+odai.get(clickid)+"についての回答をコマンドで送信してください");
                    p.sendMessage(text("§e§l[ここをクリックで自動入力する]").clickEvent(suggestCommand("/mquestion "+clickid+" ")));
                }
            }
        }
        else if (e.getView().getTitle().equals("回答"))
        {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            e.setCancelled(true);
            if (Objects.requireNonNull(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName()).equals("§lはい"))
            {
                mysql.execute("insert into "+clickid+"(time,usename,useruuid,answer)values('"+date+","+p+","+p.getUniqueId()+",true');");
            }
            else if (Objects.requireNonNull(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName()).equals("§lいいえ"))
            {
                mysql.execute("insert into "+clickid+"(time,usename,useruuid,answer)values('"+date+","+p+","+p.getUniqueId()+",false');");
            }
        }
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e)
    {
        if (odai.size()>0)
        {
            Bukkit.getScheduler().runTaskLater((Plugin) this, new Runnable()
            {
                @Override
                public void run()
                {
                    e.getPlayer().sendMessage("§e§l[§9§lMQuestion§e§l]§r§e§lアンケートが行われています！");
                    e.getPlayer().sendMessage(text("§e§l[ここをクリックでアンケートに答える]").clickEvent(runCommand("/mquestion open")));
                }
            }, 100);
        }
    }
}
