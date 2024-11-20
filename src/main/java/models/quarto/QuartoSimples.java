package models.quarto;

import java.util.Arrays;
import java.util.List;

public class QuartoSimples extends Quartos {

    public QuartoSimples(int numero, int andar, double precoDiaria, String tipo) {
        super(numero, andar, precoDiaria, tipo, 2);
    }

    public String getDescricaoBasica() {
        return "Quarto Simples no andar " + getAndar() + " com número " + getNumero();
    }

    @Override
    public List<String> getMoveis() {
        return Arrays.asList(
            "Cama de solteiro", 
            "Mesa de cabeceira com abajur simples", 
            "Guarda-roupa pequeno com 2 portas",
            "Cadeira simples",
            "Espelho de parede pequeno",
            "Mesa de trabalho compacta",
            "TV de 24 polegadas"
        );
    }

}
