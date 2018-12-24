
Task

Write a simple web crawler service. This exercise tests your knowledge in application design, code quality and testing practices. The crawler should be limited to one domain. Given a starting URL,it should visit all pages within the domain, but not follow the links to external sites such as Google or Twitter.

The output should be a simple structured site map. 
This does not need to be a traditional XML sitemap - just some sort of output to reflect what your crawler has discovered), showing links to other pages under the same domain, links to external URLs and links to static content such as images for each respective page. Expose this functionality as a REST endpoint (example "GET {uri}/crawl")



Technology

Spring Boot 2.1

Java 1.8

JSoup



Build and Run

1.	Clone the project –  git $ clone https://github.com/ravipal27/WebCrawler.git
                                $ cd webcrawler/ 
                                $ mvn package
2.	Run

       $ java -jar target/web-crawler-1.0-SNAPSHOT.jar

3.	Pass the URL as a request parameter.
http://localhost:8090/crawler?url=http://wiprodigital.com


  
  Process:

1.	Using JSoup to parse the links by using JSoup Document : Document doc = Jsoup.connect(url).get();
2.	Using a[href] to get elements as links : Elements links = doc.select("a[href]");
3.	Using img[src] to get image elements from the JSoup connection : Elements imageLinks = doc.select("img[src]");
4.	Created a method which checks whether the child links are part of the given domain or not.
5.	After hitting the URL, a JSON response is produced which contains three different sets:
One for all the links present in the domain.
Second for all the images
And third one for all the external links.

 



Improvements :


With time :
1.	Can perform the three tasks asynchronously as they are independent of each other and also the user need not wait for all three tasks to complete.
2.	Add more functional tests.
3.	Better logging.

