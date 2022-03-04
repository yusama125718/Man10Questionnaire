package yusama125718_209282ihcuobust.man10_questionnaire;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.Material;

import static yusama125718_209282ihcuobust.man10_questionnaire.Man10_Questionnaire.*;

public class QuestionField extends Thread
{
    public void start()
    {
        Inventory inv = Bukkit.createInventory(null,27,"質問リスト");
        for (int i=0;i<27;i++)
        {
            inv.setItem(i,Material.WHITE_STAINED_GLASS_PANE,"");
        }
        int i = 0;
        for (String string : odai.keySet())
        {
            i++;
            if (odaitype.get(string)==0)
            {
                inv.setItem(i+9,Material.RED_STAINED_GLASS_PANE,string);
            }
            else if (odaitype.get(string)==1)
            {
                inv.setItem(i+9,Material.YELLOW_STAINED_GLASS_PANE,string);
            }
        }
        sendplayer.openInventory(inv);
    }
}
