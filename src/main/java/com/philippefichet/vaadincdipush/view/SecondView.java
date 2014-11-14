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
@CDIView("next")
public class SecondView extends VerticalLayout implements View{

    @PostConstruct
    public void init() {
        Button prev = new Button("prev");
        prev.addClickListener((eventClick) -> {
            getUI().getNavigator().navigateTo("");
        });
        addComponent(prev);
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
    }
}
