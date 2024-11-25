package models.reserva;

import jakarta.persistence.*;
import models.Cliente;
import models.quarto.Quarto;
import utils.mapper.Mapper;

import java.util.Date;

import dto.ClienteDTO;
import dto.QuartoDTO;

@Entity
@Table(name = "reservas")
public class Reserva implements Prototype {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática de ID
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "CPF") // Relacionamento com Cliente (FK)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_quarto", referencedColumnName = "id")
    private Quarto quarto;

    @Column(name = "data_checkin", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCheckin;

    @Column(name = "data_checkout", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataCheckout;

    @Column(name = "preco_total", nullable = false)
    private double precoTotal;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public Date getDataCheckin() {
        return dataCheckin;
    }

    public void setDataCheckin(Date dataCheckin) {
        this.dataCheckin = dataCheckin;
    }

    public Date getDataCheckout() {
        return dataCheckout;
    }

    public void setDataCheckout(Date dataCheckout) {
        this.dataCheckout = dataCheckout;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", quarto=" + quarto +
                ", dataCheckin=" + dataCheckin +
                ", dataCheckout=" + dataCheckout +
                ", precoTotal=" + precoTotal +
                '}';
    }

    public Reserva clone() {
        return new ReservaBuilder()
        .setCliente(Mapper.parseObject(this.getCliente(), ClienteDTO.class))
        .setQuarto(Mapper.parseObject(this.getQuarto(), QuartoDTO.class))
        .setDataCheckin(this.dataCheckin)
        .setDataCheckout(this.dataCheckout)
        .setPrecoTotal(this.getPrecoTotal())
        .builder();
    }
}
