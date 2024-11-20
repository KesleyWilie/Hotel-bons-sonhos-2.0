package dto;

public class AdmDTO extends UsuarioDTO{
    private static final boolean isAdmin = true;

    public AdmDTO(String nome, String email, String cpf, String telefone, String senha) {
        super(nome, email, cpf, telefone, senha, isAdmin);
    }

    public AdmDTO(){}
}
