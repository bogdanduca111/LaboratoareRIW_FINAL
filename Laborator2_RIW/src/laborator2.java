import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;


public class laborator2 {
	public static Scanner input;
	
	
	public static boolean CheckIfText(File file)
	{
		boolean exist = false;
		if(file == null)
		{
			return false;
		}
		String nume = file.getName();
		int i = nume.lastIndexOf('.');
		String ext = i>0?nume.substring(i+1) : " ";
		if(ext.equals("txt"))
		{
			exist = true;
		}
		else
		{
			exist = false;
		}
		return exist;
	}
	
	
	
	public static Queue<File> getFile(File path)
	{
		Queue<File> fisiere =  new LinkedList<File>();
		Queue<File> directoare = new LinkedList<File>();
		directoare.add(path);
		
        
		
		while(!directoare.isEmpty())
		{
			File current = directoare.poll(); //returneaza primul element si il "sterge"
			
			File[] listaDir = current.listFiles();
			
			if(listaDir != null)
			{
				for(File file : listaDir)
				{
					if(file.isDirectory())
					{
						directoare.add(file);
						 //System.out.println("director");
					}
					else if(CheckIfText(file))
					{
						fisiere.add(file);
						//System.out.println("fisier");
					}
				}
			}
			
		}
		return fisiere;
			
	}
	public static boolean StopWords(String words) 
	{
		ArrayList<String> listaCuvinte=new ArrayList<String>();
	    try {
	    	FileReader in = new FileReader("stopwords.txt");
		    BufferedReader br = new BufferedReader(in);
	    	  String line = br.readLine();
	  	    while (line!=null) {
	  	       
	  	    	listaCuvinte.add(line);
	  	        line = br.readLine();
	  	    }
	    }catch(IOException e)
	    {
	    	e.printStackTrace();
	    }
		   for( String i : listaCuvinte)
		    {
		       if(words.equals(i))
		       {
		    		return true;
		       }	
		    }
	    //input.close();
		return false;
	}
	
	public static Map<String, Integer> TextSplitPorter(File filePath)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		FileInputStream inputStream = null;
		Porter porter=new Porter();
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(filePath);
			sc = new Scanner(inputStream, "UTF-8");
			StringBuilder word = new StringBuilder();

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				for (int i = 0; i < line.length(); ++i) {
					if (Character.isLetter(line.charAt(i))) {
						word.append(line.charAt(i));

					} else {
						if (((word.length() > 2))&&(!StopWords(word.toString()))) {
							if (!map.containsKey(porter.stripAffixes(word.toString()))) {
								map.put(porter.stripAffixes(word.toString()), 1);
							} else {
								map.put(porter.stripAffixes(word.toString()), map.get(porter.stripAffixes(word.toString())) + 1);
							}
						}
						word.setLength(0);
					}
					
					if ((i == (line.length() - 1)) && (word.length() > 1 ))
					{
						if(!StopWords(word.toString()))
						{
							if (!map.containsKey(word.toString())) {
								map.put(porter.stripAffixes(word.toString()), 1);
							} else {
								map.put(porter.stripAffixes(word.toString()), map.get(porter.stripAffixes(word.toString())) + 1);
							}
						}
						word.setLength(0);
					}
				}
			}
			System.out.println(map);
			inputStream.close();
			sc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public static void main(String[] args) throws IOException{
		System.out.println("Laborator 2: ");
		
	       File p = new File("D:\\workspace\\Laborator2_RIW\\Folder1");
	       Queue<File> fisiere=new LinkedList<File>();
		   fisiere=getFile(p);
		   for(File f : fisiere)	
		   {
				System.out.println(f);
				TextSplitPorter(f);
		   }

	}

}
