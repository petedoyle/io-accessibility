/*
 * Copyright (c) 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.io.accessibility.picasamodel;

import com.google.api.client.xml.XmlNamespaceDictionary;

/**
 * @author Yaniv Inbar
 */
public class Util {

	public static final XmlNamespaceDictionary NAMESPACE_DICTIONARY = new XmlNamespaceDictionary();
	static {
		NAMESPACE_DICTIONARY.set( "", "http://www.w3.org/2005/Atom" );
		NAMESPACE_DICTIONARY.set( "", "http://www.w3.org/2005/Atom" );
		NAMESPACE_DICTIONARY.set( "atom", "http://www.w3.org/2005/Atom" );
		NAMESPACE_DICTIONARY.set( "exif", "http://schemas.google.com/photos/exif/2007" );
		NAMESPACE_DICTIONARY.set( "gd", "http://schemas.google.com/g/2005" );
		NAMESPACE_DICTIONARY.set( "geo", "http://www.w3.org/2003/01/geo/wgs84_pos#" );
		NAMESPACE_DICTIONARY.set( "georss", "http://www.georss.org/georss" );
		NAMESPACE_DICTIONARY.set( "gml", "http://www.opengis.net/gml" );
		NAMESPACE_DICTIONARY.set( "gphoto", "http://schemas.google.com/photos/2007" );
		NAMESPACE_DICTIONARY.set( "media", "http://search.yahoo.com/mrss/" );
		NAMESPACE_DICTIONARY.set( "openSearch", "http://a9.com/-/spec/opensearch/1.1/" );
		NAMESPACE_DICTIONARY.set( "xml", "http://www.w3.org/XML/1998/namespace" );
	}

	private Util() {
	}
}