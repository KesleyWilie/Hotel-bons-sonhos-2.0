package models;
//import jakarta.persistence.*;

public abstract class Usuario {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String CPF;
    
    public abstract boolean isAdmin();
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCPF(){
        return CPF;
    }

    public void setCPF(String CPF){
        this.CPF = CPF;
    }
    
   public String toString(){
        return this.nome + this.CPF + this.email + this.telefone;
   }
}
/*
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column
    private String telefone;

    @Column(name = "isAdmin", nullable = false)
    private boolean isAdmin;

    // get e set da vida
} */