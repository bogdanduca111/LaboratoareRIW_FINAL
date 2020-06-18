
import java.io.File;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element; 
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;





public class laborator1 {

	public static void laborator1() throws IOException
	{
		File input=new File("/workspace/emag.html");
		
		Document doc = Jsoup.parse(input,"utf-8");
		
		String title = doc.title();
		System.out.println("Title: "+title);
		
		String keywords = doc.select("meta[name=keywords]").first().attr("content");  
        System.out.println("Primul Meta keyword : " + keywords);  
        
        String description = doc.select("meta[name=description]").first().attr("content");  
        System.out.println("Primul Meta description : " + description);  
        
        String robot = doc.select("meta[name=robots]").first().attr("content");
        System.out.println("Primul Robots: "+robot);
        
        Element link = doc.select("a[href^=https:]").first();
        System.out.println("Primul link: "+link.attr("abs:href"));
        
       System.out.println("Text:");
       String text = doc.body().text();
       System.out.println(text);
	}
//Laborator 1 Ex 2
	
	public static Scanner input;		
	public static Map<String, Integer> TextSplit(File file) 
	{
		Map<String, Integer> map = new HashMap<String,Integer>();
		try {
			input=new Scanner(file);
			while(input.hasNextLine())
			{
				String line=input.nextLine();
				String mystring=line.toLowerCase();
				StringBuilder word=new StringBuilder();
				for(int i=0;i<mystring.length();++i)
				{
					if(Character.isLetter(mystring.charAt(i)))
					{
						word.append(mystring.charAt(i));
					}
					else
					{
						if(word.length()>1)
						{
							if(!map.containsKey(word.toString())) {
								map.put(word.toString(), 1);
							}
							else
							{
								map.put(word.toString(), map.get(word.toString())+1);
							}
							
						}
						word.setLength(0);
					}
				}
			}
			System.out.println(map);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}
	public static void main(String[] args) throws IOException{
		
		System.out.println("Laborator 1: ");
		   laborator1();
		   File file=new File("text1.txt");
	       TextSplit(file);
	}

}
