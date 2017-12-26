package dataHandlers;

import classes.Transaction;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 *   Author: Roberto Sanchez A. 
 *   Date:   12/19/17
 */

/**
 * @TransactionHandler.class manages the transactions that were done.
 * For now, Renting car Transactions are stored in @fileName
 * Note: This could be replaced for a JSON Document Data Base like MongoDB,
 *       for simplicity the transactions are stored in a .dat file.
 */
public class TransactionHandler {

    private static final String fileName = "Transactions.dat";

    // Get all stored transactions from @fileName:
    public List<Transaction> getAllTransactions(){
            // list of transaction to return
            List<Transaction> transactionList = null;
            try {
                File file = new File(fileName);
                if (!file.exists()) {
                    // if the document fileName does not exist
                    transactionList = new ArrayList<>();
                    saveTransactionList(transactionList);
                }
                else{
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    transactionList = (List<Transaction>) ois.readObject();
                    ois.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return transactionList;
    }

        // CRUD actions:

    /**
     * Gets a transaction from @fileName
     * @param id Transaction id
     * @return Transaction
     */
    public Transaction getTransaction(int id){
        List<Transaction> transactions = getAllTransactions();
        // check the transaction
        for (Transaction transaction: transactions){
            if(transaction.getId() == id){
                return transaction;
            }
        }
        return null;
    }

    /**
     * Store a Transaction in @fileName
     * @param pTransaction To store
     * @return true for a successful storage, false otherwise.
     */
    public boolean addTransaction(Transaction pTransaction){
        List<Transaction> transactions = getAllTransactions();
        boolean transactionExits = false;
        for(Transaction transaction: transactions){
            if(transaction.getId() == pTransaction.getId()){
                transactionExits = true;
                break;
            }
        }
        if(!transactionExits){
            transactions.add(pTransaction);
            saveTransactionList(transactions);
            return true;
        }
        return false;
    }

    /**
     * Update an existing transaction
     * @param pTransaction to Update
     * @return true for a successful storage, false otherwise.
     */
    public Boolean updateTransaction(Transaction pTransaction){
        List<Transaction> transactions = getAllTransactions();
        for(Transaction transaction: transactions){
            int index = transactions.indexOf(transaction);
            transactions.set(index, pTransaction);
            saveTransactionList(transactions);
            return true;
        }
        return false;
    }

    /**
     * Delete an existing transaction
     * @param id transaction id
     * @return true for a successful deletion, false otherwise.
     */
    public Boolean deleteTransaction(int id){
        List<Transaction> transactions = getAllTransactions();
        for(Transaction transaction: transactions){
            if(transaction.getId() == id){
                int index = transactions.indexOf(transaction);
                transactions.remove(index);
                saveTransactionList(transactions);
                return true;
            }
        }
        return false;
    }


    // End of the CRUD actions

    /**
     * Auxiliary function that save a list of Transactions in @fileName
     * @param transactionList list of Transactions
     */
    private void saveTransactionList(List<Transaction> transactionList){
        try {
            File file = new File(fileName);
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(transactionList);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }


