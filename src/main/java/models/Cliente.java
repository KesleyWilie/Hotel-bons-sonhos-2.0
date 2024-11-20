package models;

public class Cliente extends Usuario{
    private static final boolean admin = false;
    
    public boolean isAdmin() {
        return admin;
    }
}
