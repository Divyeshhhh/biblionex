package com.github.creme332.controller.librarian;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.github.creme332.controller.Screen;
import com.github.creme332.model.AppState;
import com.github.creme332.model.Loan;
import com.github.creme332.model.User;
import com.github.creme332.model.UserType;
import com.github.creme332.model.Librarian;
import com.github.creme332.view.librarian.CheckInPage;

public class CheckInController implements PropertyChangeListener {
    private AppState app;
    private CheckInPage view;
    private Librarian librarian;

    public CheckInController(AppState app, CheckInPage view) {
        this.app = app;
        this.view = view;
        app.addPropertyChangeListener(this);

        view.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchLoans();
            }
        });

        view.getBackButton().addActionListener(e -> app.setCurrentScreen(Screen.LIBRARIAN_DASHBOARD_SCREEN));

        addTableButtonListeners();

        Thread th = new Thread() {
            @Override
            public void run() {
                displayActiveLoans();
            }
        };
        th.start();
    }

    /**
     * Add listeners to check in and renew buttons.
     */
    private void addTableButtonListeners() {
        JTable table = view.getTable();
        CheckInPage.ActionCellRenderer rendererEditor = view.getActionCellRenderer();

        rendererEditor.setRenewButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    int loanId = (int) table.getValueAt(selectedRow, 0);
                    System.out.println("Renew Loan ID " + loanId);
                    renewLoan(loanId);
                }
            }
        });

        rendererEditor.setCheckInButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int loanId = (int) table.getValueAt(selectedRow, 0);
                    System.out.println("Check in Loan ID " + loanId);
                    checkInLoan(loanId);
                }
            }
        });
    }

    private void displayActiveLoans() {
        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);

        try {
            List<Loan> loans = Loan.findAllActive();
            for (Loan loan : loans) {
                Object[] rowData = { loan.getLoanId(), loan.getPatronId(), loan.getBarcode(),
                        loan.getDueDate(), loan.getRenewalCount(), "Action" };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error occurred while fetching loans.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void searchLoans() {
        String barcodeText = view.getBarcodeField().getText().trim();

        if (barcodeText.length() == 0) {

        }
        int barcode;

        try {
            barcode = Integer.parseInt(barcodeText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Please enter a valid barcode.", "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel tableModel = view.getTableModel();
        tableModel.setRowCount(0);

        try {
            Loan loan = Loan.findActiveLoanByBarcode(barcode);
            if (loan != null) {
                Object[] rowData = { loan.getLoanId(), loan.getPatronId(), loan.getBarcode(),
                        loan.getDueDate(), loan.getRenewalCount(), "Action" };
                tableModel.addRow(rowData);
            } else {
                JOptionPane.showMessageDialog(view, "No active loans found for the given barcode.",
                        "No Results",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error occurred while fetching loans.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void renewLoan(int loanId) {
        try {
            Loan loan = Loan.findById(loanId);
            Librarian loggedInLibrarian = (Librarian) app.getLoggedInUser();
            loggedInLibrarian.renewLoan(loan);
            JOptionPane.showMessageDialog(view, "Loan renewed successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            displayActiveLoans();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Renewal Limit Reached", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error occurred while renewing the loan.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void checkInLoan(int loanId) {
        try {
            Loan loan = Loan.findById(loanId);
            Librarian loggedInLibrarian = (Librarian) app.getLoggedInUser();
            loggedInLibrarian.checkIn(loan);
            JOptionPane.showMessageDialog(view, "Loan checked in successfully.", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            displayActiveLoans();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error occurred while checking in the loan.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (propertyName.equals("loggedInUser")) {

            // ignore event if logged in user is not a librarian
            User newUser = (User) evt.getNewValue();
            if (newUser == null
                    || newUser.getUserType() != UserType.LIBRARIAN)
                return;

            // initialize librarian
            librarian = (Librarian) newUser;
            System.out.println(librarian);
        }
    }
}
