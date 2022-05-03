import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/*
 * AUTHOR: Mason Holter
 * FILE: WikiRacer.java
 * PURPOSE: This program defines the WikiRacer function, which finds the quickest "path"
 * between to wikipedia page identififiers given at the command line. This program also 
 * utilizes the WikiScraper and MaxPQ classes.
 */

public class WikiRacer {
	
	public static void main(String[] args) {
		List<String> ladder = findWikiLadder(args[0], args[1]);
		System.out.println(ladder);
	}

    /*
     * Forms WikiLinks set for start page and end page. Checks if end
     * page can be found at start. If not, runs discoverLoop
     * 
     * @param start, name of starting Wikipedia page
     * @param end, name of ending Wikipedia page
     * 
     * @return path, List of Wikipedia page names leading from start
     * to end page
     */
	private static List<String> findWikiLadder(String start, String end) {
		Set<String> startPageLinks = WikiScraper.findWikiLinks(start);
		Set<String> endPageLinks = WikiScraper.findWikiLinks(end);
		if (startPageLinks.contains(end)) { 
			System.out.println("Found at start");
			return Arrays.asList(new String[] {start, end});
		} else { 
			List<String> path = discoveryLoop(start, end, endPageLinks);
			return path;
		}
	}
	
    /*
     * Using the max priority queue, this function iterates through 
     * each page with the most amount of references to the end page links.
     * From each page, it finds pages with links on that page, adding them to the queue.
     * Each page visited gets added to the 'path' List, which compiles the 
     * path chosen by the program.
     * 
     * @param start, name of starting Wikipedia page
     * @param end, name of ending Wikipedia page
     * @param endPage, Set containing strings of all links in end Wikipedia page.
     * 
     * @return path, path of pages chosen by program
     */
	private static List<String> discoveryLoop(String start, String end, Set<String> endPage) { 
		MaxPQ queue = new MaxPQ();
		queue.enqueue(0, start);
		
		List<String> path = new ArrayList<String>(); 
		while (!queue.isEmpty()) { 
			String currPage = queue.dequeue();
			path.add(currPage);
			
			Set<String> currPageLinks = WikiScraper.findWikiLinks(currPage);
			
			if (currPageLinks.contains(end)) { 
				return path;
			}
			
			// Forms intersection of "curr" page links and "end" page links
			currPageLinks.retainAll(endPage);
			
			List<String> pageLinksIntersect = new ArrayList<String>(currPageLinks);
			
			if (currPageLinks.isEmpty()) { 
				continue;
			}
			
			for (int i = 0; i < currPageLinks.size(); i++) { 
				String currLink = pageLinksIntersect.get(i);
				Set<String> currLinkLinks = WikiScraper.findWikiLinks(currLink);
				if (currLinkLinks.contains(end)) { 
					path.add(currLink);
					path.add(end);
					return path;
				}
        
				currLinkLinks.retainAll(endPage);
				queue.enqueue(currLinkLinks.size(), currLink);
			}
		}
		return path;
	}
}

