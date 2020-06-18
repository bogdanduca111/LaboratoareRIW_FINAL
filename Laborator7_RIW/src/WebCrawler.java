import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@SuppressWarnings("unused")
public class WebCrawler {
	private static LinkedList<String> q = new LinkedList<String>();
	public static String path = "D:\\workspace\\Laborator7_RIW\\http\\riweb.tibeica.com\\crawl\\";
	public static HttpClient httpClient = new HttpClient("CLIENT RIW");
	public void Crawler()
	{
		
		
		try {
			String mydoc = null;
			int nrPagini=0;
			Document doc=Jsoup.connect("http://riweb.tibeica.com/crawl/").get();
			//System.out.println(doc);
			Elements linksOfPages = doc.select("a[href]");
			for(Element l: linksOfPages)
			{
				String ll=l.attr("abs:href");
				q.add(ll);
				System.out.println(ll);
				nrPagini++;
				
			}
			System.out.println(nrPagini);
			while(!q.isEmpty()&&(nrPagini<100))
			{
				
				for (int i = 0; i < q.size(); i++) {
					
					mydoc = httpClient.getResource(q.get(i),"riweb.tibeica.com", "riweb.tibeica.com", 80);  
					
					String[] res = q.get(i).split("/");
					String link = res[res.length - 1];
					
					FileWriter fw = new FileWriter(path+link);
					BufferedWriter bw = new BufferedWriter(fw);
					
					
					
					bw.write(mydoc);
					bw.close();
				}
			}
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) throws IOException {
	
		WebCrawler crw=new WebCrawler();
		crw.Crawler();
	}

}