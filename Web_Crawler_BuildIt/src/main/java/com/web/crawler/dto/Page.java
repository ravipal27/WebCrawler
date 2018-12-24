package com.web.crawler.dto;

import java.util.Set;

public class Page {
	private final String url;
	private Set<String> domaiLinks;
	private Set<String> images;
	private Set<String> externalLinks;

	public Page(String url) {
		this.url = url;
	}

	public Set<String> getDomaiLinks() {
		return domaiLinks;
	}

	public void setDomaiLinks(Set<String> domaiLinks) {
		this.domaiLinks = domaiLinks;
	}

	public Set<String> getImages() {
		return images;
	}

	public void setImages(Set<String> images) {
		this.images = images;
	}

	public Set<String> getExternalLinks() {
		return externalLinks;
	}

	public void setExternalLinks(Set<String> externalLinks) {
		this.externalLinks = externalLinks;
	}

	public String getUrl() {
		return url;
	}

}
