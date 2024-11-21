package models;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("1") // Valor para diferenciar admins
public class ADM extends Usuario {

    @Override
    public boolean isAdmin() {
        return true;
    }
}
