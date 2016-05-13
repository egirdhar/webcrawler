package com.app.web_crawler_app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author girdhar_katiyar
 *  this code would hit the web url, read the web data and then parse the data for links to images and text etc . in same domain 
 */

public class PerformCrawl {
	
	// detect http/https urls 
	public static Pattern PATTERN= Pattern.compile("http([^\\s])*");
	
	private ExecutorService executorService;
	
	// given web url
	private String webUrl;
	
	
	public PerformCrawl(ExecutorService executorService ) {
		 this.executorService = executorService;
	}
	
	
	public PerformCrawl(String webUrl) {		
		this(Executors.newSingleThreadExecutor());
		this.webUrl = webUrl;
	}
	
	
	/**
	 * start the java executor and run the job to read the web url   
	 */
	void perform(){
		
		Future<List<String>> future = executorService.submit(readWebJob());
		try
		{
			List<String> urls = future.get(15, TimeUnit.SECONDS);
		    for (String url: urls)
		        System.out.println(url);
		  	}
		catch (ExecutionException ee)
		{
		     System.err.println("Exception while reading web url : "+ee.getMessage());
		}
		catch (InterruptedException e) 
		{
		    System.err.println("URL not responding");
		}
		catch (TimeoutException eite){
			  System.err.println("Timeout (waited for 15 seconds) - URL not responding");
		
		}
		  executorService.shutdown();
	 }

	
	/**
	 *  the job to read the web url   
	*/
	Callable<List<String>> readWebJob()
	{	
		Callable<List<String>> callable = new Callable<List<String>>()
		{
			public List<String> call() throws IOException, MalformedURLException
			{
				List<String> urls = new ArrayList<String>();
				readWeb(urls);
			    return urls;	
			}
		};
		return callable;
	}
	
	/**
	 *  make a http url connection and return the required urls  
	*/
	List<String> readWeb(List<String> urls) throws IOException, MalformedURLException
	{		     
		 URL url = new URL(webUrl);                   
 		 
		// System.getProperties().put("proxyHost", "ngproxy.persistent.co.in");
 		// System.getProperties().put("proxyPort", "8080");
 		 
         HttpURLConnection connection =(HttpURLConnection) url.openConnection();
         
         InputStreamReader reader  = new InputStreamReader(connection.getInputStream());
         BufferedReader bufferedReader =new BufferedReader(reader);
         String webDataLine;
                           
         while ((webDataLine = bufferedReader.readLine()) != null)
         { 
        	 
        	 Matcher matcher= PATTERN.matcher(webDataLine);
      	   
        	 while(matcher.find()){
        		 webDataLine=parseWebDataLine(webDataLine);
        		 if (webDataLine.contains(url.getHost()))
        			 urls.add(webDataLine);
        	 }
         }	
        
		  	
         return urls;
	}
	
	/**
	 *  parse the http| https urls    
	*/
    String parseWebDataLine( String webDataline)
    {
    	webDataline=webDataline.substring(webDataline.indexOf("http"));
    	webDataline=webDataline.split("\">")[0];
    	 
    	return webDataline;
    }	
 }   	 
     
