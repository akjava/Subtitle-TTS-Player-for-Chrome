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

import com.google.gwt.storage.client.Storage;

/*
 * TODO need something auto made
 */
public class SubPlayerPreference {
private Storage storage;

public static final String KEY_VOICE_NAME="voice_name";
public static final String KEY_VOICE_RATE="voice_rate";
public static final String KEY_VOICE_PITCH="voice_pitch";
public static final String KEY_CURRENT_SRT="current_srt";
public static final String KEY_SRT_INDEX="srt_index";
private String voiceName;
private double voiceRate;
private double voicePitch;

private String srtText;
private int srtSelectIndex;
public Storage getStorage() {
	return storage;
}



public String getVoiceName() {
	return voiceName;
}

public void setVoiceName(String voiceName) {
	this.voiceName = voiceName;
	storage.setItem(KEY_VOICE_NAME, voiceName);
}

public double getVoiceRate() {
	return voiceRate;
}

public void setVoiceRate(double voiceRate) {
	this.voiceRate = voiceRate;
	storage.setItem(KEY_VOICE_RATE,""+ voiceRate);
}

public double getVoicePitch() {
	return voicePitch;
}

public void setVoicePitch(double voicePitch) {
	this.voicePitch = voicePitch;
	storage.setItem(KEY_VOICE_PITCH, ""+voicePitch);
}

public String getSrtText() {
	return srtText;
}

public void setSrtText(String srtText) {
	this.srtText = srtText;
	storage.setItem(KEY_CURRENT_SRT, srtText);
}

public int getSrtSelectIndex() {
	return srtSelectIndex;
}

public void setSrtSelectIndex(int srtSelectIndex) {
	this.srtSelectIndex = srtSelectIndex;
	storage.setItem(KEY_SRT_INDEX, ""+srtSelectIndex);
}

public SubPlayerPreference(){
	storage= Storage.getLocalStorageIfSupported();
}

public void initialize(){//TODO make exception
	storage = Storage.getLocalStorageIfSupported();
	if(storage==null){
		throw new RuntimeException("storage not supported");
	}
	voiceName=toStringValue(storage.getItem(KEY_VOICE_NAME),"");
	voiceRate=toDouble(storage.getItem(KEY_VOICE_RATE),1.0);
	voicePitch=toDouble(storage.getItem(KEY_VOICE_PITCH),1.0);
	
	srtText=toStringValue(storage.getItem(KEY_CURRENT_SRT),"");
	srtSelectIndex=toInt(storage.getItem(KEY_SRT_INDEX),0);
}

private String toStringValue(String value,String defaultValue){
	String ret=defaultValue;
	if(value!=null){
	ret=value;
	}
	return ret;
}

private double toDouble(String value,double defaultValue){
	double ret=defaultValue;
	if(value!=null){
	try{
		ret=Double.parseDouble(value);
	}catch (Exception e) {
	}
	}
	return ret;
}
private int toInt(String value,int defaultValue){
	int ret=defaultValue;
	if(value!=null){
	try{
		ret=Integer.parseInt(value);
	}catch (Exception e) {
	}
	}
	return ret;
}

}
