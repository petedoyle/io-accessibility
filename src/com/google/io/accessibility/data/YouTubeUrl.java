package com.google.io.accessibility.data;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

public class YouTubeUrl extends GenericUrl {
	@Key public final String alt = "jsonc";
	@Key public String author;
	@Key public Boolean caption;
	@Key public String q; // Query string
	
	@Key("max-results")
	public Integer maxResults;

	public YouTubeUrl(String url) {
		super( url );
	}
}