package models.reserva;

import models.Cliente;
import models.quarto.Quarto;
import utils.mapper.Mapper;

import java.sql.Date;

import dto.ClienteDTO;
import dto.QuartoDTO;

public class ReservaBuilder {

    private Reserva instancia;

    public ReservaBuilder(){
        this.instancia = new Reserva();
    }

    public ReservaBuilder setId(int id){
        instancia.setId(id);
        return this;
    }

    public ReservaBuilder setCliente(ClienteDTO cliente){
        instancia.setCliente(Mapper.parseObject(cliente, Cliente.class));
        return this;
    }

    public ReservaBuilder setQuarto(QuartoDTO quarto){
        instancia.setQuarto(Mapper.parseObject(quarto, Quarto.class));
        return this;
    }

    public ReservaBuilder setDataCheckin(Date data){
        instancia.setDataCheckin(data);
        return this;
    }

    public ReservaBuilder setDataCheckout(Date data) {
        instancia.setDataCheckout(data);
        return this;
    }

    public ReservaBuilder setPrecoTotal(double preco) {
        instancia.setPrecoTotal(preco);
        return this;
    }

    public Reserva builder(){
        return instancia;
    }
}
