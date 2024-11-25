package dao;

import dto.QuartoDTO;

import models.quarto.Quarto;
import utils.mapper.Mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class QuartoDAO {
    private EntityManager em;

    public QuartoDAO() {
        this.em = Persistence.createEntityManagerFactory("HotelBonsSonhosPU").createEntityManager();
    }

    public void cadastrarQuarto(QuartoDTO dto) {
        Quarto entity = Mapper.parseObject(dto, Quarto.class);

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        System.out.println("Quarto cadastrado com sucesso.");
    }

    public List<QuartoDTO> listarQuartos() {
        String jpql = "SELECT q FROM Quarto q";
        TypedQuery<Quarto> query = em.createQuery(jpql, Quarto.class);
        List<Quarto> quartos = query.getResultList();

        List<QuartoDTO> dtos = new ArrayList<>();
        for (Quarto quarto : quartos) {
            QuartoDTO dto = Mapper.parseObject(quarto, QuartoDTO.class);
            dtos.add(dto);
        }
        return dtos;
    }

    public QuartoDTO recuperarQuarto(int id) {
        Quarto quarto = em.find(Quarto.class, id);
        if (quarto == null) {
            return null;
        }
        return Mapper.parseObject(quarto, QuartoDTO.class);
    }

    public boolean atualizarQuarto(QuartoDTO dto) {
        Quarto entity = Mapper.parseObject(dto, Quarto.class);

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

        return true;
    }

    public int buscarIdQuarto(int numero, String tipo, int andar) {
        String jpql = "SELECT q.id FROM Quarto q WHERE q.numero = :numero AND q.tipo = :tipo AND q.andar = :andar";
        TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);
        query.setParameter("numero", numero);
        query.setParameter("tipo", tipo); // Ajustado para 'tipo'
        query.setParameter("andar", andar);
    
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return -1; // Retorna -1 caso não encontre o quarto
        }
    }    

    public String removerQuarto(int id) {
        em.getTransaction().begin();
        Quarto quarto = em.find(Quarto.class, id);

        if (quarto == null) {
            em.getTransaction().rollback();
            return "Quarto não encontrado.";
        }

        // Verifica se há reservas associadas
        String jpqlCheck = "SELECT COUNT(r) FROM Reserva r WHERE r.quarto.id = :id";
        TypedQuery<Long> checkQuery = em.createQuery(jpqlCheck, Long.class);
        checkQuery.setParameter("id", id);

        long reservasAssociadas = checkQuery.getSingleResult();
        if (reservasAssociadas > 0) {
            em.getTransaction().rollback();
            return "Não é possível remover o quarto, pois existem reservas associadas.";
        }

        em.remove(quarto);
        em.getTransaction().commit();
        return "Quarto removido com sucesso.";
    }
}