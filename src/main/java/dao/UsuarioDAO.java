package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.AdmDTO;
import dto.ClienteDTO;
import dto.UsuarioDTO;
import models.ADM;
import models.Cliente;
import models.Usuario;
import utils.mapper.Mapper;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Persistence;

public class UsuarioDAO {

    public void cadastrarUsuario(UsuarioDTO dto, boolean isAdmin) throws SQLException {
        Usuario entity;
        if (dto instanceof AdmDTO) {
            entity = Mapper.parseObject(dto, ADM.class);
        } else {
            entity = Mapper.parseObject(dto, Cliente.class);
        }

        String sql = "INSERT INTO usuarios (nome, email, telefone, CPF, senha, isAdmin) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = SingletonConnection.getCon().prepareStatement(sql)) {
            ps.setString(1, entity.getNome());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getTelefone());
            ps.setString(4, entity.getCPF());
            ps.setString(5, entity.getSenha());
            ps.setBoolean(6, entity.isAdmin());
            ps.executeUpdate();
            System.out.println("Usuário cadastrado com sucesso");
        }
    }

    public ArrayList<UsuarioDTO> listarTodosUsuarios() {
        String sql = "SELECT CPF, nome, email, telefone, senha, isAdmin FROM usuarios";
        ArrayList<UsuarioDTO> usuarios = new ArrayList<>();

        try (Connection con = SingletonConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UsuarioDTO usuario;
                if (rs.getBoolean("isAdmin")) {
                    usuario = new AdmDTO(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("CPF"),
                            rs.getString("telefone"),
                            rs.getString("senha")
                    );
                } else {
                    usuario = new ClienteDTO(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("CPF"),
                            rs.getString("telefone"),
                            rs.getString("senha")
                    );
                }
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public ArrayList<UsuarioDTO> listarUsuarios(boolean apenasClientes) {
        String sql = "SELECT CPF, nome, email, telefone, senha FROM usuarios WHERE isAdmin = ?";
        ArrayList<UsuarioDTO> usuarios = new ArrayList<>();

        try (Connection con = SingletonConnection.getCon();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, !apenasClientes); 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UsuarioDTO usuario = new UsuarioDTO(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("CPF"),
                            rs.getString("telefone"),
                            rs.getString("senha"),
                            !apenasClientes 
                    );
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public UsuarioDTO recuperarUsuario(String cpf) {
        String sql = "SELECT CPF, nome, email, telefone, senha, isAdmin FROM usuarios WHERE CPF = ?";
        UsuarioDTO usuario = null;

        try (PreparedStatement ps = SingletonConnection.getCon().prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new UsuarioDTO(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("CPF"),
                            rs.getString("telefone"),
                            rs.getString("senha"),
                            rs.getBoolean("isAdmin")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
    

    public boolean atualizarUsuario(UsuarioDTO usuario) {
        Usuario entity;
        if (usuario instanceof AdmDTO) {
            entity = Mapper.parseObject(usuario, ADM.class);
        } else {
            entity = Mapper.parseObject(usuario, Cliente.class); 
        }

        String sql = "UPDATE usuarios SET nome = ?, email = ?, telefone = ?, senha = ?, isAdmin = ? WHERE cpf = ?";

        try (PreparedStatement ps = SingletonConnection.getCon().prepareStatement(sql)) {
            ps.setString(1, entity.getNome());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getTelefone());
            ps.setString(4, entity.getSenha());
            ps.setBoolean(5, entity.isAdmin());
            ps.setString(6, entity.getCPF());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String removerUsuario(String cpf) {
        String checkSql = "SELECT COUNT(*) FROM reservas WHERE id_cliente = ?";
        String deleteSql = "DELETE FROM usuarios WHERE cpf = ? AND isAdmin = FALSE";
        String verifyUserSql = "SELECT COUNT(*) FROM usuarios WHERE cpf = ?";
    
        try (Connection con = SingletonConnection.getCon();
             PreparedStatement verifyUserPs = con.prepareStatement(verifyUserSql)) {
            verifyUserPs.setString(1, cpf);
            ResultSet rsVerify = verifyUserPs.executeQuery();
            if (rsVerify.next() && rsVerify.getInt(1) == 0) {
                return "Usuário não encontrado.";
            }
    
            try (PreparedStatement checkPs = con.prepareStatement(checkSql)) {
                checkPs.setString(1, cpf);
                ResultSet rsCheck = checkPs.executeQuery();
                if (rsCheck.next() && rsCheck.getInt(1) == 0) {
                    try (PreparedStatement deletePs = con.prepareStatement(deleteSql)) {
                        deletePs.setString(1, cpf);
                        int rowsAffected = deletePs.executeUpdate();
                        if (rowsAffected > 0) {
                            return "Usuário removido com sucesso";
                        } else {
                            return "Não é possível remover o usuário";
                        }
                    }
                } else {
                    return "Não é possível remover o usuário, pois existem reservas associadas.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Ocorreu um erro";
    }
    
}
/*
Em vez de conexões diretas com SQL, tem que utilizar utilize o EntityManager para operações no banco de dados, tem esse exp +/- de como ficaria
public class UsuarioDAO {

    private EntityManager em;

    public UsuarioDAO() {
        this.em = Persistence.createEntityManagerFactory("HotelBonsSonhosPU").createEntityManager();
    }

    public Usuario findByCpf(String cpf) {
        return em.find(Usuario.class, cpf);
    }

    public void save(Usuario usuario) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    public void update(Usuario usuario) {
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
    }

    public void delete(String cpf) {
        em.getTransaction().begin();
        Usuario usuario = em.find(Usuario.class, cpf);
        if (usuario != null) {
            em.remove(usuario);
        }
        em.getTransaction().commit();
    }
}
    */