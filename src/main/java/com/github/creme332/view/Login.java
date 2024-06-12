package com.github.creme332.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;

public class Login extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton registerButton;

    private final Border redBorder = new LineBorder(Color.red);

    public Login() {
        this.setLayout(new GridBagLayout());

        JPanel main = new JPanel(); // a container which will appear in middle of screen
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        add(main);

        JPanel imgContainer = new JPanel(new GridBagLayout());
        imgContainer.setAlignmentX(Component.LEFT_ALIGNMENT);// 0.0

        JLabel image = new JLabel();
        FontIcon icon = FontIcon.of(BootstrapIcons.PERSON_CIRCLE, 200);
        icon.setIconColor(Color.white);
        image.setIcon(icon);
        imgContainer.add(image);
        main.add(imgContainer);

        // add gap between imgContainer and formContainer
        main.add(Box.createRigidArea(new Dimension(0, 60)));

        JPanel formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setAlignmentX(Component.LEFT_ALIGNMENT);// 0.0

        main.add(formContainer);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);// 0.0

        formContainer.add(emailLabel);

        // Create text field for email
        emailField = new JTextField(20);
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);// 0.0

        formContainer.add(emailField);

        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create JLabel for password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);// 0.0

        formContainer.add(passwordLabel);

        // Create text field for password
        passwordField = new JPasswordField(20);
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);// 0.0

        formContainer.add(passwordField);

        formContainer.add(Box.createRigidArea(new Dimension(0, 60)));

        // Create login button
        JPanel buttonContainer = new JPanel(new GridBagLayout());
        buttonContainer.setAlignmentX(Component.LEFT_ALIGNMENT);// 0.0

        loginButton = new JButton("Login");

        loginButton.setPreferredSize(new Dimension(500, 50));
        buttonContainer.add(loginButton);
        formContainer.add(buttonContainer);

        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create registration button
        registerButton = new JButton("Create a new account");
        registerButton.setAlignmentX(Component.LEFT_ALIGNMENT);// 0.0

        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setBackground(null);
        formContainer.add(registerButton);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public void showError() {
        emailField.setBorder(redBorder);
        passwordField.setBorder(redBorder);
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
}
