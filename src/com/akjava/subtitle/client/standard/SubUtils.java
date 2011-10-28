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


import java.util.Arrays;
import java.util.List;

import com.akjava.subtitle.client.srt.SRTList;
import com.akjava.subtitle.client.srt.SRTObject;
import com.akjava.subtitle.client.srt.SRTParser;




/**
 * 
 *
 */
public class SubUtils {
    
    public static int SUB=0;
    public static int SRT=1;
    
    public static final double NTSC_FRAME_RATE=29.97;
    public static final double PAL_FRAME_RATE=25;
    
    public static String[] getDefaultImageExtensions(){
        return new String[]{".png",".gif",".bmp",".jpg"};
    }

    public static long countContainTime(StandardSubObject object1,StandardSubObject object2){
        long result=0;
        for(long i=object1.getStartTime();i<=object1.getEndTime();i++){
            if(i>=object2.getStartTime() && i<=object2.getEndTime()){
                result++;
            }
        }
        return result;
    }
    public static long getTotalTime(StandardSubObject[] subs){
        long result=0;
      
        for (int i = 0; i < subs.length; i++) {
            result+=subs[i].getEndTime()-subs[i].getStartTime();
        }
        return result;
    }

    public static String toTimeString(long frame){
        long hour=(long)(frame/(60*60*1000));
        long remain=(long)(frame%(60*60*1000));
        
        long minute=remain/(60*1000);
        remain=remain%(60*1000);
        
        long second=remain/1000;
        remain=remain%1000;
        
        String result="";
        if(hour<10){
            result="0"+hour;
        }else{
            result+=hour;
        }
        result+=":";
        if(minute<10){
            result+="0"+minute;
        }else{
            result+=minute;
        }
        result+=":";
        if(second<10){
            result+="0"+second;
        }else{
            result+=second;
        }
        long milli=remain*60/1000;
        if(milli<10){
            result+=":"+"0"+milli;
        }
        
        else{
            result+=":"+milli;
        }
            
        return result;
        
    }
    
    public static String saveAsSrt(List<StandardSubObject> standardList){
    	//log.info(standardList);
    	//now save as srt.
    	StringBuffer srtText=new StringBuffer();
    	StandardSubObject[] subList=standardList.toArray(new StandardSubObject[standardList.size()]);
    	//log.info("sub:"+subList.length);
    	Arrays.sort(subList);
    	
    	int index=1;
		for (int i = 0; i < subList.length; i++) {
			SRTObject srt=new SRTObject();
			String text=subList[i].getText();
			//change \n line separater.
			text=text.replace("\r\n","_SYSTEM_TMP_RN_");
			text=text.replace("\n","\r\n");
			text=text.replace("_SYSTEM_TMP_RN_","\r\n");
			srt.setText(text);
			srt.setStartTime(subList[i].getStartTime());
			srt.setEndTime(subList[i].getEndTime());
			srt.setIndex(index);
			srtText.append(srt.toString());
			srtText.append("\r\n");//for subtitle change
			index++;
		}
			
		return srtText.toString();
    }
    public static SRTList loadSrt(String text){
        SRTParser parser=new SRTParser();
        
        text=text.replace("\r\n","_SYSTEM_TMP_RN_");
		text=text.replace("\n","\r\n");
		text=text.replace("_SYSTEM_TMP_RN_","\r\n");
        String lines[]=text.split("\r\n");
        SRTList list=parser.parse(lines);
		return list;
        }
    
}
