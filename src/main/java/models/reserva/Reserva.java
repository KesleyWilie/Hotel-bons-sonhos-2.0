package models.reserva;

import java.sql.Date;

import dto.ClienteDTO;
import dto.QuartoDTO;
import models.Cliente;
import models.quarto.Quarto;
import utils.mapper.Mapper;
//import jakarta.persistence.*;


public class Reserva implements Prototype{

    private int id;
    private Cliente cliente;
    private Quarto quarto;
    private Date dataCheckin;
    private Date dataCheckout;
    private double precoTotal;

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

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", quarto=" + quarto +
                ", dataCheckin=" + dataCheckin +
                ", dataCheckout=" + dataCheckout +
                '}';
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
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
    /*
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "id_quarto", nullable = false)
    private Quarto quarto;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_checkin", nullable = false)
    private Date dataCheckin;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_checkout", nullable = false)
    private Date dataCheckout;

    @Column(name = "preco_total", nullable = false)
    private double precoTotal;

    // gets e set da vida
} */
}
