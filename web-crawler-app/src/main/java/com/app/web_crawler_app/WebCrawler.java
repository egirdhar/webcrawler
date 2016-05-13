package com.app.web_crawler_app;


/**
 * @author girdhar_katiyar
 *  this code will crawl for url links for images, text etc. in same domain/ sub domain  in given webpage url 
 */
public class WebCrawler
{
	public static void main(final String[] args)
	{
	  if (args.length != 1)
	  {
	     System.err.println("usage: java WebCrawler url");
	     return;
	  }
       
	  PerformCrawl crawl = new PerformCrawl(args[0]);
	  crawl.perform();
	}
}	
