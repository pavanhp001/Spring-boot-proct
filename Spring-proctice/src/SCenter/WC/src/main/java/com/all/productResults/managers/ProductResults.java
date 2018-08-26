package com.A.productResults.managers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.A.productResults.vo.ProductSearchIface;
import com.A.ui.service.config.ConfigRepo;

public class ProductResults  {
	
	private  ExecutorService pool ;

	private static final Logger logger = Logger.getLogger(ProductResults.class);

	private static ProductResults productResults;
	
    private ProductResults(int noOfThreads)
    {
    	pool = Executors.newFixedThreadPool(noOfThreads);
    }

    public void execute(ProductResultsManager r) {
    	logger.debug("ProductResults_execute_search");
    	pool.execute(r);
    }
	public static void search(ProductSearchIface prdSrcVO) {
		ProductResultsManager r = new ProductResultsManager(prdSrcVO);	
		ProductResults queue = ProductResults.getInstance();
		queue.execute(r);
	}

	private static ProductResults getInstance() {
		if (productResults == null) {
			int i = ConfigRepo.getInt("*.thread_pool_size") == 0 ? 500 : ConfigRepo.getInt("*.thread_pool_size");
			productResults = new ProductResults(i);
		}
		return productResults;
	}
}
