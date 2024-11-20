package dto;

public class ClienteDTO extends UsuarioDTO{
    private static final boolean isAdmin = false; 

    public ClienteDTO(String nome, String email, String cpf, String telefone, String senha) {
        super(nome, email, cpf, telefone, senha, isAdmin);
    }
    
    public ClienteDTO(){}
}
