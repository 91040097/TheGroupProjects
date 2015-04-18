package hackRU;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class DownloadPage {

    public static void main(String[] args) throws IOException {

        // Make a URL to the web page
        //URL url = new URL("http://colleges.usnews.rankingsandreviews.com/best-colleges/rankings/national-universities?int=9ff208");
        
    	URL url = new URL("http://www.powerscore.com/sat/help/average_test_scores.cfm");
    	//URL url = new URL("http://njmonthly.com/articles/towns-schools/top-schools-alphabetical-list");
        // Get the input stream through URL Connection
        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();

        // Once you have the Input Stream, it's just plain old Java IO stuff.

        // For this case, since you are interested in getting plain-text web page
        // I'll use a reader and output the text content to System.out.

        // For binary content, it's better to directly read the bytes from stream and write
        // to the target file.


        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;

        String c = "";
        
        boolean done = false;
        
        HashMap<String, ArrayList<String>> x = new HashMap<String, ArrayList<String>>();
        
        // read each line and write to System.out
        while ((line = br.readLine()) != null) {
            //System.out.println(line);
            
            if(line.contains("<td>"))
            {
            	//int start = line.indexOf("<b>"), end = line.indexOf("</");
            	
            	String s = "";
            	
            	boolean start = false;
            	
            	for(int i = 0;i <line.length();i++)
            	{
            		if(line.charAt(i) == '<')
            			start = true;
            		else if(line.charAt(i) == '>')
            			start = false;
            		else if(!(start))
            			s += line.charAt(i);
            			
            	}
            	
            	//String s = chop(line.substring(start + 3, end));
            	
            	s = s.trim();
            	
            	double[] x3 = isDouble(s);
            	
            	System.out.println(s);
            	if(x3 != null)
            		System.out.println(x3[0] + " " + x3[1]);
            }
            
           /* if(line.contains("<td>"))
            {
            	if(line.contains("<b>") && (line.contains("</b>") || line.contains("</br>")))
            	{
            		int start = line.indexOf("<b>"), end = line.indexOf("</");
            	
            		String s = chop(line.substring(start + 3, end));
            	
            		System.out.println(s);
            	}
            }*/
        	
        	/*if(line.contains("<p>"))
        	{
        		done = true;
        	}
        	
        	if(done)
        	{	
        		c += line;
        		
        		if(c.contains("<p>"))
        			c = c.replace("<p>", "");
        	}
        	
        	if(done && line.contains("/p>"))
        	{
        		int i = line.indexOf("/p>");
        		
        		c = c.substring(0, i);
        		
        		c = c.trim();
        		
        		if(c.contains("</p>"))
        			c = c.replace("</p>", "");
        		if(c.contains("&mdash;"))
        			c = c.replace("&mdash;", "-");
        		
        		System.out.println(c);
        		
        		c = "";
        		
        		done = false;
        	}*/
        }
    }
    
    public static double[] isDouble(String s)
    {
    	double[] nums = new double[2];
    	
    	String s1 = "", s2 = "";
    	
    	if(!(s.contains("-")))
    		return null;
    	else
    	{
    		int i = s.indexOf("-");
    		
    		if(i < 0)
    			return null;
    		
    		s1 = s.substring(0, i);
    		s2 = s.substring(i+1, s.length());
    	}
    	try
    	{
    		nums[0] = Double.parseDouble(s1);
    		nums[1] = Double.parseDouble(s2);
    	}catch(Exception E)
    	{
    		return null;
    	}
    	
    	return nums;
    }
    
    public static String chop(String s)
    {
    	for(int i = 0;i < s.length();i++)
    	{
    		if((i + 1) < s.length())
    		{
    			if(s.charAt(i) == ' ' && s.charAt(i + 1) == ' ')
    				s = s.substring(0, i) + s.substring(i + 1, s.length());
    		}
    	}
    	
    	return s;
    }
}