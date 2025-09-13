
package com.project.expensetracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

    public class GUI {
        private JFrame frame;
        private JTextField categoryField, amountField, dateField;
        private JTable table;
        private DefaultTableModel tableModel;
        private ExpenseManager manager = new ExpenseManager();
        private JLabel totalLabel;

        public GUI() {

            // Frame setup
            frame = new JFrame("Expense Tracker");
            frame.setSize(600, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            // Input panel (top)
            JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
            inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            inputPanel.add(new JLabel("Category:"));
            categoryField = new JTextField();
            inputPanel.add(categoryField);

            inputPanel.add(new JLabel("Amount:"));
            amountField = new JTextField();
            inputPanel.add(amountField);

            inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
            dateField = new JTextField();
            inputPanel.add(dateField);

            JButton addButton = new JButton("Add Expense");
            inputPanel.add(addButton);

            frame.add(inputPanel, BorderLayout.NORTH);

            // Table (center)
            tableModel = new DefaultTableModel(new Object[]{"Category", "Amount", "Date"}, 0);
            table = new JTable(tableModel);
            frame.add(new JScrollPane(table), BorderLayout.CENTER);

            // Total panel (bottom)
            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            totalLabel = new JLabel("Total Expenses: ₹" + manager.getTotalExpense());
            bottomPanel.add(totalLabel);
            frame.add(bottomPanel, BorderLayout.SOUTH);

            // Load existing expenses from CSV
            loadExistingExpenses();

            // Button action
            addButton.addActionListener((ActionEvent e) -> {
                String category = categoryField.getText();
                String amountText = amountField.getText();
                String date = dateField.getText();

                if (category.isEmpty() || amountText.isEmpty() || date.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    double amount = Double.parseDouble(amountText);
                    Expense expense = new Expense(category, amount, date);
                    manager.addExpense(expense);

                    tableModel.addRow(new Object[]{category, amount, date});
                    updateTotalLabel();

                    // Clear fields
                    categoryField.setText("");
                    amountField.setText("");
                    dateField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Amount must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            frame.setVisible(true);
        }

        private void loadExistingExpenses() {
            for (Expense exp : manager.getExpenses()) {
                tableModel.addRow(new Object[]{exp.getCategory(), exp.getAmount(), exp.getDate()});
            }
            updateTotalLabel();
        }

        private void updateTotalLabel() {
            totalLabel.setText("Total Expenses: ₹" + manager.getTotalExpense());
        }
    }


