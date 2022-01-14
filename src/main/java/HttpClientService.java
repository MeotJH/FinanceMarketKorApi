import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HttpClientService {

    private String getCodeGenerate(){

        DateUtil dateUtil = new DateUtil("20220114");

        String code = "";
        String date = dateUtil.getDate();

        System.out.println("this is date: " + date);


        try {

            URI dataGenerateUrl = new URIBuilder()
                    .setScheme("http")
                    .setHost("data.krx.co.kr")
                    .setPath("/comm/fileDn/GenerateOTP/generate.cmd")
                    .setParameter("mktId", "ALL")
                    .setParameter("trdDd", date)
                    .setParameter("share", "1")
                    .setParameter("money", "1")
                    .setParameter("csvxls_isNo", "false")
                    .setParameter("name", "fileDown")
                    .setParameter("url", "dbms/MDC/STAT/standard/MDCSTAT01501")
                    .build();

            CloseableHttpClient hc = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(dataGenerateUrl);
            CloseableHttpResponse httpResponse = hc.execute(httpGet);

            code = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

        } catch (Exception e) {

            e.printStackTrace();
        }

        return code;

    }

    private InputStream getFinanceData(){

        InputStream excelData = null;

        try {

            String code = getCodeGenerate();
            CloseableHttpClient hc = HttpClients.createDefault();

            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("code", code));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            HttpPost httpPost = new HttpPost("http://data.krx.co.kr/comm/fileDn/download_excel/download.cmd");
            httpPost.setEntity(entity);

            CloseableHttpResponse httpResponse = hc.execute(httpPost);
            excelData = httpResponse.getEntity().getContent();
        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return excelData;
    }

    public void getMarketDataOne(){

        InputStream excelData = getFinanceData();
        try {

            XSSFWorkbook workbook = new XSSFWorkbook(excelData);
            // FileOutputStream out = new FileOutputStream("hello.xlsx");
            // workbook.write(out);
            // workbook.close();

            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(1);

            for(Cell cell : row) {
                System.out.print(cell+"\t");
            }

            workbook.close();
        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
