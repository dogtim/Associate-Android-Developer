package com.example.associate.training.java.javacallKT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UseCase {
    public static UserKT registerGuest(String name) {
        UserKT guest = new UserKT(RepositoryKT.getNextGuestId(), StringUtils.nameToLogin(name), name);
        RepositoryKT.addUser(guest);
        return guest;
    }

    public static List<UserKT> getSystemUsers() {
        ArrayList<UserKT> systemUsers = new ArrayList<>();
        for (UserKT UserKT : RepositoryKT.getUsers()) {
            if (UserKT.hasSystemAccess()) {
                systemUsers.add(UserKT);
            }
        }
        return systemUsers;
    }

    public static String formatUser(UserKT UserKT) {
        return String.format("%s (%s:%d)", UserKT.displayName, UserKT.username, UserKT.id);
    }

    public static void backupUsers() {
        try {
            if (!RepositoryKT.saveAs(RepositoryKT.BACKUP_PATH)) {
                // TODO: Report error backing up UserKT database.
            }
        } catch (IOException e) {
            // Log exception.
        }
    }
}


