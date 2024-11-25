package models.reserva;

import models.Cliente;
import models.quarto.Quarto;
import utils.mapper.Mapper;

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

    public ReservaBuilder setDataCheckin(java.util.Date dataCheckin){
        instancia.setDataCheckin(dataCheckin);
        return this;
    }

    public ReservaBuilder setDataCheckout(java.util.Date dataCheckout) {
        instancia.setDataCheckout(dataCheckout);
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
