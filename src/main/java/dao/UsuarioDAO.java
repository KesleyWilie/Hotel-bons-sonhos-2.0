package dao;

import dto.AdmDTO;
import dto.ClienteDTO;
import dto.UsuarioDTO;
import models.ADM;
import models.Cliente;
import models.Usuario;
import utils.mapper.Mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private EntityManager em;

    public UsuarioDAO() {
        this.em = Persistence.createEntityManagerFactory("HotelBonsSonhosPU").createEntityManager();
    }

    public void cadastrarUsuario(UsuarioDTO dto) {
        Usuario entity;
        if (dto instanceof AdmDTO) {
            entity = Mapper.parseObject(dto, ADM.class);
        } else {
            entity = Mapper.parseObject(dto, Cliente.class);
        }

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        System.out.println("Usuário cadastrado com sucesso");
    }

    public List<UsuarioDTO> listarTodosUsuarios() {
        String jpql = "SELECT u FROM Usuario u";
        TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
        List<Usuario> usuarios = query.getResultList();
    
        List<UsuarioDTO> dtos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.isAdmin()) {
                // Criando AdmDTO para administradores
                AdmDTO dto = new AdmDTO();
                dto.setCPF(u.getCPF());
                dto.setNome(u.getNome());
                dto.setEmail(u.getEmail());
                dto.setSenha(u.getSenha());
                dto.setTelefone(u.getTelefone());
                dto.setIsAdmin(true);
                dtos.add(dto);
            } else {
                // Criando ClienteDTO para clientes
                ClienteDTO dto = new ClienteDTO();
                dto.setCPF(u.getCPF());
                dto.setNome(u.getNome());
                dto.setEmail(u.getEmail());
                dto.setSenha(u.getSenha());
                dto.setTelefone(u.getTelefone());
                dto.setIsAdmin(false);
                dtos.add(dto);
            }
        }
        return dtos;
    }    

    public List<UsuarioDTO> listarUsuarios(boolean apenasClientes) {
        String jpql = apenasClientes
                ? "SELECT u FROM Cliente u"
                : "SELECT u FROM Usuario u";
        TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
        List<Usuario> usuarios = query.getResultList();

        List<UsuarioDTO> dtos = new ArrayList<>();
        for (Usuario u : usuarios) {
            UsuarioDTO dto = (u instanceof ADM)
                    ? Mapper.parseObject(u, AdmDTO.class)
                    : Mapper.parseObject(u, ClienteDTO.class);
            dtos.add(dto);
        }
        return dtos;
    }

    public UsuarioDTO recuperarUsuario(String cpf) {
        Usuario usuario = em.find(Usuario.class, cpf);
        if (usuario == null) {
            return null;
        }
        return (usuario instanceof ADM)
                ? Mapper.parseObject(usuario, AdmDTO.class)
                : Mapper.parseObject(usuario, ClienteDTO.class);
    }

    public boolean atualizarUsuario(UsuarioDTO dto) {
        Usuario entity;
        if (dto instanceof AdmDTO) {
            entity = Mapper.parseObject(dto, ADM.class);
        } else {
            entity = Mapper.parseObject(dto, Cliente.class);
        }

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        return true;
    }

    public String removerUsuario(String cpf) {
        em.getTransaction().begin();
        Usuario usuario = em.find(Usuario.class, cpf);

        if (usuario == null) {
            em.getTransaction().rollback();
            return "Usuário não encontrado.";
        }

        if (!(usuario instanceof ADM)) { // Admin não pode ser removido
            em.remove(usuario);
            em.getTransaction().commit();
            return "Usuário removido com sucesso.";
        }

        em.getTransaction().rollback();
        return "Não é possível remover o usuário.";
    }
}
