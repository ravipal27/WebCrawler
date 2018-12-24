package com.web.crawler.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.web.crawler.controller.WebCrawlerController;
import com.web.crawler.dto.Page;

public class CrawlerService {

	private static final Logger logger = LoggerFactory.getLogger(CrawlerService.class);
	public static Set<String> uniqueURL = new HashSet<String>();
	public static Set<String> uniqueImageSrc = new HashSet<>();
	public static Set<String> externalLinksSrc = new HashSet<>();
	public URL parentURL;
	Map<String, Page> siteMap = new HashMap<>();

	public CrawlerService(String url) {
		try {
			this.parentURL = new URL(url);
		} catch (MalformedURLException e) {
			logger.error("URL not correct");
		}
	}

	public Page get_links(String url) {
		Page page = new Page(url);
		try {
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("a[href]");
			if (links.isEmpty())
				return page;
			links.parallelStream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
				boolean add = false;
				try {
					if (isSameDomain(parentURL, this_url)) {
						add = uniqueURL.add(this_url);
					}

					if (add && isSameDomain(parentURL, this_url)) {
						get_links(this_url);
					}

				} catch (MalformedURLException e) {
					logger.error("URL not correct");
				}
			});

			// Getting Image links
			Elements imageLinks = doc.select("img[src]");
			if (imageLinks.isEmpty())
				return page;

			imageLinks.parallelStream().map((link) -> link.attr("abs:src")).forEachOrdered((this_url) -> {
				boolean add = false;
				try {
					if (isSameDomain(parentURL, this_url)) {
						add = uniqueImageSrc.add(this_url);
					}

					if (add && isSameDomain(parentURL, this_url)) {
						get_links(this_url);
					}

				} catch (MalformedURLException e) {
					logger.error("URL not correct");
				}
			});

			// Getting External Links
            Elements externalLinks = doc.select("a[href]");
			if (externalLinks.isEmpty())
				return page;
			externalLinks.parallelStream().map((link) -> link.attr("abs:href")).forEachOrdered((this_url) -> {
				boolean add = false;
				try {
					if (!isSameDomain(parentURL, this_url)) {
						add = externalLinksSrc.add(this_url);
					}

					if (add && isSameDomain(parentURL, this_url)) {
						get_links(this_url);
					}

				} catch (MalformedURLException e) {
					logger.error("URL not correct");
				}
			});

			page.setDomaiLinks(uniqueURL);
			page.setImages(uniqueImageSrc);
			page.setExternalLinks(externalLinksSrc);

		} catch (IOException e) {
			logger.error("IO Exception");
		}
		return page;
	}

	boolean isSameDomain(URL home, String this_url) throws MalformedURLException {
		URL currentURL = new URL(this_url);
		if (currentURL.getHost().endsWith(home.getHost())) {
			return true;
		}
		return false;
	}
}
