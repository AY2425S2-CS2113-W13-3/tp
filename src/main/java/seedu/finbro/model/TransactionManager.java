package seedu.finbro.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;

/**
 * Manages transactions in the FinBro application.
 */
public class TransactionManager {
    private final List<Transaction> transactions;

    /**
     * Constructs a TransactionManager with an empty list of transactions.
     */
    public TransactionManager() {
        this.transactions = new ArrayList<>();
    }

    /**
     * Adds a transaction to the manager.
     *
     * @param transaction The transaction to add
     */
    public void addTransaction(Transaction transaction) {
        assert transaction != null : "Cannot add null transaction";
        transactions.add(transaction);
    }

    // TODO Deletes a transaction at the specified index.

    /**
     * Lists all transactions in reverse chronological order.
     *
     * @return List of all transactions in reverse chronological order
     */
    public List<Transaction> listTransactions() {
        assert transactions != null : "Transactions list cannot be null";
        // Sort by date in reverse chronological order
        List<Transaction> sortedTransactions = new ArrayList<>(transactions);
        Collections.sort(sortedTransactions, (t1, t2) -> t2.getDate().compareTo(t1.getDate()));
        return sortedTransactions;
    }

    // TODO Lists transactions from a specific date in reverse chronological order.

    // TODO Searches for transactions whose descriptions contain any of the given keywords.

    /**
     * Filters transactions made between the specified start date and end date
     * @param startDate the start date specified by the user for filtering
     * @param endDate the end date specified by the user for filtering
     * @return a list of filtered transactions
     */
    // TODO Filters transactions between the specified start and end dates.
    public ArrayList<Transaction> getFilteredTransactions(LocalDate startDate, LocalDate endDate) {
        assert startDate != null : "Start date cannot be null";
        assert endDate != null : "End date cannot be null";
        assert !startDate.isAfter(endDate) : "Start date cannot be after end date";
        return transactions.stream()
                .filter(t -> (t.getDate().isEqual(startDate) || t.getDate().isAfter(startDate)) &&
                        (t.getDate().isEqual(endDate) || t.getDate().isBefore(endDate)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * @return stream of all transactions that contain keyword
     */
    public ArrayList<Transaction> getTransactionsContainingKeyword(String keyword) {
        assert keyword != null : "Search keyword cannot be null";
        assert !keyword.trim().isEmpty() : "Search keyword cannot be empty";
        return transactions.stream()
                .filter(t -> (t.getDescription().contains(keyword)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * @return stream of all transactions that have exact same name and amount
     */
    public ArrayList<Transaction> getTransactionDuplicates(String description, double amount) {
        assert description != null : "Description cannot be null";
        assert amount > 0 : "Amount must be greater than zero";
        return transactions.stream()
                .filter(t -> (t.getDescription().equals(description) && t.getAmount() == amount ))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Calculates the current balance based on all transactions.
     *
     * @return The current balance
     */
    public double getBalance() {
        double balance = 0;
        for (Transaction transaction : transactions) {
            if (transaction instanceof Income) {
                balance += transaction.getAmount();
            } else if (transaction instanceof Expense) {
                balance -= transaction.getAmount();
            }
        }
        return balance;
    }

    /**
     * Calculates the total income from all transactions.
     *
     * @return The total income
     */
    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> t instanceof Income)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Calculates the total expenses from all transactions.
     *
     * @return The total expenses
     */
    public double getTotalExpenses() {
        return transactions.stream()
                .filter(t -> t instanceof Expense)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    // TODO Gets the total income for a specific month and year.

    // TODO Gets the total expenses for a specific month and year.

    // TODO Gets expenses by category for a specific month and year.

    /**
     * Clears all transactions.
     */
    public void clearTransactions() {
        transactions.clear();
    }

    /**
     * Returns the number of transactions.
     *
     * @return The number of transactions
     */
    public int getTransactionCount() {
        return transactions.size();
    }
}

