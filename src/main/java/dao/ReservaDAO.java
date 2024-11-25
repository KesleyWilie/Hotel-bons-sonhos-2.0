package dao;

import dto.ReservaDTO;
import models.reserva.Reserva;
import utils.mapper.Mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private EntityManager em;

    public ReservaDAO() {
        this.em = Persistence.createEntityManagerFactory("HotelBonsSonhosPU").createEntityManager();
    }

    public void cadastrarReserva(ReservaDTO dto){
        Reserva entity = Mapper.parseObject(dto, Reserva.class);

        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();

        System.out.println("Reserva cadastrada com sucesso.");
    }

    public List<ReservaDTO> listarReservas() {
        String jpql = "SELECT r FROM Reserva r";
        TypedQuery<Reserva> query = em.createQuery(jpql, Reserva.class);
        List<Reserva> reservas = query.getResultList();

        List<ReservaDTO> dtos = new ArrayList<>();
        for (Reserva reserva : reservas) {
            ReservaDTO dto = Mapper.parseObject(reserva, ReservaDTO.class);
            dtos.add(dto);
        }
        return dtos;
    }    

    public List<ReservaDTO> listarReservasPorQuarto(int codigoQuarto) {
        String jpql = "SELECT r FROM Reserva r WHERE r.quarto.codigo = :codigoQuarto";
        TypedQuery<Reserva> query = em.createQuery(jpql, Reserva.class);
        query.setParameter("codigoQuarto", codigoQuarto);
        List<Reserva> reservas = query.getResultList();

        List<ReservaDTO> dtos = new ArrayList<>();
        for (Reserva reserva : reservas) {
            ReservaDTO dto = Mapper.parseObject(reserva, ReservaDTO.class);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<ReservaDTO> listarReservasPorCliente(String cpf) {
        String jpql = "SELECT r FROM Reserva r WHERE r.cliente.cpf = :cpf";
        TypedQuery<Reserva> query = em.createQuery(jpql, Reserva.class);
        query.setParameter("cpf", cpf);
        List<Reserva> reservas = query.getResultList();

        List<ReservaDTO> dtos = new ArrayList<>();
        for (Reserva reserva : reservas) {
            ReservaDTO dto = Mapper.parseObject(reserva, ReservaDTO.class);
            dtos.add(dto);
        }
        return dtos;
    }

    public ReservaDTO recuperarReserva(int id) {
        Reserva reserva = em.find(Reserva.class, id);
        if (reserva == null) {
            return null;
        }
        return Mapper.parseObject(reserva, ReservaDTO.class);
    }

    public boolean atualizarReserva(ReservaDTO dto) {
        Reserva entity = Mapper.parseObject(dto, Reserva.class);

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

        return true;
    }
    
    public String removerReserva(int id) {
        em.getTransaction().begin();
        Reserva reserva = em.find(Reserva.class, id);

        if (reserva == null) {
            em.getTransaction().rollback();
            return "Reserva não encontrada.";
        }

        em.remove(reserva);
        em.getTransaction().commit();
        return "Reserva removida com sucesso.";
    }

    public EntityManager getEntityManager() {
        return this.em;
    } 
}
