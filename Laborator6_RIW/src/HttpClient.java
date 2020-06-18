import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class HttpClient {

	private String userag;
	// user agent setare CLIENT RIW
    public HttpClient(String user_agent) {
		
    	this.userag = user_agent;
	}
    public String getResource(String resName, String domainName, String host, int port) throws IOException
    {
       
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append("GET " + resName + " HTTP/1.1\r\n");
        requestBuilder.append("Host: " + domainName + "\r\n");
        requestBuilder.append("Connection: close\r\n");
        requestBuilder.append("\r\n");
        
        String httpRequest = requestBuilder.toString();
        System.out.println(httpRequest);
       
        
        
        //trimitere cerere
        Socket s = new Socket(host, port);
        
        DataOutputStream dataOutputBuffer = new DataOutputStream(s.getOutputStream()); 
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(s.getInputStream())); 
        dataOutputBuffer.writeBytes(httpRequest);
 
        //raspuns
        String response;
        response = bufferReader.readLine();
        System.out.println("Raspuns: "+ response+"\n");
        
        //construire cod de stare
        StringBuilder str=new StringBuilder();
        int i=0;
        while(response.charAt(i)!=' ' )
        {
        	i++;	
        }
        str.append(response.charAt(i+1));
        str.append(response.charAt(i+2));
        str.append(response.charAt(i+3));
        
        //System.out.println(str.toString());
        StringBuilder createHTMLPage = null;
        boolean rasp_OK = false;
        
        if(str.toString().equals("200"))
        {
        	rasp_OK=true;
        	
        }
        else if(str.toString().equals("301"))
        {
        	rasp_OK=false;
        }
        if(rasp_OK==false)
        {
        	System.out.println("Cod de raspuns:301");
        }
        
      
        while ((response = bufferReader.readLine()) != null)
        {
            if (response.equals("")) 
            {
            	
               break;
            	
            }
            
            System.out.println(response);
        }
        
        if (rasp_OK) 
        {
            createHTMLPage = new StringBuilder();
            while ((response = bufferReader.readLine()) != null)
            {
                createHTMLPage.append(response + System.lineSeparator());
            }	        
        }

      
        s.close();
     
        return createHTMLPage.toString();
    }
}
