package models.quarto;

public class QuartoFactory {
    public QuartoDeLuxo criarQuartoDeLuxo(int numero, int andar, double precoDiaria) {
        return new QuartoDeLuxo(numero, andar, precoDiaria, "Luxo");
    }

    public QuartoSimples criarQuartoSimples(int numero, int andar, double precoDiaria) {
        return new QuartoSimples(numero, andar, precoDiaria, "Simples");
    }

    public QuartoSuite criarQuartoSuite(int numero, int andar, double precoDiaria) {
        return new QuartoSuite(numero, andar, precoDiaria, "Suite");
    }
}


