/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philippefichet.vaadincdipush.view;

import com.philippefichet.vaadincdipush.Chat;
import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author glopinous
 */
@CDIView("")
public class FirstView extends VerticalLayout implements View{

    @Inject
    private Chat chat;
    
    private HorizontalLayout loginLayout = new HorizontalLayout();
    
    private Panel messagePanel = new Panel();
    
    private HorizontalLayout centerLayout = new HorizontalLayout();
    
    private VerticalLayout messageLayout = new VerticalLayout();
    
    private HorizontalLayout sendLayout = new HorizontalLayout();
    
    private Panel listUserPanel = new Panel("Users");
    
    private VerticalLayout listUserLayout = new VerticalLayout();
    
    private TextField chatMessage = new TextField();
    
    private Button sendMessage = new Button("Send");
    
    private String login = null;
    
    private TextField loginChoose;
    
    private Button attach;
    
    @PostConstruct
    public void init() {
        setHeight("100%");
        loginLayout = new HorizontalLayout();
        Label loginLabel = new Label("Login");
        loginChoose = new TextField();
        attach = new Button("Connect");
        attach.addClickListener((Button.ClickEvent event) -> {
            if(chat.loginFree(loginChoose.getValue())) {
                if (login == null) {
                    login = loginChoose.getValue();
                    chat.getUsernameConnected().forEach((l) -> {
                        newUser(l);
                    });
                    chat.attach(this);
                } else {
                    chat.rename(loginChoose.getValue(), this);
                    login = loginChoose.getValue();
                }
                attach.setStyleName(ValoTheme.BUTTON_FRIENDLY);
                Notification.show("Login \"" + loginChoose.getValue() + "\" valid.",null, Notification.Type.HUMANIZED_MESSAGE);

                messagePanel.setVisible(true);
                sendLayout.setVisible(true);
                
            } else {
                Notification.show("Login \"" + loginChoose.getValue() + "\" already exist.",null, Notification.Type.ERROR_MESSAGE);
            }
        });
        
        chatMessage.setWidth("100%");
        sendLayout.setWidth("100%");
        sendMessage.setWidthUndefined();
        sendLayout.addComponent(chatMessage);
        sendLayout.addComponent(sendMessage);
        sendLayout.setExpandRatio(chatMessage, 100);
        sendMessage.addClickListener((event) -> {
            chat.sendMessage(this, chatMessage.getValue());
            chatMessage.setValue("");
        });
        
        
        Button next = new Button("next");
        next.addClickListener((eventClick) -> {
            getUI().getNavigator().navigateTo("next");
        });
        
        loginLayout.addComponent(loginLabel);
        loginLayout.addComponent(loginChoose);
        loginLayout.addComponent(attach);
        loginLayout.setComponentAlignment(loginLabel, Alignment.MIDDLE_RIGHT);
        loginLayout.setComponentAlignment(loginChoose, Alignment.MIDDLE_CENTER);
        loginLayout.setComponentAlignment(attach, Alignment.MIDDLE_LEFT);
        
        loginLayout.setWidth("100%");
        loginLabel.setWidthUndefined();
        loginChoose.setWidth("100%");
        attach.setWidthUndefined();
        messagePanel.setHeight("100%");
        listUserPanel.setStyleName(ValoTheme.PANEL_WELL);
        listUserPanel.setContent(listUserLayout);
        listUserPanel.setHeight("100%");
        centerLayout.setHeight("100%");
        centerLayout.addComponent(listUserPanel);
        centerLayout.addComponent(messageLayout);
        centerLayout.setExpandRatio(messageLayout, 100);
        messagePanel.setContent(centerLayout);
        addComponent(loginLayout);
        addComponent(messagePanel);
        addComponent(sendLayout);
        
        messagePanel.setVisible(false);
        sendLayout.setVisible(false);
        
        setExpandRatio(messagePanel, 100);
    }
    
    public String getLogin() {
        return login;
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        getUI().addShortcutListener(new ShortcutListener("enter", ShortcutAction.KeyCode.ENTER, null) {
            
            @Override
            public void handleAction(Object sender, Object target) {
                if(target.equals(loginChoose)) {
                    attach.click();
                } else if (target.equals(chatMessage)) {
                    sendMessage.click();
                }
            }
        });
    }
    
    public void receiveMessage(String login, String message) {
        getUI().access(() -> {
            Label msgAdded = null;
            if (login == null) {
                msgAdded = new Label(message,ContentMode.HTML);
                msgAdded.addStyleName(ValoTheme.LABEL_BOLD);
            } else  {
                msgAdded = new Label(login  + ": " + message);
            }
            messageLayout.addComponent(msgAdded);
            messagePanel.setScrollTop(messageLayout.getComponentCount()*100);
        });
    }

    public void newUser(String login) {
        Label user = new Label(login);
        listUserLayout.addComponent(user);
        listUserLayout.setComponentAlignment(user, Alignment.MIDDLE_CENTER);
    }
    public void renameNewUser(String login, String newLogin) {
        for (int i = 0; i < listUserLayout.getComponentCount(); i++) {
            Label l = (Label)listUserLayout.getComponent(i);
            if (l.getValue().equals(login)) {
                l.setValue(newLogin);
                break;
            }
        }
    }
    public void removeUser(String login) {
        for (int i = 0; i < listUserLayout.getComponentCount(); i++) {
            Label l = (Label)listUserLayout.getComponent(i);
            if (l.getValue().equals(login)) {
                listUserLayout.removeComponent(l);
                break;
            }
        }
    }
}
