import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

    public String getDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String strToday = sdf.format(c1.getTime());

        return strToday;

    }
}
