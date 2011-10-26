package com.akjava.gwt.subplayer.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class PlayerWidget extends Composite {

	private static PlayerWidgetUiBinder uiBinder = GWT.create(PlayerWidgetUiBinder.class);

	interface PlayerWidgetUiBinder extends UiBinder<Widget, PlayerWidget> {
	}

	public PlayerWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private int subIndex;
	private int subLength;
public int getSubLength() {
		return subLength;
	}

	public void setSubLength(int subLength) {
		this.subLength = subLength;
	}

public int getSubIndex() {
		return subIndex;
	}

	public void setSubIndex(int subIndex) {
		this.subIndex = subIndex;
		index.setText((subIndex+1)+"/"+subLength);
	}

@UiField HorizontalPanel controler;
@UiField Label index;
@UiField Button first,prev,play,stop,next;

	
public HorizontalPanel getControler(){
return controler;
}

public Label getIndex(){
return index;
}

public Button getFirst(){
return first;
}

public Button getPrev(){
return prev;
}

public Button getPlay(){
return play;
}

public Button getStop(){
return stop;
}

public Button getNext(){
return next;
}


	
@UiHandler("first")
void clickFirst(ClickEvent e) {

}
@UiHandler("prev")
void clickPrev(ClickEvent e) {

}
@UiHandler("play")
void clickPlay(ClickEvent e) {

}
@UiHandler("stop")
void clickStop(ClickEvent e) {

}
@UiHandler("next")
void clickNext(ClickEvent e) {

}


}
