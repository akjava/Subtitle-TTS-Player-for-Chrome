package com.akjava.gwt.subplayer.client;

import com.akjava.gwt.subplayer.client.resources.Binder;
import com.akjava.gwt.subplayer.client.ui.RangeSlider;
import com.akjava.subtitle.client.srt.SRTList;
import com.akjava.subtitle.client.srt.SRTObject;
import com.akjava.subtitle.client.srt.SRTParser;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SubPlayer implements EntryPoint,SubContainer {

	
	private VerticalPanel itemPanel;
	private LoadPanel loadPanel;
	@Override
	public void onModuleLoad() {
		//pre load resource
		ImageResource icon=Binder.INSTANCE.loadanime();
		loadImg = new Image(icon);
		loadImg.setVisible(false);
		loadImg.addLoadHandler(new LoadHandler() {
			
			@Override
			public void onLoad(LoadEvent event) {
				RootPanel.get().remove(loadImg);
				loadImg.setVisible(true);
			}
		});
		RootPanel.get().add(loadImg);
		
		preference = new SubPlayerPreference();
		preference.initialize();
		
		
		
		tab = new TabLayoutPanel(1.5, Unit.EM);
		tab.setHeight("550px");
		
		VerticalPanel root=new VerticalPanel();
		root.setWidth("100%");
		root.setHeight("100%");
		//root.setHeight("200px");
		RootLayoutPanel.get().add(tab);
		 tab.add(root, "PLAY");
		 
		 noSubtitle = new Label("Subtitle is empty.load from Load tab");
		 noSubtitle.setStyleName("nosubtitle");
		 root.add(noSubtitle);
		 
		 
		 loadPanel = new LoadPanel();
		 loadPanel.setWidth("100%");
		 loadPanel.setHeight("100%");
		 tab.add(loadPanel, "LOAD");
		 tab.selectTab(1);
		 loadPanel.setText(preference.getSrtText());
		
		itemPanel = new VerticalPanel();
		itemPanel.setSpacing(8);
		
		itemPanelScroll = new ScrollPanel(itemPanel);
		itemPanelScroll.setWidth("100%");
		itemPanelScroll.setHeight("400px");
		root.add(itemPanelScroll);
		
		/*
		for(int i=0;i<5;i++){
			String text=i+" hello world\n";
			for(int j=0;j<i;j++){
				text+="line\n";
			}
		
		HTMLPanel label=new HTMLPanel(text.replace("\n", "<br/>"));
		FocusPanel panel=new FocusPanel(label);
		panel.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				unselectAll();
				setlectWidget((Widget) event.getSource());
			}
		});
		//label.setHeight("100px");
		itemPanel.add(panel);
		
		}*/
		

		
		
		
		playerWidget = new PlayerWidget(this);
		root.add(playerWidget);
		
		DisclosurePanel ds=new DisclosurePanel("show subtitle time [start] - [end]");
		timeLabel = new Label();
		ds.add(timeLabel);
		//ds.add(new Label("0:0:0 - 0:0:12"));
		root.add(ds);
		
		selectWidgetHandler=new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				unselectAll();
				setlectWidget((Widget) event.getSource());
			}
		};
		
		VoiceSettings voiceSettings=new VoiceSettings();
		root.add(voiceSettings);
		
		//load data from preferences
		//if empty load mode.
	}
	
	public class SRTItemPanel extends FocusPanel{
		SRTObject srt;
	public SRTObject getSrt() {
			return srt;
		}
	public SRTItemPanel(SRTObject srt){
		this.srt=srt;
		HorizontalPanel hpanel=new HorizontalPanel();
		hpanel.setSpacing(2);
		hpanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		hpanel.add(new Button("PLAY"));
		HTMLPanel html=new HTMLPanel(srt.getText().replace("\n", "<br/>"));
		
		hpanel.add(html);
		
		setWidget(hpanel);
	}
	}
	
	ClickHandler selectWidgetHandler;
	private TabLayoutPanel tab;
	private Label noSubtitle;
	private SubPlayerPreference preference;
	private Image loadImg;
	private PlayerWidget playerWidget;
	private ScrollPanel itemPanelScroll;
	private Label timeLabel;
	
	public class LoadPanel extends VerticalPanel{
		private TextArea textArea;

		public LoadPanel(){
		textArea = new TextArea();
		textArea.setWidth("95%");
		textArea.setHeight("300px");
		add(textArea);
		
		HorizontalPanel bcontrol=new HorizontalPanel();
		add(bcontrol);
		Button load=new Button("LOAD");
		
		load.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				loadSrt(0);
				
			}
		});
		bcontrol.add(load);
		
		
		Button clear=new Button("CLEAR");
		
		clear.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				clearSrt();
			}

		
		});
		bcontrol.add(clear);
		}

		public String getText(){
			return textArea.getText();
		}
		public void setText(String text){
			textArea.setText(text);
		}
		
		
	}
	
	private void clearSrt() {
		loadPanel.setText("");
		itemPanel.clear();
		initPlayerSettings();
		
		preference.setSrtText("");
		
		
		updateNoSubtitleWarning();
	}
	
	
	
	private void updateNoSubtitleWarning(){
		if(itemPanel.getWidgetCount()>0){
			noSubtitle.setVisible(false);
		}else{
			noSubtitle.setVisible(true);
		}
	}
	
	
	private void loadSrt(final int selectIndex) {
		final String text=loadPanel.getText();
		
		
		
		
		final DialogBox dialog=new DialogBox();
		
		//dialog.setSize("200px", "200px");
		dialog.setText("Subtitle Parsing");
		//GWT.log(loadImg.getUrl());
		//loadImg.setVisible(true);
		VerticalPanel vpanel=new VerticalPanel();
		//Image img=new Image("../img/loadanime.gif");
		//GWT.log(img.getUrl());
		//loadImg.setVisible(true);
		vpanel.add(loadImg);
		dialog.setWidget(vpanel);
		dialog.setModal(true);
		dialog.setGlassEnabled(true);
		dialog.show();
		dialog.center();
		
		Timer timer=new Timer(){

			@Override
			public void run() {
			
				SRTParser parser=new SRTParser();
				SRTList list=parser.parse(text.split("\n"));
				dialog.hide();
				
				playerWidget.setSubLength(list.size());
				if(list.size()>0){
					preference.setSrtText(text);
					preference.setSrtSelectIndex(0);
					playerWidget.setSubIndex(0);
				}
				itemPanel.clear();
				for(int i=0;i<list.size();i++){
					SRTObject srt=list.getSRTObjectAt(i);
					SRTItemPanel panel=new SRTItemPanel(srt);
					panel.addClickHandler(selectWidgetHandler);
					itemPanel.add(panel);
				}
				initPlayerSettings();
				tab.selectTab(0);//switch to view
				//label mode
				updateNoSubtitleWarning();
				selectWidget(selectIndex);
			}};
			timer.schedule(100);
		
	}
	
	private void initPlayerSettings(){
		playerWidget.setSubIndex(0);
	}
	
	private void selectWidget(int index){
		if(index<itemPanel.getWidgetCount()){
			Widget widget=itemPanel.getWidget(index);
			setlectWidget(widget);
		}
	}
	
	private void setlectWidget(Widget widget){
		widget.addStyleName("select");
		int ind=itemPanel.getWidgetIndex(widget);
		playerWidget.setSubIndex(ind);
		SRTItemPanel srtItem=(SRTItemPanel) widget;
		SRTObject srt=srtItem.getSrt();
		timeLabel.setText(srt.getStartTime().toString()+" - "+srt.getEndTime().toString());
	}
	private void unselectAll(){
		for(int i=0;i<itemPanel.getWidgetCount();i++){
			itemPanel.getWidget(i).removeStyleName("select");
		}
	}
	
	private int calculateScrollY(int index){
		int scroll=0;
		for(int i=0;i<index;i++){
			scroll+=itemPanel.getWidget(i).getOffsetHeight();
			scroll+=itemPanel.getSpacing();
		}
		return scroll;
	}


	
	

	private Timer tmpTimer;
	@Override
	public void autoPlay(int index) {
		//TODO remove test
		tmpTimer = new Timer(){
			@Override
			public void run() {
				endPlay();
			}};
			tmpTimer.scheduleRepeating(3000);
			
		play(index);	
	}


	private void endPlay(){
		if(playerWidget.hasNext()){
			playerWidget.doNext();
			play(playerWidget.getSubIndex());
		}else{
			//TODO remove
			tmpTimer.cancel();
			playerWidget.endAutoPlay();
		}
	}

	@Override
	public void moveTo(int index) {
		
		int sat=calculateScrollY(index);
		GWT.log("scroll:"+sat);
		itemPanelScroll.setVerticalScrollPosition(sat);
		
		unselectAll();
		setlectWidget(itemPanel.getWidget(index));
	}



	@Override
	public void play(int index) {
		// TODO call tts
		if(playerWidget.isAutoPlaying()){
			//do something
			//catch TYPE:interrupted or TYPE:end call endPlay()
		}
	}



	@Override
	public void stop() {
		playerWidget.setAutoPlaying(false);
		
		//TODO call tts
		
	}
	
	public class VoiceSettings extends VerticalPanel{
		Label rateValue;
		public VoiceSettings(){
			
			rateValue=new Label();
			add(rateValue);
			final RangeSlider range=new RangeSlider(1, 50, 10);
			range.addMouseUpHandler(new MouseUpHandler() {
				@Override
				public void onMouseUp(MouseUpEvent event) {
					rateValue.setText(""+range.getValue());
				}
			});
			add(range);
			
		}
		
	}

}
