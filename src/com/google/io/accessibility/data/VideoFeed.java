package com.google.io.accessibility.data;

import java.io.Serializable;
import java.util.List;

import com.google.api.client.util.Key;

public class VideoFeed implements Serializable {
	@Key public List<Video> items;
}