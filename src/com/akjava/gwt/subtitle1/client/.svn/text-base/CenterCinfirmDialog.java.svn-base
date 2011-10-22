package com.akjava.gwt.subtitle1.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public  abstract class CenterCinfirmDialog extends DialogBox {

	public abstract void clickOK();
    public CenterCinfirmDialog(String title,String text) {
      // Set the dialog box's caption.
    
      setText(title);
      VerticalPanel p=new VerticalPanel();
      p.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
      Label l=new Label();
      l.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
      l.setText(text);
      l.setSize("320px", "120px");
     
      p.add(l);
      // DialogBox is a SimplePanel, so you have to set its widget property to
      // whatever you want its contents to be.
      Button ok = new Button("OK");
      
      ok.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
        	clickOK();
          CenterCinfirmDialog.this.removeFromParent();
        }
      });
      
      
      
      HorizontalPanel buttons=new HorizontalPanel();
      buttons.setSpacing(10);
      buttons.add(ok);
      
      
     Button cancel = new Button("Cancel");
      
     cancel.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          CenterCinfirmDialog.this.removeFromParent();
        }
      });
      
     buttons.add(cancel);
      p.add(buttons);
      
      setWidget(p);
    }
    
    public void show() { 
    	
        super.show(); 
        int left = ( Window.getClientWidth( ) - getOffsetWidth( )) / 2; 
        int top = ( Window.getClientHeight( ) - getOffsetHeight( )) / 2; 
        setPopupPosition( left, top+Window.getScrollTop()); 
    }


  }
