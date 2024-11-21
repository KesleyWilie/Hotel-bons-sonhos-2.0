package models;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("0") // Valor para diferenciar clientes
public class Cliente extends Usuario {

    @Override
    public boolean isAdmin() {
        return false;
    }
}
