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

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.akjava.subtitle.client.standard.StandardSubObject;


/**
 * @author ak
 * License Apache 2.0

 */
public class SRTList {
private int index=1;
Vector srtList=new Vector();
public int size(){
	return srtList.size();
}
public void clear(){
	srtList.removeAll(srtList);
	index=1;
}
public void add(SRTObject object){
	add(object,false);
}

public synchronized void add(SRTObject object,boolean isIndexChange){
	if(isIndexChange){
		object.setIndex(index);
		srtList.addElement(object);
		index++;
	}else{
		srtList.addElement(object);
			index=object.getIndex()+1;
	}
	
}
public synchronized void add(TimeData start,TimeData end,String text){
	SRTObject object=new SRTObject();
	object.setStartTime(start);
	object.setEndTime(end);
	object.setIndex(index);
	object.setText(text);
	srtList.addElement(object);
	index++;
}
public SRTObject getSRTObjectAt(int index){
	return (SRTObject) srtList.elementAt(index);
}

public List convertStandardSubtitleDataList(){
    List list=new Vector();
    for(int i=0;i<srtList.size();i++){
        SRTObject srt=(SRTObject)srtList.get(i);
        StandardSubObject standard=new StandardSubObject();
        standard.setStartTime(srt.getStartTime().getTime());
        standard.setEndTime(srt.getEndTime().getTime());
        if(isImageFile(srt.getText())){
        standard.setImagePath(srt.getText());   
        }else{
        if(srt.getText()!=null){
        	standard.setText(srt.getText());
        }else{
        	//
        }
        
        }
        list.add(standard);
    }
    return list;
}
/**
 * @param text
 * @return
 */
private boolean isImageFile(String text) {
	if(text==null){
		return false;
	}
    String extensions[]={".png",".gif",".bmp",".jpg"};
    for (int i = 0; i < extensions.length; i++) {
        if(text.toLowerCase().endsWith(extensions[i])){
            return true;
        }
    }
   
    return false;
}

public synchronized void sort(){
    SRTObject[] srtObjects=(SRTObject[]) srtList.toArray(new SRTObject[srtList.size()]);
    Arrays.sort(srtObjects);
    srtList.removeAll(srtList);
    for (int i = 0; i < srtObjects.length; i++) {
        srtList.add(srtObjects[i]);
    }
}
public void reindex(){
    //not sort.
    SRTObject[] srtObjects=(SRTObject[]) srtList.toArray(new SRTObject[srtList.size()]);
    for (int i = 0; i < srtObjects.length; i++) {
        srtObjects[i].setIndex(i+1);
    }
}
public String toString(){
StringBuffer buffer=new StringBuffer();
SRTObject[] srtObjects=(SRTObject[]) srtList.toArray(new SRTObject[srtList.size()]);
for (int i = 0; i < srtObjects.length; i++) {
	buffer.append(srtObjects[i].toString());
	buffer.append("\r\n");
}
return buffer.toString();
}

public SRTObject getContain(double time){
	return getContain((long)time*1000);
}



public SRTObject getContain(long time){
	SRTObject ret=null;
	for (int i = 0; i <size(); i++) {
		SRTObject srt=(SRTObject)srtList.get(i);
		if(time>=srt.getStartTime().getTime() && time<srt.getEndTime().getTime()){
			ret=srt;
			break;
		}
	}
	return ret;
}

public void remove(SRTObject srt) {
	srtList.remove(srt);
}

}
