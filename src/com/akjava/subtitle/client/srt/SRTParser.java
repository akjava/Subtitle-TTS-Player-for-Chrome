/*
 * �쐬��: 2004/04/09
 * License Apache 2.0
 * ���̐������ꂽ�R�����g�̑}����e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
package com.akjava.subtitle.client.srt;



/**
 * @author ak
*/
public class SRTParser {
private static int INDEX=0;
private static int TIME=1;
private static int TEXT=2;
private int mode=INDEX;

public SRTList parse(String[] lines){
	
	SRTList data=new SRTList();
	
		
		
		String line;
		SRTObject srtObject=new SRTObject();
		SRTObject preObject=null;
		String headerLine="";
		for(int i=0;i<lines.length;i++){
			line=lines[i];
			//log.info("line:"+line);
			if(mode==INDEX){
			
			    //first line ignore. what?
			    if(line.equals("") && srtObject!=null && preObject!=null){
			    	//log.info("addLine:"+preObject.getText());
			    	// preObject.setText(preObject.getText()+"\r\n");	//i dont know what is this?
			    }else{
			    if(!isDigitOnly(line)){
			    	//some accident.
			    	if(preObject!=null){//ignore or add.
			    	
			    		preObject.setText(preObject.getText()+"\r\n"+"\r\n"+line);//now can have line separators
			    	}
			    	//log.info("ignore:"+line);
			    }else{
			    	//System.out.println("headerText:"+headerLine.length());
			    	if(headerLine.length()>=2){
			    	
			    		//header only subs;
			    		//SubObject emptySub=new srtObject();
			    		data.add(srtObject);
			    		srtObject.setText(headerLine.substring(2));
			    		preObject=srtObject;
						srtObject=new SRTObject();
						headerLine="";
			    	}
				int index=0;
				try{
					index=Integer.parseInt(line);
					
				}catch(Exception e){
					index=0;
				}
					srtObject.setIndex(index);
					mode=TIME;
			    	}
			    }
			}
				
			else if(mode==TIME){
			
				if(line.length()==29){
					TimeData start=parseTime(line.substring(0,12));
					TimeData end=parseTime(line.substring(17));
					srtObject.setStartTime(start);
					srtObject.setEndTime(end);
					mode=TEXT;
				}
			}else if(mode==TEXT){
			
				if(line.equals("")){
					if(srtObject.getText()==null){//start with line separator case
						if(headerLine.equals("")){
						headerLine+="\r\n";
						
						}else{
							mode=INDEX;
						}
						//
					}else{//guess end srt text.
					if(isValid(srtObject)){
						data.add(srtObject);
						
					}
					preObject=srtObject;
					srtObject=new SRTObject();
					mode=INDEX;
					headerLine="";
					}
				}else{
				//	System.out.println("not empty:'"+line+"'");
					String text=srtObject.getText();
					if(text==null){
						text=headerLine+line;	//now support start with empty
						headerLine="";
					}
					else{
						text=text+"\r\n"+line;
					}
					srtObject.setText(text);
				}
			}else{
				
			}
		}
		if(isValid(srtObject)){
								data.add(srtObject);
							}
	
		mode=INDEX;
	
	return data;
}
/**
 * @param string
 * @return
 */
private TimeData parseTime(String line) {
	TimeData timeData=new TimeData(line);
	
	return timeData;
}
/**
 * @param srtObject
 * @return
 */
private boolean isValid(SRTObject srtObject) {
	if(srtObject.getIndex()!=0 && srtObject.getStartTime()!=null && srtObject.getEndTime()!=null){
		return true;
}else{
	return false;
}
}

public static boolean isDigitOnly(String text){
	if(text==null || text.length()==0){
		return false;
	}
	for (int i = 0; i < text.length(); i++) {
		if(!Character.isDigit(text.charAt(i))){
			return false;
		}
	}
	return true;
}
}
