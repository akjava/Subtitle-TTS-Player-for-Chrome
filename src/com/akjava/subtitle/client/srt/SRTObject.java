/*
 * �쐬��: 2004/04/09
 * License Apache 2.0
 * ���̐������ꂽ�R�����g�̑}����e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
package com.akjava.subtitle.client.srt;

/**
 * @author ak
 * License Apache 2.0

 */
public class SRTObject implements Comparable{
private TimeData startTime;
private TimeData endTime;
private int index;
private String text=null;	//don't change null is important.
public SRTObject(){
	
}
public SRTObject(String text, long start,long end) {
	this.text=text;
	setStartTime(start);
	setEndTime(end);
}
public String toString(){
String result=""+index+"\r\n";
result+=startTime+" --> "+endTime+"\r\n";
result+=text+"\r\n";
return result;
}
/**
 * @return
 */
public TimeData getEndTime() {
	return endTime;
}

/**
 * @return
 */
public int getIndex() {
	return index;
}

/**
 * @return
 */
public TimeData getStartTime() {
	return startTime;
}

/**
 * @return
 */
public String getText() {
	return text;
}

/**
 * @param data
 */
public void setEndTime(TimeData data) {
	endTime = data;
}

public void setEndTime(long time) {
	endTime = new TimeData(time);
}

/**
 * @param i
 */
public void setIndex(int i) {
	index = i;
}

/**
 * @param data
 */
public void setStartTime(TimeData data) {
	startTime = data;
}

public void setStartTime(long time) {
	startTime = new TimeData(time);
}

/**
 * @param string
 */
public void setText(String string) {
	text = string;
}
/* (non-Javadoc)
 * @see java.lang.Comparable#compareTo(java.lang.Object)
 */
public int compareTo(Object o) {
    if(o instanceof SRTObject){
        SRTObject srt=(SRTObject)o;
        if(srt.getStartTime().getTime()==this.getStartTime().getTime()){
            if(srt.getEndTime().getTime()>this.getEndTime().getTime()){
                return -1;
            }else{
                return 1;
            }
        }else{
            if(srt.getStartTime().getTime()>this.getStartTime().getTime()){
                return -1;
            }else{
                return 1;
            }
        }
    }
    return 0;
}

public boolean intersect(SRTObject srt){
	if(srt==null){
		return false;
	}
	if(srt.getEndTime().getTime()>getStartTime().getTime() && srt.getEndTime().getTime()<getEndTime().getTime()){
		return true;
	}
	
	if(srt.getStartTime().getTime()>getStartTime().getTime() && srt.getStartTime().getTime()<getEndTime().getTime()){
		return true;
	}
	
	return false;
}

}
