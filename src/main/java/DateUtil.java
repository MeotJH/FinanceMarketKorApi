import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private String _date;

    public <date> DateUtil(String date){
        this._date = date;
    }

    public String getDate() throws ParseException {
        return this._date;
    }
}
