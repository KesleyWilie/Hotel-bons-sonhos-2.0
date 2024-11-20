package models.quarto;
import java.util.List;

public abstract class Quartos extends Quarto{

    public Quartos(int numero, int andar, double precoDiaria, String tipo, int capacidadeMaxima) {
        this.setNumero(numero);
        this.setAndar(andar);
        this.setPrecoDiaria(precoDiaria);
        this.setTipo(tipo);
        this.setCapacidadeMaxima(capacidadeMaxima);
    }

    public abstract String getDescricaoBasica();

    public abstract List<String> getMoveis();

}
