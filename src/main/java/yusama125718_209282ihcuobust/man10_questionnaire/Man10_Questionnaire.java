package yusama125718_209282ihcuobust.man10_questionnaire;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.event.ClickEvent.runCommand;
import static net.kyori.adventure.text.event.ClickEvent.suggestCommand;

public final class Man10_Questionnaire extends JavaPlugin
{
    static public boolean active = false;
    static public boolean system = true;
    static public HashMap<String,String> odai = new HashMap<>();
    static public HashMap<String,Integer> odaitype = new HashMap<>();
    static public String addid;
    static public String addodai;
    static public Integer addtype;
    public static JavaPlugin mque;
    static public boolean usercheck = false;
    static public String checkid;
    static public UUID checkuser;
    static public Player sendplayer;

    @Override
    public void onEnable()
    {
        mque = this;
        List<String> odainame = mque.getConfig().getStringList("odai");
        List<String> odaiid = mque.getConfig().getStringList("odaiID");
        List<Integer> type = mque.getConfig().getIntegerList("type");
        for (int i = 0;i<odaiid.size();i++)
        {
            odai.put(odainame.get(i),odaiid.get(i));
            odaitype.put(odainame.get(i),type.get(i));
        }
        system = mque.getConfig().getBoolean("system");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!sender.hasPermission("mquestion.player"))
        {
            sender.hasPermission("§c[MQuestion]You don't have Permission");
            return true;
        }
        switch (args.length)
        {
            case 1:
            {
                if (args[0].equals("help"))
                {
                    if (sender.hasPermission("mquestion.op"))
                    {
                        sender.sendMessage("§e§l[§9§lMQuestion§e§l]§5/mquestion on [お題] t/f §f§l: §5ONにします");
                        sender.sendMessage("§e§l[§9§lMQuestion§e§l]§5/mquestion off §f§l: §5OFFにします");
                        sender.sendMessage("§e§l[§9§lMQuestion§e§l]§5/mquestion start [お題] [ID] t/f §f§l: §5true/falseで回答する質問をスタートします");
                    }
                    sender.sendMessage("§e§l[§9§lMQuestion§e§l]§5/mquestion open §f§l: §5アンケートの画面を開きます");
                    return true;
                }
                if (args[0].equals("on"))
                {
                    if (!sender.hasPermission("mquestion.op"))
                    {
                        sender.sendMessage("§e§l[§9§lMQuestion§e§l]§5 /mquestion helpでhelpを表示");
                        return true;
                    }
                    system = true;
                    mque.getConfig().set("system",system);
                    saveConfig();
                    sender.sendMessage("§e§l[§9§lMQuestion§e§l]§rONにしました");
                }
                if (args[0].equals("off"))
                {
                    if (!sender.hasPermission("mquestion.op"))
                    {
                        sender.sendMessage("§e§l[§9§lMQuestion§e§l]§5 /mquestion helpでhelpを表示");
                        return true;
                    }
                    system = false;
                    mque.getConfig().set("system",system);
                    saveConfig();
                    sender.sendMessage("§e§l[§9§lMQuestion§e§l]§rOFFにしました");
                }
                if (args[0].equals("open"))
                {
                    if (!(sender instanceof Player))
                    {
                        sender.sendMessage(("§c[MQuestion]Player以外は実行できません"));
                        return true;
                    }
                    sendplayer = ((Player) sender);
                    QuestionField GUI = new QuestionField();
                    GUI.start();
                    return true;
                }
                if (args[0].equals("list"))
                {
                    sender.sendMessage("§l=====現在受付中の質問=====");
                    for (String string : odai.keySet())
                    {
                        sender.sendMessage(string);
                    }
                }
            }
            case 3:
            {
                if (!system)
                {
                    sender.sendMessage("§e§l[§9§lMQuestion§e§l]§cMQuestionは現在OFFです");
                    return true;
                }
                if (!odai.containsKey(args[0]))
                {
                    sender.sendMessage("§e§l[§9§lMQuestion§e§l]§cその質問は存在しません");
                    return true;
                }
                if (!sender.hasPermission("mquestion.op"))
                {
                    if (args[1].equals("finish"))
                    {
                        odai.remove(args[0]);
                        odaitype.remove(args[0]);
                        List<String> odainame = new ArrayList<>();
                        List<String> odaiid = new ArrayList<>();
                        List<Integer> type = mque.getConfig().getIntegerList("type");
                        for (String string : odai.keySet())
                        {
                            odainame.add(odai.get(string));
                            odaiid.add(string);
                            type.add(odaitype.get(string));
                        }
                        mque.getConfig().set("odai",odainame);
                        mque.getConfig().set("odaiID",odaiid);
                        mque.getConfig().set("type",type);
                        saveConfig();
                        sender.sendMessage("§e§l[§9§lMQuestion§e§l]§r§b"+args[0]+"§rを終了しました");
                        return true;
                    }
                }
                usercheck = false;
                checkid = args[0];
                checkuser = ((Player) sender).getUniqueId();
                UserCheck check = new UserCheck();
                check.start();
                try
                {
                    check.join();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            case 5:
            {
                if (!sender.hasPermission("mquestion.op"))
                {
                    sender.sendMessage("§e§l[§9§lMQuestion§e§l]§5 /mquestion helpでhelpを表示");
                    return true;
                }
                if (args[0].equals("start"))
                {
                    if (!system)
                    {
                        sender.sendMessage("§e§l[§9§lMQuestion§e§l]§cMQuestionは現在OFFです");
                        return true;
                    }
                    if (odai.size()>=9)
                    {
                        sender.sendMessage("§e§l[§9§lMQuestion§e§l]§cアンケートは1度に9つまでしか開けません");
                        return true;
                    }
                    if (args[3].equals("t/f"))
                    {
                        if (args[1].length()>100)
                        {
                            sender.sendMessage("§e§l[§9§lMQuestion§e§l]§cお題は100文字以内にしてください");
                            return true;
                        }
                        if (args[2].length()>10)
                        {
                            sender.sendMessage("§e§l[§9§lMQuestion§e§l]§cIDは10文字以内にしてください");
                            return true;
                        }
                        odai.put(args[2],args[1]);
                        odaitype.put(args[2],0);
                        addodai = args[1];
                        addid = args[2];
                        addtype = 0;
                        List<String> odainame = new ArrayList<>();
                        List<String> odaiid = new ArrayList<>();
                        List<Integer> type = new ArrayList<>();
                        for (String string : odai.keySet())
                        {
                            odainame.add(odai.get(string));
                            odaiid.add(string);
                            type.add(odaitype.get(string));
                        }
                        mque.getConfig().set("odai",odainame);
                        mque.getConfig().set("odaiID",odaiid);
                        mque.getConfig().set("type",type);
                        saveConfig();
                        sender.sendMessage("§e§l[§9§lMQuestion§e§l]§r開始しました");
                        Createtable tablecreate = new Createtable();
                        tablecreate.start();
                        try
                        {
                            tablecreate.join();
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        Bukkit.broadcast("§e§l[§9§lMQuestion§e§l]§r"+odai+"について§e§lアンケートが始まりました！","mquestion.player");
                        Bukkit.broadcast(String.valueOf(text("§e§l[ここをクリックで回答する]").clickEvent(runCommand("/mquestion open"))),"mquestion.player");
                    }
                    else if ("free")
                    {
                        if (args[1].length()>100)
                        {
                            sender.sendMessage("§e§l[§9§lMQuestion§e§l]§cお題は100文字以内にしてください");
                            return true;
                        }
                        if (args[2].length()>10)
                        {
                            sender.sendMessage("§e§l[§9§lMQuestion§e§l]§cIDは10文字以内にしてください");
                            return true;
                        }
                        odai.put(args[2],args[1]);
                        odaitype.put(args[2],0);
                        addodai = args[1];
                        addid = args[2];
                        addtype = 1;
                        List<String> odainame = new ArrayList<>();
                        List<String> odaiid = new ArrayList<>();
                        List<Integer> type = mque.getConfig().getIntegerList("type");
                        for (String string : odai.keySet())
                        {
                            odainame.add(odai.get(string));
                            odaiid.add(string);
                            type.add(odaitype.get(string));
                        }
                        mque.getConfig().set("odai",odainame);
                        mque.getConfig().set("odaiID",odaiid);
                        mque.getConfig().set("type",type);
                        saveConfig();
                        sender.sendMessage("§e§l[§9§lMQuestion§e§l]§r開始しました");
                        Createtable tablecreate = new Createtable();
                        tablecreate.start();
                        try
                        {
                            tablecreate.join();
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        Bukkit.broadcast("§e§l[§9§lMQuestion§e§l]§r"+odai+"について§e§lアンケートが始まりました！","mquestion.player");
                        Bukkit.broadcast(String.valueOf(text("§e§l[ここをクリックで回答する]").clickEvent(runCommand("/mquestion open"))),"mquestion.player");
                    }
                }
            }
            default:
            {
                sender.sendMessage("§e§l[§9§lMQuestion§e§l]§5 /mquestion helpでhelpを表示");
                return true;
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
