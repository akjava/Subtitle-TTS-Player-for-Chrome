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
