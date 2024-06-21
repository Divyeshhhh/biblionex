package com.github.creme332.controller.librarian;

import com.github.creme332.model.AppState;
import com.github.creme332.view.librarian.Dashboard;
import com.github.creme332.controller.Screen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardController {
    private AppState app;
    private Dashboard dashboard;

    public DashboardController(AppState app, Dashboard dashboard) {
        this.app = app;
        this.dashboard = dashboard;

        dashboard.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.setCurrentScreen(Screen.LOGIN_SCREEN);
            }
        });

        dashboard.getCheckInButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Check in button click
            }
        });

        dashboard.getMaterialsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Materials button click
            }
        });

        dashboard.getCatalogingButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Cataloging button click
            }
        });

        dashboard.getCheckOutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Check out button click
            }
        });

        dashboard.getPatronsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Patrons button click
            }
        });

        dashboard.getOverduesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Overdues button click
            }
        });

        dashboard.getRenewButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Renew button click
            }
        });

        dashboard.getLibrariansButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Librarians button click
            }
        });

        dashboard.getAcquisitionsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Acquisitions button click
            }
        });

        dashboard.getReportsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Reports button click
            }
        });
    }
}
