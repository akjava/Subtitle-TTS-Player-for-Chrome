/*
 * Copyright (C) 2011 aki@akjava.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

	private SubContainer container;
	public PlayerWidget(SubContainer container) {
		initWidget(uiBinder.createAndBindUi(this));
		this.container=container;
		updateButtons();
	}
	
	private int subIndex;
	private int subLength;
private boolean autoPlaying;	
public boolean isAutoPlaying() {
	return autoPlaying;
}

public void setAutoPlaying(boolean autoPlaying) {
	this.autoPlaying = autoPlaying;
	updateButtons();
}

public int getSubLength() {
		return subLength;
	}

	public void setSubLength(int subLength) {
		this.subLength = subLength;
		updateButtons();
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
@UiField Button first,prev,play,stop,next,auto;

	
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


public boolean hasNext(){
	return subIndex<subLength-1;
}
public void doNext(){
	int tmp=subIndex+1;
	if(tmp>=subLength){
		tmp=subLength-1;	
	}
	subIndex=tmp;
	container.moveTo(subIndex);
	updateButtons();
}

private void doFirst(){
	subIndex=0;
	container.moveTo(subIndex);
	updateButtons();	
}

@UiHandler("first")
void clickFirst(ClickEvent e) {
	doFirst();
}
@UiHandler("prev")
void clickPrev(ClickEvent e) {
	int tmp=subIndex-1;
	if(tmp<0){
		tmp=0;
	}
	subIndex=tmp;
	container.moveTo(subIndex);
	updateButtons();
}
@UiHandler("play")
void clickPlay(ClickEvent e) {
container.play(subIndex);
}
@UiHandler("stop")
void clickStop(ClickEvent e) {
container.stop();
}
@UiHandler("next")
void clickNext(ClickEvent e) {
doNext();
}
@UiHandler("auto")
void clickAuto(ClickEvent e) {
setAutoPlaying(true);
container.autoPlay(subIndex);
}

public void endAutoPlay(){
	setAutoPlaying(false);
	//doFirst(); //there are first button and sometime need to check last talking words
}

private void updateButtons(){
	if(!autoPlaying){
	if(subLength==0 || subIndex>=subLength-1){
		next.setEnabled(false);
	}else{
		next.setEnabled(true);
	}
	if(subLength==0 ||subIndex==0){
		prev.setEnabled(false);
	}else{
		prev.setEnabled(true);
	}
	
	if(subLength==0){
		first.setEnabled(false);
		play.setEnabled(false);
		stop.setEnabled(false);
		auto.setEnabled(false);
	}else{
		first.setEnabled(true);
		play.setEnabled(true);
		stop.setEnabled(true);
		auto.setEnabled(true);
	}
	}else{
		first.setEnabled(false);
		prev.setEnabled(false);
		next.setEnabled(false);
		play.setEnabled(false);
		stop.setEnabled(true);
		auto.setEnabled(false);	
	}
}


}
