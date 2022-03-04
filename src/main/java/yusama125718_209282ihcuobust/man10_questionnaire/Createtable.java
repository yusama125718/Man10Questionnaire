package yusama125718_209282ihcuobust.man10_questionnaire;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static yusama125718_209282ihcuobust.man10_questionnaire.Man10_Questionnaire.addid;
import static yusama125718_209282ihcuobust.man10_questionnaire.Man10_Questionnaire.mque;

public class Createtable extends Thread
{
    @Override
    public void start()
    {
        MySQLManager mysql = new MySQLManager(mque,"mquestion");
        mysql.execute("create table if not exists "+addid+"(id int auto_increment,time varchar(19),username varchar(50),useruuid varchar(50),answer varchar(100),primary key(id));");
    }
}
