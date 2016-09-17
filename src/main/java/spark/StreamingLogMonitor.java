package spark;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by changhongzi on 2016/9/8.
 */
public class StreamingLogMonitor {

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect(
            "http://master1.bigdata.haierubic.com:8088/proxy/application_1472979898086_0175/executors/")
            .get();
        Elements newsHeadlines = doc.select(".row .span12 tr a");
        for (int i = 0; i < newsHeadlines.size(); i++) {
            Element element = newsHeadlines.get(i);
            String href = element.attr("href").replace("start=-4096", "start=0");
            if (href.startsWith("http://")) {
                System.out.println(href);
                Document dccc = Jsoup.connect(href).get();
                System.out.println(dccc.body());

            }
        }
    }
}
