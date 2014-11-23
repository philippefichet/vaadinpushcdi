/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philippefichet.vaadincdipush;

import com.philippefichet.vaadincdipush.ui.MainUI;
import com.philippefichet.vaadincdipush.view.FirstView;
import com.vaadin.server.ClientConnector;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author glopinous
 */
@ApplicationScoped
public class Chat {
    private final Map<String, FirstView> userMap = new ConcurrentHashMap<>();
    
    public Set<String> getUsernameConnected() {
        return userMap.keySet();
    }
    
    public boolean attach(FirstView firstView) {
        if (loginFree(firstView.getLogin())) {
            userMap.put(firstView.getLogin(), firstView);
            firstView.getUI().addDetachListener((ClientConnector.DetachEvent event) -> {
                userMap.forEach((name, view) -> {
                    view.receiveMessage(null, "Byebye \"" + firstView.getLogin() + "\" !");
                });
                userMap.remove(firstView.getLogin());
                userMap.forEach((l, v) -> {
                    v.removeUser(firstView.getLogin());
                });
            });
            
            sendNewUser(firstView.getLogin());
            
            return true;
        } else {
            return false;
        }
    }
    
    public boolean loginFree(String login) {
        return !userMap.containsKey(login);
    }
    
    public void detach(FirstView firstView) {
        userMap.remove(firstView.getLogin());
    }
    
    public void sendMessage(FirstView firstView, String message) {
        userMap.forEach((name, view) -> {
            view.receiveMessage(firstView.getLogin(), message);
        });
    }
    
    public void sendMessage(String message) {
        userMap.forEach((name, view) -> {
            view.receiveMessage(null, message);
        });
    }
    
    public void rename(String newLogin, FirstView view) {
        userMap.remove(view.getLogin());
        userMap.put(newLogin, view);
        userMap.forEach((l, v) -> {
            v.renameNewUser(view.getLogin(), newLogin);
        });
        sendMessage("\"" + view.getLogin() + "\" is now \"" + newLogin + "\"");
    }

    private void sendNewUser(String login) {
        userMap.forEach((l, view) -> {
            view.newUser(login);
        });
    }
}
