package com.akjava.gwt.subplayer.client;

import com.akjava.subtitle.client.srt.SRTList;
import com.akjava.subtitle.client.srt.SRTObject;
import com.akjava.subtitle.client.srt.SRTParser;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SubPlayer implements EntryPoint {

	private int index=0;
	private VerticalPanel itemPanel;
	private LoadPanel loadPanel;
	@Override
	public void onModuleLoad() {
	
		tab = new TabLayoutPanel(1.5, Unit.EM);
		
		VerticalPanel root=new VerticalPanel();
		root.setWidth("100%");
		//root.setHeight("200px");
		RootLayoutPanel.get().add(tab);
		 tab.add(root, "PLAY");
		 
		 loadPanel = new LoadPanel();
		 loadPanel.setWidth("100%");
		 tab.add(loadPanel, "LOAD");
		
		itemPanel = new VerticalPanel();
		itemPanel.setSpacing(8);
		
		final ScrollPanel scroll=new ScrollPanel(itemPanel);
		scroll.setWidth("100%");
		scroll.setHeight("400px");
		root.add(scroll);
		
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
		
		Button bt=new Button("check");
		root.add(bt);
		bt.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
			int csize=itemPanel.getWidgetCount();	
			for(int i=0;i<csize;i++){
				Widget widget=itemPanel.getWidget(i);
				GWT.log("item:"+i+","+widget.getAbsoluteLeft()+","+widget.getAbsoluteTop()+","+widget.getOffsetHeight());
				}
			}
		});
		
		Button next=new Button("next");
		root.add(next);
		next.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
			index++;
			if(index>=itemPanel.getWidgetCount()){
				index=0;
			}
			int sat=calculateScrollY(index);
			GWT.log("scroll:"+sat);
			scroll.setVerticalScrollPosition(sat);
			
			unselectAll();
			setlectWidget(itemPanel.getWidget(index));
			}
		});
		
		
		DisclosurePanel ds=new DisclosurePanel("show subtitle time start - end");
		ds.add(new Label("0:0:0 - 0:0:12"));
		root.add(ds);
		
		selectWidgetHandler=new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				unselectAll();
				setlectWidget((Widget) event.getSource());
			}
		};
	}
	
	public class SRTItemPanel extends FocusPanel{
		SRTObject srt;
	public SRTItemPanel(SRTObject srt){
		this.srt=srt;
		HTMLPanel html=new HTMLPanel(srt.getText().replace("\n", "<br/>"));
		setWidget(html);
	}
	}
	
	ClickHandler selectWidgetHandler;
	private TabLayoutPanel tab;
	
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
				loadSrt();
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
		
		
	}
	
	private void clearSrt() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	private void loadSrt() {
		String text=loadPanel.getText();
		SRTParser parser=new SRTParser();
		SRTList list=parser.parse(text.split("\n"));
		itemPanel.clear();
		for(int i=0;i<list.size();i++){
			SRTObject srt=list.getSRTObjectAt(i);
			SRTItemPanel panel=new SRTItemPanel(srt);
			panel.addClickHandler(selectWidgetHandler);
			itemPanel.add(panel);
		}
		initPlayerSettings();
		tab.selectTab(0);
	}
	
	private void initPlayerSettings(){
		index=0;
	}
	
	private void setlectWidget(Widget widget){
		widget.addStyleName("select");
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

}
