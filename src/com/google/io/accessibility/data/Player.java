package com.google.io.accessibility.data;

import java.io.Serializable;

import com.google.api.client.util.Key;

public class Player implements Serializable {
	@Key("default")
	public String defaultUrl;
}