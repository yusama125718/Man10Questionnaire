package yusama125718_209282ihcuobust.man10_questionnaire;

import static yusama125718_209282ihcuobust.man10_questionnaire.Man10_Questionnaire.*;

public class UserCheck extends Thread
{
    public void start()
    {
        MySQLManager mysql = new MySQLManager(mque,"mquestion");
        mysql.execute("select * from "+checkid+" where useruuid in ("+checkuser+");");
    }
}
