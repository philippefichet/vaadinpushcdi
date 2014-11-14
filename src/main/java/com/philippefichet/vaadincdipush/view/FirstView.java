/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philippefichet.vaadincdipush.view;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;

/**
 *
 * @author glopinous
 */
@CDIView()
public class FirstView extends VerticalLayout implements View{

    @PostConstruct
    public void init() {
        Button next = new Button("next");
        next.addClickListener((eventClick) -> {
            getUI().getNavigator().navigateTo("next");
        });
        addComponent(next);
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
    }
}
