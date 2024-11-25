package controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import dao.ReservaDAO;
import dto.ClienteDTO;
import dto.QuartoDTO;
import dto.ReservaDTO;
import models.reserva.Reserva;
import models.reserva.ReservaBuilder;
import models.strategy.*;
import utils.mapper.Mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ReservaController {

    // Método para reservar um quarto
    public static double reservarQuarto(QuartoDTO quarto, String cpf, Date checkin, Date checkout) {
        // Validação de datas
        if (!validarDatas(checkin, checkout)) {
            throw new IllegalArgumentException("Datas de check-in ou check-out inválidas.");
        }

        // Consultar quantidade de reservas anteriores
        int quantReservas = consultarReservasAnteriores(cpf);

        // Determinar a estratégia de preços baseada na quantidade de reservas e data
        IEstrategiaDePrecos estrategiaPreco = determinarEstrategiaDePreco(checkin, quantReservas);

        // Calcular o número de noites e o preço total
        long numeroDeNoites = ChronoUnit.DAYS.between(checkin.toLocalDate(), checkout.toLocalDate());
        double precoTotal = calcularPreco(quarto.getPrecoDiaria(), estrategiaPreco, (int) numeroDeNoites, quantReservas);

        // Criar a reserva
        ReservaBuilder builder = criarReservaBuilder(quarto, cpf, checkin, checkout, precoTotal);

        // Salvar a reserva no banco de dados usando JPA
        ReservaDAO reservaDAO = new ReservaDAO();
        EntityManager em = reservaDAO.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(builder.builder()); // Persiste a reserva
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Erro ao salvar a reserva", e);
        }
    
        return precoTotal;
    }

    // Valida a data de check-in e check-out
    private static boolean validarDatas(Date checkin, Date checkout) {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataCheckin = checkin.toLocalDate();
        LocalDate dataCheckout = checkout.toLocalDate();

        return !dataCheckin.isBefore(dataAtual) && dataCheckin.isBefore(dataCheckout);
    }

    // Determina a estratégia de preços
    private static IEstrategiaDePrecos determinarEstrategiaDePreco(Date checkin, int quantReservas) {
        int mesCheckin = checkin.toLocalDate().getMonthValue();
        if (quantReservas > 10) {
            return new EstrategiaPrecoClienteFiel();
        } else if (mesCheckin == 7 || mesCheckin == 12) {
            return new EstrategiaPrecoSazonal();
        } else if (mesCheckin == 3) {
            return new EstrategiaPrecoPromo();
        }
        return null;
    }

    // Calcula o preço total da reserva
    private static double calcularPreco(double precoDiaria, IEstrategiaDePrecos estrategia, int noites, int quantReservas) {
        return (estrategia != null) ? estrategia.calcularPreco(precoDiaria, noites, quantReservas) : precoDiaria * noites;
    }

    // Cria o builder para a reserva
    private static ReservaBuilder criarReservaBuilder(QuartoDTO quarto, String cpf, Date checkin, Date checkout, double precoTotal) {
        ReservaBuilder builder = new ReservaBuilder()
                .setId(new Random().nextInt(10000))
                .setDataCheckin(checkin)
                .setDataCheckout(checkout)
                .setPrecoTotal(precoTotal)
                .setQuarto(quarto);

        ClienteDTO cliente = (ClienteDTO) UsuarioController.resgatarCliente(cpf);
        if (cliente != null) {
            builder.setCliente(cliente);
        } else {
            throw new IllegalArgumentException("Cliente não encontrado para CPF informado.");
        }

        return builder;
    }

    // Consulta uma reserva prototype
    public static Reserva consultarReservaPrototype(QuartoDTO quarto, String cpf) {
        ReservaDAO reservaDAO = new ReservaDAO();//
        EntityManager em = reservaDAO.getEntityManager();
        List<Reserva> reservas = em.createQuery("SELECT r FROM Reserva r WHERE r.cliente.cpf = :cpf", Reserva.class)
                .setParameter("cpf", cpf)
                .getResultList();

        for (Reserva reserva : reservas) {
            if (reserva.getCliente().getCPF().equals(cpf)) {
                return reserva.clone(); // Retorna uma cópia da reserva
            }
        }
        return null;
    }

    // Consulta o número de reservas anteriores feitas por um cliente
    public static int consultarReservasAnteriores(String cpf) {
        ReservaDAO reservaDAO = new ReservaDAO();
        EntityManager em = reservaDAO.getEntityManager();
        long count = (long) em.createQuery("SELECT COUNT(r) FROM Reserva r WHERE r.cliente.cpf = :cpf")
                .setParameter("cpf", cpf)
                .getSingleResult();
        return (int) count;
    }

    // Resgata todas as reservas feitas por um cliente
    public static List<ReservaDTO> resgatarReservasDeClientes(String cpf) {
        ReservaDAO reservaDAO = new ReservaDAO();
        EntityManager em = reservaDAO.getEntityManager();
        List<Reserva> reservas = em.createQuery("SELECT r FROM Reserva r WHERE r.cliente.cpf = :cpf", Reserva.class)
                .setParameter("cpf", cpf)
                .getResultList();

        return Mapper.parseListObjects(reservas, ReservaDTO.class);
    }

    // Consulta a disponibilidade de um quarto para as datas fornecidas
    public static boolean consultarDisponibilidade(QuartoDTO quarto, Date dataCheckin, Date dataCheckout) {
        ReservaDAO reservaDAO = new ReservaDAO();
        EntityManager em = reservaDAO.getEntityManager();
        List<Reserva> reservas = em.createQuery("SELECT r FROM Reserva r WHERE r.quarto.id = :idQuarto", Reserva.class)
                .setParameter("idQuarto", quarto.getCodigoQuarto())
                .getResultList();

        for (Reserva reserva : reservas) {
            Date checkinExistente = (Date) reserva.getDataCheckin();
            Date checkoutExistente = (Date) reserva.getDataCheckout();

            if ((dataCheckin.before(checkoutExistente) && dataCheckout.after(checkinExistente)) ||
                (dataCheckin.equals(checkinExistente) || dataCheckout.equals(checkoutExistente))) {
                return false; // O quarto está indisponível
            }
        }
        return true; // O quarto está disponível
    }
}
