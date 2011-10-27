package com.akjava.gwt.subplayer.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.akjava.gwt.chrometts.client.ChromeTts;
import com.akjava.gwt.chrometts.client.TtsEvent;
import com.akjava.gwt.chrometts.client.TtsOption;
import com.akjava.gwt.chrometts.client.TtsVoice;
import com.akjava.gwt.chrometts.client.ChromeTts.GetVoiceHandler;
import com.akjava.gwt.chrometts.client.ChromeTts.SpeakHandler;
import com.akjava.gwt.chrometts.client.ChromeTts.TtsEventHandler;
import com.akjava.gwt.subplayer.client.resources.Binder;
import com.akjava.gwt.subplayer.client.ui.HTML5InputRange;
import com.akjava.subtitle.client.srt.SRTList;
import com.akjava.subtitle.client.srt.SRTObject;
import com.akjava.subtitle.client.srt.SRTParser;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.text.shared.Renderer;
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
import com.google.gwt.user.client.ui.ValueListBox;
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
		tab.setHeight("600px");
		
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
		DisclosurePanel vs=new DisclosurePanel("Voice Settings");
		root.add(vs);
		voiceSettings = new VoiceSettings();
		vs.add(voiceSettings);
		
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
		Button play=new Button("PLAY");
		play.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				int index=itemPanel.getWidgetIndex(SRTItemPanel.this);
				play(index);
			}
		});
		hpanel.add(play);
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
	private SRTItemPanel getSelectWidget(){
		return (SRTItemPanel) itemPanel.getWidget(playerWidget.getSubIndex());
	}
	private SRTItemPanel getWidget(int index){
		return (SRTItemPanel) itemPanel.getWidget(index);
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
	private VoiceSettings voiceSettings;
	@Override
	public void autoPlay(int index) {			
		play(index);	
	}


	private void endPlay(){
		if(playerWidget.hasNext()){
			playerWidget.doNext();
			play(playerWidget.getSubIndex());
		}else{
			playerWidget.endAutoPlay();
		}
	}

	@Override
	public void moveTo(int index) {
		
		int sat=calculateScrollY(index);
		
		itemPanelScroll.setVerticalScrollPosition(sat);
		
		unselectAll();
		setlectWidget(itemPanel.getWidget(index));
	}



	TtsEventHandler eventHandler;
	@Override
	public void play(int index) {
		// TODO call tts
		
		if(eventHandler==null){
			eventHandler=new TtsEventHandler() {
				@Override
				public void event(TtsEvent event) {
					if(event.getType().equals(TtsEvent.TYPE_END) || event.getType().equals(TtsEvent.TYPE_CANCELLED)
							|| event.getType().equals(TtsEvent.TYPE_INTERRUPTED) || event.getType().equals(TtsEvent.TYPE_ERROR)
					){
						//
						endPlay();
					}
				}
			};
		};
		
		
		
		if(ChromeTts.isAvaialbe()){
		String text=getWidget(index).getSrt().getText();
		TtsOption option=ChromeTts.options().rate(voiceSettings.getRate()).pitch(voiceSettings.getPitch()).voiceName(voiceSettings.getVoiceName());
		
		if(playerWidget.isAutoPlaying()){
			option.onEvent(eventHandler);
		}
		
		
		ChromeTts.speak(text, option, null);
		}
	}



	@Override
	public void stop() {
		playerWidget.setAutoPlaying(false);
		ChromeTts.stop();
	}
	
	public class VoiceSettings extends VerticalPanel{
		Label rateValue;
		private Label pitchValue;
		private HTML5InputRange pitchRange;
		private HTML5InputRange rateRange;
		private ValueListBox<String> voiceNames;
		public VoiceSettings(){
			
			voiceNames = new ValueListBox<String>(new Renderer<String>() {

				@Override
				public String render(String object) {
					return object;
				}

				@Override
				public void render(String object, Appendable appendable)
						throws IOException {
				}
			});
			voiceNames.addValueChangeHandler(new ValueChangeHandler<String>() {
				
				@Override
				public void onValueChange(ValueChangeEvent<String> event) {
					String value=event.getValue();
					preference.setVoiceName(value);
				}
			});
			add(voiceNames);
			updateVoiceNames();
			
			
			HorizontalPanel voices=new HorizontalPanel();
			add(voices);
			voices.add(new Label("Voices:"));
			voices.add(voiceNames);
			
			Button reset=new Button("Reset");
			voices.add(reset);
			reset.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					
					
					preference.setVoiceName("");
					preference.setVoiceRate(1);
					preference.setVoicePitch(1);
					
					updateVoiceNames();
					
					rateRange.setValue(10);
					rateValue.setText("RATE:"+1);
					pitchRange.setValue(10);
					pitchValue.setText("PITCH:"+1);
				}
			});
			
			
			HorizontalPanel settings=new HorizontalPanel();
			add(settings);
			rateValue=new Label();
			rateValue.setWidth("60px");
			
			
			settings.add(rateValue);
			int drv=(int) (preference.getVoiceRate()*10);
			rateValue.setText("RATE:"+drv);
			rateRange = new HTML5InputRange(1, 50, drv);
			rateRange.setWidth("100px");
			rateRange.addMouseUpHandler(new MouseUpHandler() {
				@Override
				public void onMouseUp(MouseUpEvent event) {
					double rv=(double)rateRange.getValue()/10;
					rateValue.setText("RATE:"+rv);
					preference.setVoiceRate(rv);
				}
			});
			settings.add(rateRange);
			
			pitchValue=new Label();
			pitchValue.setWidth("60px");
			
			
			settings.add(pitchValue);
			int dpv=(int) (preference.getVoicePitch()*10);
			pitchValue.setText("PITCH:"+dpv);
			pitchRange = new HTML5InputRange(1, 20, dpv);
			pitchRange.setWidth("100px");
			pitchRange.addMouseUpHandler(new MouseUpHandler() {
				@Override
				public void onMouseUp(MouseUpEvent event) {
					double pv=(double)pitchRange.getValue()/10;
					pitchValue.setText("PITCH:"+pv);
					preference.setVoicePitch(pv);
				}
			});
			settings.add(pitchRange);
			
			
		}
		public String getVoiceName(){
			return voiceNames.getValue();
		}
		private void updateVoiceNames(){
			if(ChromeTts.isAvaialbe()){
				ChromeTts.getVoices(new GetVoiceHandler() {
					
					@Override
					public void voices(JsArray<TtsVoice> voices) {
						String dv=preference.getVoiceName();
						List<String> names=new ArrayList<String>();
						for(int i=0;i<voices.length();i++){
							names.add(voices.get(i).getVoiceName());
						}
						String vname=names.get(0);
						if(names.contains(dv)){
							vname=dv;
						}
						voiceNames.setValue(vname);
						voiceNames.setAcceptableValues(names);
					}
				});
				}
		}
		
		public double getPitch(){
			return (double)pitchRange.getValue()/10;
		}
		public double getRate(){
			return (double)rateRange.getValue()/10;
		}
	}

}
