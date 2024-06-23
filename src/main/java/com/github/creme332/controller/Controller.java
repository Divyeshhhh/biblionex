package com.github.creme332.controller;

import com.github.creme332.controller.librarian.RegistrationController;
import com.github.creme332.controller.librarian.LibrarianListPageController;
import com.github.creme332.controller.librarian.PatronListPageController;
import com.github.creme332.controller.patron.RegisterController;
import com.github.creme332.model.AppState;
import com.github.creme332.utils.exception.InvalidPathException;
import com.github.creme332.view.*;
import com.github.creme332.view.librarian.RegistrationForm;
import com.github.creme332.view.librarian.LibrarianListPage;
import com.github.creme332.view.librarian.PatronListPage;
import com.github.creme332.view.patron.Registration;

/**
 * Main controller of application.
 */
public class Controller {
    private AppState app = new AppState();

    public Controller() {
        Frame frame = null;
        FrameController frameController = null;
        try {
            frame = new Frame();
            frameController = new FrameController(app, frame);
        } catch (InvalidPathException e) {
            e.printStackTrace();
            System.exit(0);
        }

        Registration patronRegistrationPage = (Registration) frame.getPage(Screen.PATRON_REGISTRATION_SCREEN);
        new RegisterController(app, patronRegistrationPage);

        new RegistrationController(app, (RegistrationForm) frame.getPage(Screen.LIBRARIAN_REGISTRATION_SCREEN));

        new PatronListPageController(app, (PatronListPage) frame.getPage(Screen.PATRON_LIST_SCREEN));

        new LibrarianListPageController (app, (LibrarianListPage) frame.getPage(Screen.LIBRARIAN_LIST_SCREEN));

        Login loginPage = (Login) frame.getPage(Screen.LOGIN_SCREEN);
        new LoginController(app, loginPage);

        // initialize controller for patron dashboard
        new com.github.creme332.controller.patron.DashboardController(app,
                (com.github.creme332.view.patron.Dashboard) frame
                        .getPage(Screen.PATRON_DASHBOARD_SCREEN));

        // initialize controller for librarian dashboard
        new com.github.creme332.controller.librarian.DashboardController(app,
                (com.github.creme332.view.librarian.Dashboard) frame
                        .getPage(Screen.LIBRARIAN_DASHBOARD_SCREEN));

        if (app.getAutoLogin() != null) {
            // auto login enabled
            app.autoLogin(app.getAutoLogin());
        } else {
            // start application normally
            frameController.playAnimation();
        }
    }
}