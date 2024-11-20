package models;

public class ADM extends Usuario {
    private static final boolean admin = true;
    
    public boolean isAdmin() {
        return admin;
    }
}
