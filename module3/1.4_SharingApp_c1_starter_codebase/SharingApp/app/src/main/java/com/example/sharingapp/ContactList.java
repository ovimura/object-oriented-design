package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ContactList {
    private ArrayList<Contact> contacts;
    private String FILENAME = "contacts.json";

    public ContactList() {
        contacts = new ArrayList<Contact>();
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts.addAll(contacts);
    }

    public ArrayList<Contact> getContacts() {
        return this.contacts;
    }

    public ArrayList<String> getAllUsernames() {
        ArrayList<String> usernames = new ArrayList<>();
        for(Contact c: this.contacts) {
            usernames.add(c.getUsername());
        }
        return usernames;
    }

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    public void deleteContact(Contact contact) {
        this.contacts.remove(contact);
    }

    public Contact getContact(int index) {
        return this.contacts.get(index);
    }

    public int getSize() {
        return this.contacts.size();
    }

    public int getIndex(Contact contact) {
        return this.contacts.indexOf(contact);
    }

    public boolean hasContact(Contact contact) {
        return this.contacts.contains(contact);
    }

    public Contact getContactByUsername(String username) {
        for(Contact c: this.contacts) {
            if(c.getUsername().equalsIgnoreCase(username)) {
                return c;
            }
        }
        return null;
    }

    public void loadContacts(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();
            contacts = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (FileNotFoundException e) {
            contacts = new ArrayList<Contact>();
        } catch (IOException e) {
            contacts = new ArrayList<Contact>();
        }
    }

    public void saveContacts(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(contacts, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameAvailable(String username) {
        for(Contact c: this.contacts) {
            if(c.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        return true;
    }
}