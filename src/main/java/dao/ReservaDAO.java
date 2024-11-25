package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import models.Cliente;
import models.quarto.Quarto;
import models.reserva.Reserva;

import java.util.List;

public class ReservaDAO {

    private EntityManager em;

    public ReservaDAO() {
        this.em = Persistence.createEntityManagerFactory("HotelBonsSonhosPU").createEntityManager();
    }

    // Método para cadastrar uma reserva
    @SuppressWarnings("null")
    public void cadastrarReserva(Reserva reserva) {
        EntityManager em = null;

        try {
            // Obtenha o EntityManager
            em.getTransaction().begin();

            // Certifique-se de que as entidades associadas (Cliente, Quarto) estejam gerenciadas
            Cliente cliente = em.find(Cliente.class, reserva.getCliente().getCPF());
            Quarto quarto = em.find(Quarto.class, reserva.getQuarto().getId());

            if (cliente == null || quarto == null) {
                throw new IllegalArgumentException("Cliente ou Quarto não encontrados no banco de dados.");
            }

            // Atualize as associações com as entidades gerenciadas
            reserva.setCliente(cliente);
            reserva.setQuarto(quarto);

            // Persista a reserva
            em.persist(reserva);

            // Confirme a transação
            em.getTransaction().commit();

            System.out.println("Reserva cadastrada com sucesso.");
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
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
            "SELECT r FROM Reserva r WHERE r.quarto.id = :codigoQuarto", 
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
