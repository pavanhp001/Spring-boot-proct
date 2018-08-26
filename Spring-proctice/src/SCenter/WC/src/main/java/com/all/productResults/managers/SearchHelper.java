package com.A.productResults.managers;

import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.tools.ant.types.resources.comparators.Date;
import com.A.productResults.managers.ProductResults;
import com.A.productResults.vo.ProductSearchIface;

public class SearchHelper {
	static final Logger logger = Logger.getLogger(SearchHelper.class);

	public static void SearchProducts(ProductSearchIface searchResults)
			throws InterruptedException {
		logger.info("Fetch Results from Product Results Manager start :: "
				+ new Date());
		ProductResults.search(searchResults);
		logger.info("Retrieved Products from V :: " + new Date()
				+ " , Size :: " + searchResults.getTotalCount());
	}

	public static void SaveSearchCriteria(HttpSession session,
			SearchResults criteria) {
		session.setAttribute("productResultManager", criteria);
	}

	public static SearchResults getSearchResults(HttpSession session) {
		return (SearchResults) session.getAttribute("productResultManager");
	}
}
