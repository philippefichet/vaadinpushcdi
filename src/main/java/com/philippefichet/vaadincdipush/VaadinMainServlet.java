/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.philippefichet.vaadincdipush;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.server.VaadinCDIServlet;
import com.vaadin.server.VaadinServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author philippe
 */
@WebServlet(urlPatterns = {"/*"}, asyncSupported = true, loadOnStartup = 1)
@VaadinServletConfiguration(ui = com.philippefichet.vaadincdipush.ui.MainUI.class, productionMode = true)
public class VaadinMainServlet extends VaadinCDIServlet {
    
}
