package hackRU;
//package hackRU;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;


public class CollegeAcceptanceRates {

	public static void main(String[] args) throws IOException {

		// Make a URL to the web page
		//URL url = new URL("http://colleges.usnews.rankingsandreviews.com/best-colleges/rankings/national-universities?int=9ff208");

		URL url = new URL("http://www.ivywise.com/admission_statistics.html"); 
		//URL url = new URL("http://njmonthly.com/articles/towns-schools/top-schools-alphabetical-list");
		// Get the input stream through URL Connection
		URLConnection con = url.openConnection();
		InputStream is = con.getInputStream();

		// Once you have the Input Stream, it's just plain old Java IO stuff.

		// For this case, since you are interested in getting plain-text web page
		// I'll use a reader and output the text content to System.out.

		// For binary content, it's better to directly read the bytes from stream and write
		// to the target file.
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = null;
		int count = 0;
		// read each line and write to System.out
		while ((line = br.readLine()) != null) {

			if(line.contains("<td>"))
			{
				//int start = line.indexOf("<b>"), end = line.indexOf("</");

				String s = "";

				boolean start = false;

				for(int i = 0;i <line.length();i++){
					if(line.charAt(i) == '<'){
						start = true; 
					}
					else if(line.charAt(i) == '>')
						start = false;
					else if(!(start))
						s += line.charAt(i);
				}

				//String s = chop(line.substring(start + 3, end));
				
				double[] x3 = isDouble(s);
				//System.out.println(x3);
				s = s.trim().replaceAll("%", "");
				s = s.replaceAll("School", "");
				s = s.replaceAll("2014", "");
				s = s.replaceAll("2013", "");
				s = s.replaceAll("2012", "");
				s = s.replaceAll("2005", "");

				//if(s.length() > 0){
					//System.out.println(s);
				//}
				
				//int count = 0;
				
				if(x3 != null){
					if(count == 1){
						System.out.println(x3[0]);
					}
					count++;
				}
				
					//counter++;
				else{
					if(s.length()>0 && !(s.contains("TBA"))){
						System.out.println(s);
						count =1;
					}
				}
			}
		}
	}

	public static double[] isDouble(String s)
	{
		double[] nums = new double[1];

		String s1 = "";

		if(!(s.contains("%")))
			return null;

		else
		{
			
			int count = 0;
			for(int i = 0; i < s.length(); i++){
				if(s.charAt(i) == '%'){
					s1 = s.substring(0, i);
					break;
				}
			}
			
			//s2 = s.substring(i+1, s.length());
		}
		try
		{
			nums[0] = Double.parseDouble(s1);
			//nums[1] = Double.parseDouble(s2);
		}
		catch(Exception E){
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