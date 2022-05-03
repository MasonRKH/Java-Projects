import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * AUTHORS: Mason Holter/Tyler Conklin
 * FILE: WikiScraper.java
 * PURPOSE: This program defines the WikiScraper function, which compiles all
 * hyperlinked references to other Wikipedia pages, given a name of a valid
 * Wikipedia page. 
 * 
 */
public class WikiScraper {
	
	public static Map<String, Set<String>> discovered = new HashMap<>();
	
    /*
     * Compiles HTML code from supplied string with fetchHTML, then creates
     * Set of referenced Wikipedia page names using ScrapeHTML.
     * Memoization implemented using a HashMap storing pages that 
     * have already been visited, mapped to their links Set.
     * 
     * @param link, name of Wikipedia page
     * 
     * @return links, Set of Wikipedia page names referenced 
     */
	public static Set<String> findWikiLinks(String link) {
		if(discovered.containsKey(link)) {
			return discovered.get(link);
		} else { 
			String html = fetchHTML(link);
			Set<String> links = scrapeHTML(html);
			discovered.put(link,  links);
			return links;
		}
	}
	
    /*
     * Compiles Wikipedia page HTML information by utilizing
     * the openStrema function, with the site data being 
     * receieved by InputStream. It is then compiled with a 
     * StringBuffer loop.
     * 
     * @param link, name of Wikipedia page
     * 
     * @return buffer, HTML code received from Wiki page.
     */
	private static String fetchHTML(String link) {
		StringBuffer buffer = null;
		try {
			URL url = new URL(getURL(link));
			InputStream is = url.openStream();
			int ptr = 0;
			buffer = new StringBuffer();
			while ((ptr = is.read()) != -1) {
			    buffer.append((char)ptr);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return buffer.toString();
	}
	
    /*
     * Creates Wikipedia link by adding page name to the correct
     * Wikipedia.org page link header.
     * 
     * @param link, name of Wikipedia page
     * 
     * @return complete link
     */
	private static String getURL(String link) {
		return "https://en.wikipedia.org/wiki/" + link;
	}
	
    /*
     * Searches for the pattern "<a href=\"/wiki/", which, in correct 
     * circumstances, indicates a linked reference to another Wikipedia 
     * page. The name of this page is added to a Set, which is returned
     * after each link has been found.
     * 
     * @param html, HTML data from Wikipedia page
     * 
     * @return close, Wikipedia page names referenced in HTML data
     */
	private static Set<String> scrapeHTML(String html) {
		Set<String> close = new HashSet<String>();
		Pattern pattern = Pattern.compile("<a href=\"/wiki/", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(html);

		while (matcher.find()) {
	        String news = html.substring(matcher.end(), (html.substring(matcher.end()).indexOf("\""))+matcher.end());
	        if (!news.contains(":") && !news.contains("#") && !news.contains("title=")) { 
	        	close.add(news);
	        }
		}
		return close;
	}	
}

