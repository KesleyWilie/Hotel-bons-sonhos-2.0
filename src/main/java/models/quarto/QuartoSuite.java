package models.quarto;

import java.util.Arrays;
import java.util.List;

public class QuartoSuite extends Quartos {

    public QuartoSuite(int numero, int andar, double precoDiaria, String tipo) {
        super(numero, andar, precoDiaria, tipo, 4);
    }

    public String getDescricaoBasica() {
        return "Quarto de Suíte no andar " + getAndar() + " com número " + getNumero();
    }

    @Override
    public List<String> getMoveis() {
        return Arrays.asList(
            "Cama king-size com cabeceira estofada de luxo", 
            "Mesas de cabeceira com abajures modernos",
            "Guarda-roupa walk-in",
            "Sofá de três lugares",
            "Mesa de jantar com 4 cadeiras",
            "Banheira de hidromassagem",
            "TV de 65 polegadas",
            "Frigobar completo",
            "Armário para bebidas premium",
            "Poltronas adicionais na área de estar"
        );
    }

}
