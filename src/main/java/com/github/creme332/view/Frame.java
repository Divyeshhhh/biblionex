package com.github.creme332.view;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.*;
import com.github.creme332.controller.Screen;
import com.github.creme332.model.UserType;
import com.github.creme332.utils.IconLoader;
import com.github.creme332.utils.exception.InvalidPathException;
import com.github.creme332.view.librarian.MaterialForm;
import com.github.creme332.view.librarian.OverdueLoansPage;
import com.github.creme332.view.librarian.PublisherForm;
import com.github.creme332.view.librarian.RegistrationForm;
import com.github.creme332.view.librarian.UserListPage;
import com.github.creme332.view.librarian.VendorForm;
import com.github.creme332.view.patron.Account;
import com.github.creme332.view.patron.Catalog;
import com.github.creme332.view.librarian.AuthorForm;
import com.github.creme332.view.librarian.CheckInPage;
import com.github.creme332.view.librarian.CheckOutPage;
import com.github.creme332.view.librarian.MaterialList;
import com.github.creme332.view.patron.Registration;
import com.github.creme332.view.patron.Sidebar;

/**
 * Frame of the GUI application.
 */
public class Frame extends JFrame {
    // variables for managing different screens
    private CardLayout cardLayout = new CardLayout(); // used to swap between screens
    private JPanel cardPanels = new JPanel(cardLayout); // a container for all screens

    // a map that maps a screen name to screen
    private Map<Screen, JPanel> screenMapper = new EnumMap<>(Screen.class);

    Sidebar patronSidebar = new Sidebar();

    public Frame() throws InvalidPathException {
        // set frame title
        this.setTitle("biblionex");

        // make frame resizable
        this.setResizable(true);

        // add close button to frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // set application icon
        this.setIconImage(new IconLoader().loadIcon("/icons/stack-of-books.png").getImage());

        // setup screen mapper and create screens

        // add screens visible to everyone
        screenMapper.put(Screen.SPLASH_SCREEN, new SplashScreen());
        screenMapper.put(Screen.LOGIN_SCREEN, new Login());
        screenMapper.put(Screen.FORGET_PASSWORD, new ForgotPassword());

        // add screens visible to only patrons
        screenMapper.put(Screen.PATRON_REGISTRATION_SCREEN, new Registration());
        screenMapper.put(Screen.PATRON_DASHBOARD_SCREEN, new com.github.creme332.view.patron.Dashboard());
        screenMapper.put(Screen.PATRON_LOAN_SCREEN, new com.github.creme332.view.patron.LoanPage());
        screenMapper.put(Screen.PATRON_ACCOUNT_SCREEN, new Account());
        screenMapper.put(Screen.PATRON_CATALOG_SCREEN, new Catalog());

        // add screens visible to only librarians
        screenMapper.put(Screen.LIBRARIAN_DASHBOARD_SCREEN, new com.github.creme332.view.librarian.Dashboard());
        screenMapper.put(Screen.LIBRARIAN_REGISTRATION_SCREEN, new RegistrationForm());
        screenMapper.put(Screen.LIBRARIAN_CATALOGING_SCREEN, new MaterialForm());
        screenMapper.put(Screen.LIBRARIAN_CHECKIN_SCREEN, new CheckInPage());
        screenMapper.put(Screen.LIBRARIAN_PATRON_LIST_SCREEN, new UserListPage(UserType.PATRON));
        screenMapper.put(Screen.LIBRARIAN_LIBRARIAN_LIST_SCREEN, new UserListPage(UserType.LIBRARIAN));
        screenMapper.put(Screen.LIBRARIAN_VENDOR_SCREEN, new VendorForm());
        screenMapper.put(Screen.LIBRARIAN_AUTHOR_SCREEN, new AuthorForm());
        screenMapper.put(Screen.LIBRARIAN_PUBLISHER_SCREEN, new PublisherForm());
        screenMapper.put(Screen.LIBRARIAN_CHECKOUT_SCREEN, new CheckOutPage());
        screenMapper.put(Screen.LIBRARIAN_OVERDUE_LOANS_SCREEN, new OverdueLoansPage()); // Add OverdueLoansPage
        screenMapper.put(Screen.LIBRARIAN_MATERIAL_LIST_SCREEN, new MaterialList());

        // add screens to cardPanels
        for (Map.Entry<Screen, JPanel> entry : screenMapper.entrySet()) {
            cardPanels.add(entry.getValue(), entry.getKey().name());
        }

        // hide patron sidebar by default
        patronSidebar.setVisible(false);

        // setup frame layout
        setLayout(new BorderLayout());
        add(patronSidebar, BorderLayout.WEST);
        add(cardPanels, BorderLayout.CENTER);

        // display frame in the middle of the screen
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JPanel getPage(Screen name) {
        JPanel screen = screenMapper.get(name);
        if (screen == null) {
            System.out.println("Invalid screen: " + name.name());
            System.exit(0);
        }
        return screen;
    }

    public void switchToScreen(Screen screenName) {
        cardLayout.show(cardPanels, screenName.name());
        patronSidebar
                .setVisible(screenName.name().startsWith("PATRON_") && screenName != Screen.PATRON_REGISTRATION_SCREEN);
    }

    public Sidebar getSidebar() {
        return patronSidebar;
    }
}
