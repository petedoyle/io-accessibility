package com.google.io.accessibility.data;

import java.io.Serializable;

import com.google.api.client.util.Key;

public class Video implements Serializable {
	@Key public String title;
	@Key public String description;
	@Key public Player player;
}
