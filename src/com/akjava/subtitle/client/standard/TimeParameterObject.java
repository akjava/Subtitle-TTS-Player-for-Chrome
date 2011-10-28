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
public class TimeParameterObject extends ParameterObject{
private long startTime;
private long endTime;

public long getEndTime() {
    return endTime;
}
public void setEndTime(long endTime) {
    this.endTime = endTime;
}
public long getStartTime() {
    return startTime;
}
public void setStartTime(long startTime) {
    this.startTime = startTime;
}
public TimeParameterObject(){
    super();
}

public TimeParameterObject(String name,String value,long start,long end){
super(name,value);
startTime=start;
endTime=end;
}

public boolean equals(Object object){
    if(object instanceof TimeParameterObject){
        TimeParameterObject other=(TimeParameterObject)object;
        
        
        return super.equals(object) && getStartTime() == other.getStartTime() && getEndTime() == other.getEndTime();
    }
    return false;
}


public boolean equalTime(StandardSubObject standard){
    return this.getStartTime()==standard.getStartTime() &&this.getEndTime()==standard.getEndTime();
}

public String toString(){
    return "name="+getName()+" value="+getValue()+" start="+startTime+" end="+endTime;
}

}
