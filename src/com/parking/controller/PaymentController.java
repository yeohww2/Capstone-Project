package com.parking.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class PaymentController extends NavigationController {

    @FXML
    private Label paymentStatusLabel;
    @FXML
    private TextField paymentAmountField;
    @FXML
    private Button payButton;

    // Initialize any specific logic for the Payment page
    public void initialize() {
        // For example, load outstanding balances or payment details
    }

    @FXML
    private void processPayment() {
        // Logic to process the payment using the amount in paymentAmountField
        String amount = paymentAmountField.getText();
        // Validate and process payment logic here
        paymentStatusLabel.setText("Payment of $" + amount + " processed.");
    }

    // Add any other methods specific to Payment here
}
