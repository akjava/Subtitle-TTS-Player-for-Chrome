package com.akjava.gwt.subplayer.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Binder extends ClientBundle {
public static Binder INSTANCE=GWT.create(Binder.class);
	ImageResource loadanime();

}
