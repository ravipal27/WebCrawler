package com.web.crawler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.crawler.dto.Page;
import com.web.crawler.service.CrawlerService;

@RestController
public class WebCrawlerController {

	private static final Logger logger = LoggerFactory.getLogger(WebCrawlerController.class);

	@GetMapping("/check")
	public String webChecker() {
		logger.info("web checker called");
		return "WEB-CHECKER";
	}

	@GetMapping("/crawler")
	public Page getLinks(@RequestParam(value = "url") String url) throws Exception {

		CrawlerService recursion = new CrawlerService(url);
		logger.info("Crawling starts with URL  : " + url);
		Page page = recursion.get_links(url);

		logger.info("Crawling ends for URL  : " + url);

		return page;

	}
}
