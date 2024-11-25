package models.quarto;

import jakarta.persistence.*;
import models.quarto.observer.IObserver;
import models.quarto.observer.QuartoNovoSubject;


@Entity
@Table(name = "quartos")
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int numero;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private int andar;

    @Column(nullable = false, name = "preco_diaria")
    private double precoDiaria;

    @Column(nullable = false)
    private Integer capacidade;

    @Transient
    private QuartoNovoSubject subject;

    public void configurarOuvintes() {
        this.subject = new QuartoNovoSubject(); // Inicializa o Subject
        this.subject.notificarObservers();
    }

    public void adicionarObserver(IObserver observer) {
        if (this.subject != null) {
            this.subject.adicionarObserver(observer);
        }
    }

    public void removerObserver(IObserver observer) {
        if (this.subject != null) {
            this.subject.removerObserver(observer);
        }
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void getCodigoQuarto(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAndar() {
        return andar;
    }

    public void setAndar(int andar) {
        this.andar = andar;
    }

    public double getPrecoDiaria() {
        return precoDiaria;
    }

    public void setPrecoDiaria(double precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    public int getCapacidadeMaxima() {
        return capacidade;
    }

    public void setCapacidadeMaxima(int capacidade) {
        this.capacidade = capacidade;
    }
}
