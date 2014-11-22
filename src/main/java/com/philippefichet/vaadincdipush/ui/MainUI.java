/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philippefichet.vaadincdipush.ui;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import javax.inject.Inject;
import com.philippefichet.vaadincdipush.view.FirstView;
import com.philippefichet.vaadincdipush.view.SecondView;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import javax.inject.Inject;

/**
 *
 * @author glopinous
 */
@CDIUI("")
@Theme(value = "valo")
@Push()
public class MainUI extends UI {
    @Inject
    private CDIViewProvider viewProvider;
    
    @Override
    public void init(VaadinRequest request) {

        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.addView("", FirstView.class);
        navigator.addView("second", SecondView.class);
    }
}
