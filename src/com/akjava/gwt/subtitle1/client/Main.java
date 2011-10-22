package com.akjava.gwt.subtitle1.client;



import com.akjava.subtitle.client.srt.SRTList;
import com.akjava.subtitle.client.srt.SRTObject;
import com.akjava.subtitle.client.srt.SRTParser;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {

  /**
   * This is the entry point method.
   */
	private int index=0;
private Label currentLabel;
private Label durationLabel;
private Timer timer;
private VerticalPanel subtitles;
private TabPanel subtitleTab;

private SRTList   srtList=new SRTList();;
private Label subtitleArea;
private TimeSpinner startTimeAdd;
private TimeSpinner endTimeAdd;
private TextArea addArea;
private MessagesConstants constants;
  public void onModuleLoad() {
	  constants = (MessagesConstants)GWT.create( MessagesConstants.class );

	  HorizontalPanel root=new HorizontalPanel();
	  
	  final VerticalPanel panel=new VerticalPanel();
	  root.add(panel);
	  
	  HorizontalPanel openControler=new HorizontalPanel();
	  panel.add(openControler);
	  final TextBox id=new TextBox();
	  id.setText("");
	  id.setWidth("250px");
	  openControler.add(id);
	  Button bt=new Button(constants.Open());//open
	  
	  //timebar
	  Grid info=new Grid(1,4);
	 // panel.add(info);
	  info.setWidget(0, 0, new Label(constants.Length()+":"));//Duration
	  durationLabel = new Label();
	info.setWidget(0, 1, durationLabel);
	  info.setWidget(0, 3, new Label(constants.Current()+":"));//Current
	  currentLabel = new Label();
	info.setWidget(0, 2, currentLabel);
	
	
	
	  final HTML html=new HTML();
	  html.setSize("425px", "344px");
	  panel.add(html);
	  bt.addClickListener(new ClickListener(){
    public void onClick(Widget sender) {
			HTML div=new HTML("<div id='"+"index"+index+"'></div>");
			//panel.add(div);
			html.setHTML("<div id='"+"index"+index+"'></div>");
			String videoID=id.getText();
			if(id.getText().startsWith("http://")){
				int m=id.getText().indexOf("?");
				if(m!=-1){
				videoID=parseVideoId(id.getText().substring(m+1));
				}
			}
			GWT.log("id="+videoID, null);
			open("index"+index,videoID);
			//index++;
			
			if(timer==null){
				 Timer t = new Timer() {
				      public void run() {
				       durationLabel.setText(""+getDuration());
				       double time=getCurrentTime();
				       currentLabel.setText(""+time);
				       if(srtList!=null){
				       SRTObject sub=srtList.getContain(time);
				       if(sub!=null){
				    	   subtitleArea.setText(sub.getText());
				       		}
				       else  {
				    	   subtitleArea.setText("");
			       		}
				       }
				      }
				    };

				    // Schedule the timer to run once in 5 seconds.
				    t.scheduleRepeating(100);
			}
		}});
	  openControler.add(bt);
	  
	  //quick add area
	  final HorizontalPanel addSubtitleArea=new HorizontalPanel();
	  
	  final ToggleButton editMode=new ToggleButton(constants.EditMode());//Edit Mode
	  editMode.setDown(true);
	  editMode.addClickListener(new ClickListener(){
		    public void onClick(Widget sender) {
		    	if(editMode.isDown()){
		    		//set mode edit
		    		addSubtitleArea.setVisible(true);
		    	}else{
		    		//set mode view
		    		addSubtitleArea.setVisible(false);
		    	}
		    	
		    }
	  });
	  openControler.add(editMode);
	  
	  
	  //addSubtitleArea.setWidth("425px");
	  panel.add(addSubtitleArea);
	  final TextArea inputSub=new TextArea();
	  inputSub.setWidth("280px");
	  addSubtitleArea.add(inputSub);
	  
	  
	  final NumberListBox numberListBox=new NumberListBox(1,10);
	  Button addSubButton=new Button(constants.Add());//add
	  addSubButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				int add=Integer.parseInt(numberListBox.getItemText(numberListBox.getSelectedIndex()))*1000;
				long c=(long) (getCurrentTime()*1000);
				
				if(inputSub.getText().length()>0){
				addSubtitle(inputSub.getText(),c,c+add);
				
				inputSub.setText("");
				subtitleTab.selectTab(0);
				}
			}});
	  addSubtitleArea.add(addSubButton);
	
	  addSubtitleArea.add(new Label(constants.Length()));//duration
	  numberListBox.setItemSelected(1, true);
	  addSubtitleArea.add(numberListBox);
	  
	  addSubtitleArea.add(new Label(constants.Sec()));//sec
	  
	  subtitleArea = new Label();
	 // subtitleArea.setWordWrap(true);
	  subtitleArea.setSize("425px", "80px");
	  subtitleArea.setStyleName("subtitle");
	  panel.add(subtitleArea);
	  
		final VerticalPanel subtitleControler=new VerticalPanel();
		  subtitleTab = new TabPanel();
		subtitleControler.add(subtitleTab);
		 
		
		  //list
		  VerticalPanel listPanel=new VerticalPanel();
		  subtitleTab.add(listPanel, constants.Subtitle_List());//Subtitles
		  subtitles = new VerticalPanel();
		ScrollPanel scroller = new ScrollPanel(subtitles);
		
		  scroller.setSize("425px", "240px");
		  listPanel.add(scroller);
		  
		  editAddTab = new TabPanel();
			subtitleControler.add(editAddTab);
			
		  subtitleEdit = new VerticalPanel();
		  subtitleEdit.setVisible(false);
		editAddTab.add(subtitleEdit,constants.Edit());
		  
		  HorizontalPanel times=new HorizontalPanel();
		  subtitleEdit.add(times);
		  
		  HorizontalPanel timesb=new HorizontalPanel();
		  subtitleEdit.add(timesb);
		
		  startTimeEdit = new TimeSpinner(0);
		times.add(new Label(constants.Start()));
		  times.add(startTimeEdit);
		  
		  endTimeEdit = new TimeSpinner(0);
		  timesb.add(new Label(constants.End()));
		  timesb.add(endTimeEdit);
		  AutoSetTime ast=new AutoSetTime(startTimeEdit,endTimeEdit);
		  timesb.add(ast);
		 
		  
		  editArea = new TextArea();
		editArea.setSize("425px", "90px");
		  subtitleEdit.add(editArea);
		  
		  HorizontalPanel editButtons=new HorizontalPanel();
		  subtitleEdit.add(editButtons);
		  Button updateButton=new Button(constants.Update());//update
		  updateButton.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					updateSubtitle();
				}});
		  editButtons.add(updateButton);
		  
		  Button deleteButton=new Button(constants.Delete());//delete
		  deleteButton.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
				 CenterCinfirmDialog dialog=new CenterCinfirmDialog(constants.Delete(),constants.Delete_Selected_Sub()){//delete subtitle
					@Override
					public void clickOK() {
						if(currentSelectedPanel!=null){
						removeSubtitle(currentSelectedPanel);
						}
					}};
					dialog.show();
				}});
		  editButtons.add(deleteButton);
		  
		  
		  Button deleteAllButton=new Button(constants.Delete_all());//delete All
		  deleteAllButton.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
				 CenterCinfirmDialog dialog=new CenterCinfirmDialog(constants.Delete_all(),constants.Delete_all_confirm()){//delete all subtitles.\nAre you sure?
					@Override
					public void clickOK() {
						srtList.clear();
						selectSubtitle(null);
						  currentSelectedPanel=null;
						  setEnableEditButtons(false);
						updateSubtitleView();
					}};
					dialog.show();
				}});
		  editButtons.add(deleteAllButton);
		  
		  Button normalizeAllButton=new Button(constants.Normalize());//delete All
		  normalizeAllButton.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
				 //srtList
					for(int i=1;i<srtList.size();i++){
						SRTObject srt=srtList.getSRTObjectAt(i);
						SRTObject before_srt=srtList.getSRTObjectAt(i-1);
						if(before_srt.getEndTime().getTime()>srt.getStartTime().getTime()){
							srt.setStartTime(before_srt.getEndTime().getTime());//force
						}
					}
					updateSubtitleView();
				}
		  });
		  editButtons.add(normalizeAllButton);
		  
		  
		  
		  VerticalPanel subtitleAdd=new VerticalPanel();
		  editAddTab.add(subtitleAdd,constants.Add());
		  editAddTab.selectTab(1);
		  
		  HorizontalPanel times2=new HorizontalPanel();
		  subtitleAdd.add(times2);
		  HorizontalPanel times3=new HorizontalPanel();
		  subtitleAdd.add(times3);
		  
		  
		  startTimeAdd = new TimeSpinner(0);
		  times2.add(new Label(constants.Start()));//Start
		  times2.add(startTimeAdd);
		  
		  endTimeAdd = new TimeSpinner(0);
		  times3.add(new Label(constants.End()));//End
		  times3.add(endTimeAdd);
		  AutoSetTime ast2=new AutoSetTime(startTimeAdd,endTimeAdd);
		  times3.add(ast2);
		  
		  addArea = new TextArea();
		  addArea.setSize("425px", "90px");
		  subtitleAdd.add(addArea);
		  
		  Button addButton=new Button(constants.Add());//add
		  addButton.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					addSubtitle();
				}});
		  subtitleAdd.add(addButton);
		  
		  
		  listPanel.add(editAddTab);
		  //importer
		  VerticalPanel importPanel=new VerticalPanel();
		  subtitleTab.add(importPanel, constants.Import());
		  
		  importSrtArea = new TextArea();
		importSrtArea.setSize("425px", "420px");
		  importPanel.add(importSrtArea);
		  Button importButton=new Button(constants.Import());//import
		  importButton.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					importSubtitle();
				}});
		  importPanel.add(importButton);
		  
		  //export
		  VerticalPanel exportPanel=new VerticalPanel();
		  subtitleTab.add(exportPanel, constants.Export());//Export
		  
		  final TextArea exportSrt=new TextArea();
		  exportSrt.setSize("425px", "420px");
		  Button exportButton=new Button(constants.Select_All());//select All
		  exportButton.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					exportSrt.selectAll();
				}});
		  exportPanel.add(exportButton);
		  exportSrt.setReadOnly(true);
		  exportPanel.add(exportSrt);
		  
		  subtitleTab.addTabListener(new TabListener(){

			public boolean onBeforeTabSelected(SourcesTabEvents sender,
					int tabIndex) {
				if(tabIndex==2){
					srtList.reindex();
					exportSrt.setText(srtList.toString());
				}
				return true;
			}

			public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
				// TODO Auto-generated method stub
				
			}});
		  subtitleTab.selectTab(1);
		 
		 root.add(subtitleControler);
		  
	  RootPanel.get("main").add(root);
  }
  
  public void addSubtitle(){
	  String text=addArea.getText();
	  if(text.length()>0){
	  addSubtitle(text,startTimeAdd.getTime(),endTimeAdd.getTime());
	  }
	  addArea.setText("");
  }
  public void addSubtitle(String text,long start,long end){
	  SRTObject srt=new SRTObject(text,start,end);
		srtList.add(srt);
		srtList.sort();
		updateSubtitleView();
  }
  
  private String[] splitLine(String text){
	  text=text.replace("\r\n", "\n");
	  text=text.replace("\r", "\n");
	  return text.split("\n");
  }
  public void importSubtitle(){
	  if(importSrtArea.getText().length()>0){
	  String lines[]=splitLine(importSrtArea.getText());
	
	  SRTParser parser=new SRTParser();
	  srtList=parser.parse(lines);
	  //GWT.log(""+srtList.size(),null);
	  updateSubtitleView();
	  
	  subtitleTab.selectTab(0);
	  importSrtArea.setText("");
	  }
  }
  
  private void updateSubtitle(){
	  if(currentSelectedPanel!=null){
	  currentSelectedPanel.srt.setText(editArea.getText());
	  currentSelectedPanel.srt.getStartTime().setTime(startTimeEdit.getTime());
	  currentSelectedPanel.srt.getEndTime().setTime(endTimeEdit.getTime());
	  currentSelectedPanel.update();
	  	
	    srtList.sort();
		updateSubtitleView();
	  }
  }
  
  private void removeSubtitle(SRTPanel panel){
	  srtList.remove(panel.srt);
	  updateSubtitleView();
	  selectSubtitle(null);
	  currentSelectedPanel=null;
	  setEnableEditButtons(false);
  }
  
  private void updateSubtitleView(){
	  SelectSubtitle selecter=new SelectSubtitle();
	  subtitles.clear();
	  for(int i=0;i<srtList.size();i++){
		  SRTObject sub=srtList.getSRTObjectAt(i);
		
		  SRTPanel panel=new SRTPanel(sub);
		  panel.addClickListener(selecter);
		  subtitles.add(panel);
	  }
  }
  public static native double getDuration() /*-{
  return $wnd.getDuration();
	}-*/;
  
  public static native double getCurrentTime() /*-{
  return $wnd.getCurrentTime();
	}-*/;
	public static native void open(String id,String videoId) /*-{
	  $wnd.open(id,videoId);
	}-*/;
	public static native void play() /*-{
	  $wnd.play();
	}-*/;
 
	

	/*
	  public String toYoutubeHtml(String id){
		  String url="http://www.youtube.com/v/"+id;
		  
		  url+="&enablejsapi=1&playerapiid=ytplayer";
		  
		  String text="";
		  text+="<object width=\"425\" height=\"344\">\n";
		  text+="<param name=\"movie\" value=\""+url+"\"></param>\n";
		  text+="<param name=\"allowFullScreen\" value=\"true\"></param>\n";
		  text+="<embed src=\""+url+"\"\n";
		  text+="  type=\"application/x-shockwave-flash\"\n";
		  text+="  allowfullscreen=\"true\"\n";
		  text+="  width=\"425\" height=\"344\">\n";
		  text+="</embed>\n";
		  text+="</object>\n";
		
return text;
	  
  }
  */
	
	private SRTPanel currentSelectedPanel;
	private TimeSpinner startTimeEdit;
	private TimeSpinner endTimeEdit;
	private TextArea editArea;
	private TextArea importSrtArea;
	private VerticalPanel subtitleEdit;
	private TabPanel editAddTab;
	public class SelectSubtitle implements ClickListener{

		public void onClick(Widget sender) {
			//GWT.log("select", null);
			if(currentSelectedPanel!=null){
				currentSelectedPanel.removeStyleDependentName("selected");
			}
			sender.getParent().addStyleDependentName("selected");
			currentSelectedPanel=(SRTPanel)sender.getParent();
			
			selectSubtitle(currentSelectedPanel.srt);
			setEnableEditButtons(true);
			
		}
		
	}
	
	public void setEnableEditButtons(boolean bool){
		if(bool){
			editAddTab.selectTab(0);
		}else{
			editAddTab.selectTab(1);
		}
	}
	
	private void selectSubtitle(SRTObject srt){
		if(srt==null){
			startTimeEdit.setTime(0);
			endTimeEdit.setTime(0);
			
			editArea.setText("");
		}else{
			startTimeEdit.setTime(srt.getStartTime().getTime());
			endTimeEdit.setTime(srt.getEndTime().getTime());
			
			editArea.setText(srt.getText());
		}
		
	}
	public class AutoSetTime extends HorizontalPanel{
		public AutoSetTime(final TimeSpinner start,final TimeSpinner end){
			 final NumberListBox numberListBox=new NumberListBox(1,10);
			  Button addSubButton=new Button(constants.Sync());//add
			  addSubButton.addClickListener(new ClickListener(){
					public void onClick(Widget sender) {
						int add=Integer.parseInt(numberListBox.getItemText(numberListBox.getSelectedIndex()))*1000;
						long nt=start.getTime()+add;
						end.setTime(nt);
						}
					});
			  
			  this.add(addSubButton);
			  this.add(new Label(constants.Length()));//duration
			  numberListBox.setItemSelected(1, true);
			  
			  this.add(numberListBox);
		}
		 
	}
	public class NumberControler extends HorizontalPanel{
	private int min;
	private int max=99;
	private boolean loop=true;
	private TextBox textBox;
	public NumberControler(int width){
		this();
		textBox.setWidth(""+width+"px");
	}
	public NumberControler(){
		 textBox=new TextBox();
		 textBox.setWidth("20px");
		 add(textBox);
		 final Button minus=new Button("-");
		 minus.setStyleName("nopad");
		 minus.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					int c=getNumber();
					if(c-1>=min){
						textBox.setText(""+(c-1));
					}else{
						if(loop){
							textBox.setText(""+max);
						}
					}
				}});
		 add(minus);
		 
		 final Button plus=new Button("+");
		 plus.setStyleName("nopad");
		 plus.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					int c=getNumber();
					
					if(c+1<=max){
						textBox.setText(""+(c+1));
					}else{
						if(loop){
							textBox.setText(""+min);
						}
					}
				}});
		 add(plus);
		
		 setNumber(0);
	}
	
	public void setNumber(int num){
		textBox.setText(""+num);
	}
	public int getNumber(){
		int r=0;
		try{
		r=Integer.parseInt(textBox.getText());
		}catch(Exception e){
			
		}
		return r;
	}

	public TextBox getTextBox() {
		return textBox;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	
	}
	
	public class TimeSpinner extends HorizontalPanel{
		private NumberControler minute;
		private NumberControler second;
		private NumberControler millisecond;

		public TimeSpinner(long time){
			minute = new NumberControler(30);
			add(minute);
			add(new Label(constants.Min()));
			second = new NumberControler(30);
			add(second);
			add(new Label(constants.Sec()));
			millisecond = new NumberControler(40);
			millisecond.setMax(999);
			add(millisecond);
			add(new Label(constants.MS()));
		}
		public void setTime(long millisecond){
			long hour_millisecond=(60*60*1000);
			int minute_millisecond=60*1000;
			int second_millisecond=1000;
			int hour=(int)(millisecond/hour_millisecond);
			long remain=millisecond%hour_millisecond;
			int minute=(int)(remain/minute_millisecond);
			remain=remain%minute_millisecond;
			int second=(int)(remain/second_millisecond);
			remain=remain%second_millisecond;
			
			this.minute.setNumber(minute);
			this.second.setNumber(second);
			this.millisecond.setNumber((int)remain);
		}
		public long getTime(){
			long result=millisecond.getNumber();
			result+=second.getNumber()*1000;
			result+=minute.getNumber()*60*1000;
			
			return result;
		}
	}
	//show srt
	public class SRTPanel extends HorizontalPanel{
		private TextBox startLabel;
		private TextBox endLabel;
		private Label textLabel;
		private SRTObject srt;
		public SRTPanel(SRTObject srt){
			this.setStylePrimaryName("showing");
			this.srt=srt;
			startLabel=new TextBox();
			
			add(startLabel);
			startLabel.setWidth("80px");
			startLabel.setReadOnly(true);
			
			endLabel=new TextBox();
			
			add(endLabel);
			endLabel.setWidth("80px");
			endLabel.setReadOnly(true);
			
			
			textLabel=new Label();
			textLabel.setWidth("240px");
			
			add(textLabel);
			update();
		}
		public void update(){
			startLabel.setText(srt.getStartTime().toMinuteString());
			endLabel.setText(srt.getEndTime().toMinuteString());
			String text=srt.getText();
			text=text.replace("\r", "");
			text=text.replace("\n"," ");
			textLabel.setText(text);
		}
		
		public void addClickListener(ClickListener listener){
			textLabel.addClickListener(listener);
		}
		
		
		
	}
	  
	public class NumberListBox extends ListBox{
		public NumberListBox(int min,int max){
			for(int i=min;i<max;i++){
				this.addItem(""+i);
			}
		}
	}
	//need query
	public static String parseVideoId(String path){
		String id=null;
		
		String[] values=path.split("&");
		for (int i = 0; i < values.length; i++) {
			if(values[i].startsWith("v=")){
				id=values[i].substring(2);
				break;
			}
		}
		return id;
	}
}
