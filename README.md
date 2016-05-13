# webcrawler

this program requires web url and it would fetch all links to images/text files etc in same or sub domain. 
this is timeout for 15 secs to read given url. 
can extend this code to read from more urls from extacted url and limit that with number of urls to prevent this to go in infinite read. 


How to run build it ? 

```
-  >cd web-crawler-app
  > mvn clean package 
```   

How to run it ? 

from target directory, JAR can be found i.e. web-crawler-app-0.0.1-SNAPSHOT.jar and run the command with url.

```
- > java -jar web-crawler-app-0.0.1-SNAPSHOT.jar http://indianexpress.com
```
