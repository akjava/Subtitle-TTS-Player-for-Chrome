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
package com.akjava.subtitle.client.standard;


/**
 * 
 *
 */
public class StandardSubObject implements Comparable{

	public boolean intersect(long start,long end){
		
		if(start>this.startTime && start<this.endTime){
			return true;
		}
		
		if(end>this.startTime && end<this.endTime){
			return true;
		}
		
		return false;
	}
	//sort time base
	public int compareTo(Object o) {
		
	    if(o instanceof StandardSubObject){
	    	StandardSubObject srt=(StandardSubObject)o;
	        if(srt.getStartTime()==this.getStartTime()){
	            if(srt.getEndTime()>this.getEndTime()){
	                return -1;
	            }else{
	                return 1;
	            }
	        }else{
	            if(srt.getStartTime()>this.getStartTime()){
	                return -1;
	            }else{
	                return 1;
	            }
	        }
	    }
	    return 0;
	}
	

public long getEndTime() {
    return endTime;
}
public void setEndTime(long endTime) {
    this.endTime = endTime;
}
public String getImagePath() {
    return imagePath;
}
public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
}
public long getStartTime() {
    return startTime;
}
public void setStartTime(long startTime) {
    this.startTime = startTime;
}
public String getText() {
    return text;
}
public void setText(String text) {
    this.text = text;
}
private long startTime;
private long endTime;
private String text="";
private String imagePath;
private String option;
public String getOption() {
    return option;
}
public void setOption(String option) {
    this.option = option;
}

public String toString(){
	return "["+getText()+"]";
}

}
