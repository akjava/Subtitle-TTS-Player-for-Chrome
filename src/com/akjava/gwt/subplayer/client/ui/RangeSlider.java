package com.akjava.gwt.subplayer.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FocusWidget;

public class RangeSlider extends FocusWidget{

	public RangeSlider(int min,int max,int current){
		super(RangeElement.createRangeElement(Document.get(),min,max,current));
	}
	 protected RangeElement getRangeElement() {
		    return getElement().cast();
		  }
	 public int getValue(){
		 return Integer.parseInt(getRangeElement().getValue());
	 }
	 public void setValue(int value){
		 getRangeElement().setValue(value);
	 }
	 
	 public static class RangeElement extends Element{
		 protected RangeElement(){}
		  public final native String getValue() /*-{
		     return this.value;
		   }-*/;
		  public final native void setValue(int value) /*-{
		     this.value = value;
		   }-*/;
		  public final native int getMin() /*-{
		     return this.min;
		   }-*/;
		  public final native void setMin(int value) /*-{
		     this.min = value;
		   }-*/;
		  public final native int getMax() /*-{
		     return this.max;
		   }-*/;
		  public final native void setMax(int value) /*-{
		     this.max = value;
		   }-*/;
		  
		  public static final native RangeElement createRangeElement(Document doc,int min,int max,int value) /*-{
		    var e = doc.createElement("INPUT");
		    e.type = 'range';
		    e.min=min;
		    e.max=max;
		    e.value=value;
		    return e;
		  }-*/;
	}
}
