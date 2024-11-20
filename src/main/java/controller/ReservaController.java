package controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import dao.QuartoDAO;
import dao.ReservaDAO;
import dto.ClienteDTO;
import dto.QuartoDTO;
import dto.ReservaDTO;
import models.reserva.Reserva;
import models.reserva.ReservaBuilder;
import models.strategy.*;
import utils.mapper.Mapper;

public class ReservaController {

    public static double reservarQuarto(QuartoDTO quarto, String cpf, Date checkin, Date checkout) {
        // Obtendo a data atual.
        Calendar hoje = Calendar.getInstance();
        hoje.set(Calendar.HOUR_OF_DAY, 0);
        hoje.set(Calendar.MINUTE, 0);
        hoje.set(Calendar.SECOND, 0);
        hoje.set(Calendar.MILLISECOND, 0);
    
        Calendar checkinCalendar = Calendar.getInstance();
        checkinCalendar.setTime(checkin);
    
        // Verificar se a data de check-in é anterior à data atual
        if (checkinCalendar.before(hoje)) {
            throw new IllegalArgumentException("Não é possível reservar para uma data anterior à data atual.");
        }
    
        int quantReservas = consultarReservasAnteriores(cpf);
    
        IEstrategiaDePrecos estrategiaPreco;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkin);
    
        int mesCheckin = calendar.get(Calendar.MONTH) + 1;
    
        if (quantReservas > 10) {
            estrategiaPreco = new EstrategiaPrecoClienteFiel();
        } 
        // Se for julho ou dezembro, o preço aumenta pela demanda
        else if (mesCheckin == 7 || mesCheckin == 12) {
            estrategiaPreco = new EstrategiaPrecoSazonal();
        } 
        // Março é aniversário do hotel então tem promoção
        else if (mesCheckin == 3) {
            estrategiaPreco = new EstrategiaPrecoPromo();
        } else {
            estrategiaPreco = null;
        }
    
        
        LocalDate dataCheckin = checkin.toLocalDate();
        LocalDate dataCheckout = checkout.toLocalDate();
    
        // Calcular o número de noites, diferença em dias entre checkin e checkout.
        long numeroDeNoites = ChronoUnit.DAYS.between(dataCheckin, dataCheckout);
    
        if (numeroDeNoites <= 0) {
            throw new IllegalArgumentException("A data de check-out deve ser posterior à data de check-in.");
        }
    
        double precoTotal;
    
        // Se nenhuma estratégia for usada será o preço integral da diária
        if (estrategiaPreco != null) {
            precoTotal = estrategiaPreco.calcularPreco(quarto.getPrecoDiaria(), (int) numeroDeNoites, quantReservas);
        } else {
            precoTotal = quarto.getPrecoDiaria() * numeroDeNoites;
        }
    
        // Verifica se existe algum protótipo de reserva
        Reserva reserva = consultarReservaPrototype(quarto, cpf);
    
        ReservaBuilder builder = new ReservaBuilder().setId(new Random().nextInt(10000))
                                                        .setDataCheckin(checkin)
                                                        .setDataCheckout(checkout)
                                                        .setPrecoTotal(precoTotal)
                                                        .setQuarto(quarto);
    
        if (reserva == null) {
            ClienteDTO cliente = Mapper.parseObject(UsuarioController.resgatarCliente(cpf), ClienteDTO.class);
            builder.setCliente(cliente);
        } else {
            builder.setCliente(Mapper.parseObject(reserva.getCliente(), ClienteDTO.class));
        }
    
        new ReservaDAO().cadastrarReserva(Mapper.parseObject(builder.builder(), ReservaDTO.class));
    
        return precoTotal;
    }
    

    public static Reserva consultarReservaPrototype(QuartoDTO quarto, String cpf) {
        ArrayList<ReservaDTO> reservas = new ReservaDAO().listarReservas();
        
        for (ReservaDTO reserva : reservas) {
            if (reserva.getCliente().getCPF().equals(cpf)) {     
                Reserva r = Mapper.parseObject(reserva, Reserva.class);
                return r.clone();
            }
        }
        return null;
    }

    public static int consultarReservasAnteriores(String cpf) {
        ArrayList<ReservaDTO> reservas = new ReservaDAO().listarReservas();
        int quantidade = 0;
        
        for (ReservaDTO reserva : reservas) {
            if (reserva.getCliente().getCPF().equals(cpf)) {
                quantidade++;
            }
        }

        return quantidade;
    }

    public static ArrayList<ReservaDTO> resgatarReservasDeClientes(String cpf) {
        ArrayList<ReservaDTO> reservas = new ReservaDAO().listarReservasPorCliente(cpf);

        return reservas;
    }

    public static boolean consultarDisponibilidade(QuartoDTO quarto, Date dataCheckin, Date dataCheckout) {
        ReservaDAO reservaDAO = new ReservaDAO();

        int idQuarto = new QuartoDAO().buscarIdQuarto(quarto.getNumero(), quarto.getTipo(), quarto.getAndar());
        List<ReservaDTO> reservas = reservaDAO.listarReservasPorQuarto(idQuarto);

        for (ReservaDTO reserva : reservas) {
            Date checkinExistente = new Date(reserva.getDataCheckin().getTime());
            Date checkoutExistente = new Date(reserva.getDataCheckout().getTime());

            if ((dataCheckin.before(checkoutExistente) && dataCheckout.after(checkinExistente)) ||
                (dataCheckin.equals(checkinExistente) || dataCheckout.equals(checkoutExistente))) {
                return false; 
            }
        }

        return true; 
    }
}
