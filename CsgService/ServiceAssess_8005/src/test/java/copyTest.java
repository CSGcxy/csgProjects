import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lishuguang
 * @create 2022-05-03-15:37
 */
public class copyTest {

    public static void main(String[] args) throws ParseException, ParseException {

        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //1361325960
        long epoch = df.parse("2013-02-20 10:06:00").getTime();
        System.out.println("should be 1361325960 ："+epoch);


        Date d=new Date();
        String t=df.format(d);
        epoch=df.parse(t).getTime()/1000;
        System.out.println("t is ："+t+",unix stamp is "+epoch);

    }

}
