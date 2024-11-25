package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import models.reserva.Reserva;

import java.util.List;

public class ReservaDAO {

    private EntityManager em;

    public ReservaDAO() {
        this.em = Persistence.createEntityManagerFactory("HotelBonsSonhosPU").createEntityManager();
    }

    // Método para cadastrar uma reserva
    public void cadastrarReserva(Reserva reserva) {
        em.getTransaction().begin();
        em.persist(reserva);
        em.getTransaction().commit();
        System.out.println("Reserva cadastrada com sucesso: " + reserva);
    }

    // Método para listar todas as reservas
    public List<Reserva> listarReservas() {
        TypedQuery<Reserva> query = em.createQuery("SELECT r FROM Reserva r", Reserva.class);
        return query.getResultList();
    }

    // Método para buscar uma reserva pelo ID
    public Reserva recuperarReserva(int id) {
        return em.find(Reserva.class, id);
    }

    // Método para listar reservas por quarto
    public List<Reserva> listarReservasPorQuarto(int codigoQuarto) {
        TypedQuery<Reserva> query = em.createQuery(
            "SELECT r FROM Reserva r WHERE r.quarto.codigoQuarto = :codigoQuarto", 
            Reserva.class
        );
        query.setParameter("codigoQuarto", codigoQuarto);
        return query.getResultList();
    }

    // Método para listar reservas por cliente
    public List<Reserva> listarReservasPorCliente(String cpf) {
        TypedQuery<Reserva> query = em.createQuery(
            "SELECT r FROM Reserva r WHERE r.cliente.cpf = :cpf", 
            Reserva.class
        );
        query.setParameter("cpf", cpf);
        return query.getResultList();
    }

    // Método para atualizar uma reserva
    public boolean atualizarReserva(Reserva reserva) {
        em.getTransaction().begin();
        Reserva updated = em.merge(reserva);
        em.getTransaction().commit();
        return updated != null;
    }

    // Método para remover uma reserva pelo ID
    public void removerReserva(int id) {
        em.getTransaction().begin();
        Reserva reserva = em.find(Reserva.class, id);
        if (reserva != null) {
            em.remove(reserva);
            System.out.println("Reserva removida com sucesso: " + reserva);
        } else {
            System.out.println("Reserva não encontrada com ID: " + id);
        }
        em.getTransaction().commit();
    }
}
