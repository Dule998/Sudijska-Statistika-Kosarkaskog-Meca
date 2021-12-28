package Models.Helpers;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class HelperModel {

    public static Long getTicks()// Get ticks (mills)
    {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date.getTime();
    }

    public static Date getDate(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date;
    }

    private static Random random;
    public static int getRandomNumber(int min, int max) {
        random = new Random();
        return random.nextInt(max - min) + min;
    }


    public static final String roleAdmin = "{admin-role}";
    public static final String roleJudge = "{judge-role}";
    public static final String roleVisitor = "{visitor-role}";


    public static final int ResultRegisteer_AddUser = 10001;
    public static final int ResultRegisteer_NewMatchStatistic = 10002;

}//[Class]
