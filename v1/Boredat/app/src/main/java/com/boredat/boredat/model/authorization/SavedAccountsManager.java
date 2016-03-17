package com.boredat.boredat.model.authorization;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liz on 2/7/2016.
 */
public class SavedAccountsManager {
    // Constants
    final static public String KEY_SAVED_ACCOUNTS = "savedAccounts";
    final static public String KEY_LOGGED_IN_ACCOUNT = "loggedInAccount";
    final static public String KEY_LAST_USED_ACCOUNT = "lastUsedAccount";

    // Member Variables
    private SharedPreferences prefs;
    private Gson gson;

    public SavedAccountsManager(SharedPreferences prefs, Gson gson) {
        this.prefs = prefs;
        this.gson = gson;
    }

    public void saveAccount(Account account) {
        // Get the list of saved accounts
        List<Account> savedAccounts = getSavedAccounts();
        if (savedAccounts == null) {
            savedAccounts = new ArrayList<Account>();
        }

        // Check if the account is already saved
        int pos = savedAccounts.indexOf(account);

        // Delete the old record if it exists
        if (pos >= 0) {
            savedAccounts.remove(pos);
        }

        // Save the new account
        savedAccounts.add(account);

        // Convert the list of saved accounts to json format
        // Store the json list of saved accounts in SharedPreferences
        String json = gson.toJson(savedAccounts);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_SAVED_ACCOUNTS, json);
        editor.commit();
    }

    public Account getAccountForUserId(String id) {
        String json = prefs.getString(KEY_SAVED_ACCOUNTS, null);
        if (json != null) {
            Type type = new TypeToken<List<Account>>(){}.getType();
            List<Account> savedAccounts = gson.fromJson(json, type);
            for (Account account : savedAccounts) {
                if (account.getUserId().equals(id)) {
                    return account;
                }
            }
        }
        return null;
    }

    public List<Account> getSavedAccounts() {
        String json = prefs.getString(KEY_SAVED_ACCOUNTS, null);
        if (json != null) {
            Type type = new TypeToken<List<Account>>(){}.getType();
            return gson.fromJson(json, type);
        }
        return null;
    }

    public String[] getSavedUserIds() {
        String json = prefs.getString(KEY_SAVED_ACCOUNTS, null);
        if (json != null) {
            Type type = new TypeToken<List<Account>>(){}.getType();
            List<Account> list = gson.fromJson(json, type);
            String[] idArray = new String[list.size()];

            for (int i=0; i < list.size()-1; i++) {
                idArray[i] = list.get(i).getUserId();
            }
            return idArray;
        }
        return null;
    }

    public String getPasswordForUserId(String userId) {
        Account account = getAccountForUserId(userId);
        if (account != null) {
            return account.getPassword();
        }

        return null;
    }

    public Account getLoggedInAccount() {
        String json = prefs.getString(KEY_LOGGED_IN_ACCOUNT, null);
        if (json != null) {
            Type type = new TypeToken<Account>(){}.getType();
            return gson.fromJson(json, type);
        }
        return null;
    }

    public void setLoggedInAccount(Account account) {
        String json = gson.toJson(account);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LOGGED_IN_ACCOUNT, json);
        editor.commit();
    }

    public Account getLastUsedAccount() {
        String json = prefs.getString(KEY_LAST_USED_ACCOUNT, null);
        if (json != null) {
            Type type = new TypeToken<Account>(){}.getType();
            return gson.fromJson(json, type);
        }
        return null;
    }

    public void setLastUsedAccount(Account account) {
        String json = gson.toJson(account);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LAST_USED_ACCOUNT, json);
        editor.commit();
    }
}
