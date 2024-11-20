package dto;

public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String CPF;
    private boolean isAdmin;

    
    public UsuarioDTO(String nome, String email, String cpf, String telefone, String senha, boolean isAdmin) {
        this.nome = nome;
        this.email = email;
        this.CPF = cpf;
        this.telefone = telefone;
        this.senha = senha;
        this.isAdmin = isAdmin;
    }

    public UsuarioDTO(){}
    
    public boolean isAdmin() {
        return isAdmin;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

}

