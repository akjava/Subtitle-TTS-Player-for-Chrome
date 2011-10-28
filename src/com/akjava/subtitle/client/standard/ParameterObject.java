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
public class ParameterObject {
private String name;
private String value;

public ParameterObject(){
    
}

public ParameterObject(String name,String value){
    this.name=name;
    this.value=value;
}
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public String getValue() {
    return value;
}
public void setValue(String value) {
    this.value = value;
}

public String toString(){
    return "name="+name+" value="+value;
}

public boolean equals(Object object){
    if(object instanceof ParameterObject){
        ParameterObject other=(ParameterObject)object;
String name=getName();
if(name==null){
    if(other.getName()!=null){
        return false;
    }
}else{
    if(!name.equals(other.getName())){
        return false;
    }
}

String value=getValue();
if(value==null){
    if(other.getValue()!=null){
        return false;
    }
}else{
    if(!value.equals(other.getValue())){
        return false;
    }
}
return true;
    }
    
    return false;
}
}
