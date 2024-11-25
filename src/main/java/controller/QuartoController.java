package controller;

import dao.QuartoDAO;
import dao.ReservaDAO;
import dto.QuartoDTO;
import models.quarto.Quarto;
import models.quarto.QuartoFactory;
import models.quarto.Quartos;

import java.util.List;

public class QuartoController {
    public static void criarQuarto(int numero, String tipo, int andar, double precoDiaria) {
        
        Quarto quarto = tipoQuartoFactory(numero, tipo, andar, precoDiaria);

        // Criando um DTO  com o quarto
        if (quarto != null) {
            QuartoDTO dto = new QuartoDTO();
            dto.setCodigoQuarto(quarto.getCodigoQuarto());
            dto.setNumero(quarto.getNumero());
            dto.setAndar(quarto.getAndar());
            dto.setPrecoDiaria(quarto.getPrecoDiaria());
            dto.setTipo(quarto.getTipo());
            dto.setCapacidadeMaxima(quarto.getCapacidadeMaxima());
    
            // Salvando o quarto no Banco de Dados
            try {
                new QuartoDAO().cadastrarQuarto(dto);
                quarto.configurarOuvintes();
            } catch (Exception e) {
                System.out.println("Erro ao cadastrar o quarto: " + e.getMessage());
            }
        }
    }

    public static Quartos tipoQuartoFactory(int numero, String tipo, int andar, double precoDiaria){
        QuartoFactory factory = new QuartoFactory();

        Quartos quarto;
        switch (tipo.toLowerCase()) {
            case "luxo":
                quarto = factory.criarQuartoDeLuxo(numero, andar, precoDiaria);
                break;
            case "simples":
                quarto = factory.criarQuartoSimples(numero, andar, precoDiaria);
                break;
            case "suite":
                quarto = factory.criarQuartoSuite(numero, andar, precoDiaria);
                break;
            default:
                throw new IllegalArgumentException("Tipo de quarto inválido");
        }

        return quarto;
    }

    public static String recuperarInfoQuarto(int id){
        try {
            Quarto q = new ReservaDAO().recuperarReserva(id).getQuarto();
            return tipoQuartoFactory(q.getNumero(), q.getTipo(), q.getAndar(), q.getPrecoDiaria()).getDescricaoBasica();
        } catch (Exception e) {
            return "Erro ao recuperar as informações do quarto: " + e.getMessage();
        }
    }

    public static boolean removerQuarto(int numero, String categoria, int andar) {
        QuartoDAO quartoDAO = new QuartoDAO();
        int id = quartoDAO.buscarIdQuarto(numero, categoria, andar);

        if (id != -1) {
            String resultado = quartoDAO.removerQuarto(id);
            System.out.println(resultado);
            return resultado.contains("sucesso");
        }
        return false;
    }

    public static List<QuartoDTO> listarQuartos() {
        return new QuartoDAO().listarQuartos();
    }
}
