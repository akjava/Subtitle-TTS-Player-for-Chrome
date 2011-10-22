/*
 * Created on 2005/05/19
 * Author aki@www.xucker.jpn.org
 * License Apache2.0 or Common Public License
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
