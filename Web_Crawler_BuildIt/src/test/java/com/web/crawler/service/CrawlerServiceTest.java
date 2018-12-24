package com.web.crawler.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.junit.Test;

import com.web.crawler.dto.Page;

public class CrawlerServiceTest {

	@Test
	public void isSameDomain() throws MalformedURLException {
		URL home = null;
		CrawlerService crawler = new CrawlerService("http://wiprodigital.com");
		try {
			home = new URL("http://wiprodigital.com");
			assertTrue(crawler.isSameDomain(home, "http://wiprodigital.com/who-we-are/"));
			assertTrue(crawler.isSameDomain(home, "http://wiprodigital.com/"));
			assertTrue(crawler.isSameDomain(home, "http://wiprodigital.com/what-we-do/"));
			assertFalse(crawler.isSameDomain(home, "http://www.google.com"));
			assertFalse(crawler.isSameDomain(home, "http://www.facebook.com/"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void domainLinks() {
	String url = "https://www.wiprodigital.com";
	CrawlerService service = new CrawlerService(url);
	Page page = service.get_links(url);
	assertNotNull(page);
	Set<String> domainList = page.getDomaiLinks();
	assertNotNull(domainList);
	for(String s : domainList) {
		assertTrue(s.contains("www.wiprodigital.com"));
		assertFalse(s.contains("www.facebook."));
	}
	}
	
	@Test
	public void imageLinks() {
	String url = "https://www.wiprodigital.com";
	CrawlerService service = new CrawlerService(url);
	Page page = service.get_links(url);
	assertNotNull(page);
	Set<String> imageList = page.getImages();
	assertNotNull(imageList);
	for(String s : imageList) {
		assertTrue(s.contains(".png") || s.contains(".jpg"));
	}
	}
	
	@Test
	public void externalinks() {
	String url = "https://www.wiprodigital.com";
	CrawlerService service = new CrawlerService(url);
	Page page = service.get_links(url);
	assertNotNull(page);
	Set<String> externalList = page.getExternalLinks();
	assertNotNull(externalList);
	for(String s : externalList) {
		assertFalse(s.contains("www.wiprodigital.com"));
		assertTrue(!s.contains("www.wiprodigital.com"));
	}
	}

}
