package com.project.expensetracker;
import java.util.ArrayList;
public class ExpenseManager {
    private ArrayList<Expense> expenses;
    public ExpenseManager()
    {
        expenses = new ArrayList<>();
    }
    public void addExpense(Expense expense)
    {
        expenses.add(expense);
    }
    public ArrayList<Expense> getExpenses()
    {
        return expenses;
    }
    public double getTotalExpense()
    {
        double total =0;
        for(Expense e: expenses)
        {
            total += e.getAmount();
        }
        return total ;
    }
}
