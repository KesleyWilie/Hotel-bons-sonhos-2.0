package models;

public class Cliente {
    private int codigoDoCliente;
    private String nome;
    private String email;
    private String telefone;
    private String CPF;


    public int getCodigoDoCliente(){
        return codigoDoCliente;
    }
    public void setCodigoDoCliente(int codigoDoCliente){
        this.codigoDoCliente = codigoDoCliente;
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

}