import com.sun.org.apache.xpath.internal.objects.XString;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

    private String date;

    public <date> DateUtil(String date){
        this.date = date;
    }

    public String getDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String strToday = sdf.format(c1.getTime());

        return strToday;

    }
}
