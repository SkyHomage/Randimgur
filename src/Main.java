import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



public class Main {

	static boolean censored = false;
	
	public static void main(String[] args){
		System.out.println("Working...");
		
		System.out.println("Trying to find some random urls bro.");
		ArrayList<String> workingUrls = CreateUrls();
		
		System.out.println("Creating that sexy html.");
		CreateHTML(workingUrls);
	
		System.out.println("Done! :)");
	}
	
	public static ArrayList<String> CreateUrls(){
		String test = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		ArrayList<String> workingUrls = new ArrayList<String>();
		String url = "http://i.imgur.com/";
		String sequence = null;
		int numberOfUrls = 30;
		
		while(numberOfUrls > 0)
		{
			try{
				
				sequence =  MakeSequence(test, 5);
				Connection.Response response= Jsoup.connect(url+sequence).userAgent("Chrome").execute();
				int statuscode = response.statusCode(); 
				
				if(statuscode == 200){
					numberOfUrls--;
					workingUrls.add(url+sequence);
				}
				
			}
			catch(IOException e){
			}
		
			
		
		}
		return workingUrls;
	}
	
	public static void CreateHTML(ArrayList<String> workingUrls){
		Writer writer = null;
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("someurls.html"), "utf-8"));
		    writer.write("<!DOCTYPE html> <html> <body> ");
		    for(String link : workingUrls){
		    	if(censored)
		    		writer.write("\n <img src=\""+link+".gif\" height=\"240\" width=\"240\" "
		    				+ "style=\"visibility: hidden;\"> " + link);
		    	else
		    		writer.write("\n <img src=\""+link+".gif\" height=\"240\" width=\"240\">");
		    	
		    	
		    }
		    writer.write("\n</body> </html>");
		} catch (IOException ex) {
		  // report
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}
	}
	
	public static String MakeSequence(String validChars, Integer length){
		String str = "";
		int len = 0;
		while(len < length)
		{
			str = str+validChars.charAt(randInt(0, validChars.length()-1));
			len++;
		}
		
		
		return str;
	}
	
	private static int randInt(int min, int max) {

	   
	    Random rand = new Random();

	    
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}



