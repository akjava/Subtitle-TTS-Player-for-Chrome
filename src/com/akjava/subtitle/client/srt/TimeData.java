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
package com.akjava.subtitle.client.srt;



/**
 * @author ak
 * License Apache 2.0
 *
 * ���̐������ꂽ�R�����g�̑}����e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
public class TimeData {
private int hour;
private int second;
private int minute;
private int millisecond;
public TimeData(long millisecond){
	setTime(millisecond);
}
public void setTime(long millisecond){
	long hour_millisecond=(60*60*1000);
		int minute_millisecond=60*1000;
		int second_millisecond=1000;
		int hour=(int)(millisecond/hour_millisecond);
		long remain=millisecond%hour_millisecond;
		int minute=(int)(remain/minute_millisecond);
		remain=remain%minute_millisecond;
		int second=(int)(remain/second_millisecond);
		remain=remain%second_millisecond;
		
		setHour(hour);
		setMinute(minute);
		setSecond(second);
		setMillisecond((int)remain);
}

public TimeData(String line){
	int hour=Integer.parseInt(line.substring(0,2));
		int minute=Integer.parseInt(line.substring(3,5));
		int second=Integer.parseInt(line.substring(6,8));
		int milli=Integer.parseInt(line.substring(9,12));
		setHour(hour);
		setMinute(minute);
		setSecond(second);
		setMillisecond(milli);
}
public long getTime(){
	long result=millisecond;
	result+=second*1000;
	result+=minute*60*1000;
	result+=hour*60*60*1000;
	return result;
}
public String toString(){
	return toLabel2(hour)+":"+toLabel2(minute)+":"+toLabel2(second)+","+toLabel3(millisecond);
}

public String toMinuteString(){
	int hminute=minute+hour*60;
	return toLabel2(hminute)+":"+toLabel2(second)+","+toLabel3(millisecond);
}
private String toLabel2(int number){
	String v=""+number;
	if(v.length()<2)
	v="0"+v;
	return v;
}

private String toLabel3(int number){
	String v=""+number;
	if(v.length()<2)
	v="0"+v;
	if(v.length()<3)
	v="0"+v;
	if(v.length()>3)
	return "999";
	return v;
}

/**
 * @return
 */
public int getHour() {
	return hour;
}

/**
 * @return
 */
public int getMillisecond() {
	return millisecond;
}

/**
 * @return
 */
public int getMinute() {
	return minute;
}

/**
 * @return
 */
public int getSecond() {
	return second;
}

/**
 * @param i
 */
public void setHour(int i) {
	hour = i;
}

/**
 * @param i
 */
public void setMillisecond(int i) {
	millisecond = i;
}

/**
 * @param i
 */
public void setMinute(int i) {
	minute = i;
}

/**
 * @param i
 */
public void setSecond(int i) {
	second = i;
}
/**
 * @param duration
 */
public synchronized void roll(long duration) {
	long current=getTime();
	current+=duration;
	setTime(current);
}

}
