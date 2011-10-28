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

import java.util.ArrayList;
import java.util.List;



/**
 * 
 *
 */
public class TimeBaseFileObject {
private List paramList=new ArrayList();
private List timeList=new ArrayList();

private double version=1.0;

public double getVersion() {
    return version;
}
public void setVersion(double version) {
    this.version = version;
}
public void addParameter(ParameterObject mark){
    paramList.add(mark);
}

public void removeParameter(ParameterObject mark){
    paramList.remove(mark);
}

public void setParameters(ParameterObject[] times){
    paramList.removeAll(paramList);
    for (int i = 0; i < times.length; i++) {
        paramList.add(times[i]);
    }
}

public boolean equals(Object object){
    if(object==this){
        return true;
    }
    if(!(object instanceof TimeBaseFileObject)){
        return false;
    }
    
    TimeBaseFileObject other=(TimeBaseFileObject)object;
    
    TimeParameterObject[] myTimes=getTimeParameters();
    TimeParameterObject[] otherTimes=other.getTimeParameters();
    if(myTimes.length!=otherTimes.length){
        return false;
    }
    for(int i=0;i<myTimes.length;i++){
        if(!myTimes[i].equals(otherTimes[i])){
            return false;
        }
    }
    
    
    
  
    
    ParameterObject[] myParams=getParameters();
    ParameterObject[] otherParams=other.getParameters();
    if(myParams.length!=otherParams.length){
        return false;
    }
    for(int i=0;i<otherParams.length;i++){
        if(!myParams[i].equals(otherParams[i])){
            return false;
        }
    }
    
    
    return true;
}



public ParameterObject[] getParameters(){
    return (ParameterObject[])paramList.toArray(new ParameterObject[paramList.size()]);
}

public void removeTimeParameter(TimeParameterObject mark){
    timeList.remove(mark);
}
public void addTimeParameter(TimeParameterObject mark){
    timeList.add(mark);
}

public void setTimeParameters(TimeParameterObject[] times){
    timeList.removeAll(timeList);
    for (int i = 0; i < times.length; i++) {
        timeList.add(times[i]);
    }
}




public TimeParameterObject[] getTimeParameters(){
    return (TimeParameterObject[])timeList.toArray(new TimeParameterObject[timeList.size()]);
}
}
