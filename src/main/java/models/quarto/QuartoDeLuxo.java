package models.quarto;

import java.util.Arrays;
import java.util.List;

public class QuartoDeLuxo extends Quartos {

    public QuartoDeLuxo(int numero, int andar, double precoDiaria, String tipo) {
        super(numero, andar, precoDiaria, tipo, 6);
    }

    public String getDescricaoBasica(){
        return "Quarto de Luxo no andar " + getAndar() + " com número " + getNumero();
    }

    @Override
    public List<String> getMoveis() {
        return Arrays.asList(
            "Cama king-size com cabeceira estofada", 
            "Mesa de cabeceira com abajur de luxo",
            "Guarda-roupa grande com 4 portas e espelho embutido",
            "Poltrona de leitura confortável",
            "Mesa de trabalho espaçosa com cadeira ergonômica",
            "TV de 55 polegadas",
            "Frigobar",
            "Armário para bebidas"
        );
    }

}
