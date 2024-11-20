package controller;

import models.quarto.Quarto;
import models.quarto.QuartoFactory;
import models.quarto.Quartos;
import dao.QuartoDAO;
import dao.ReservaDAO;
import dto.QuartoDTO;

import java.util.ArrayList;

public class QuartoController {

    public static void criarQuarto(int numero, String tipo, int andar, double precoDiaria) {
        
        Quarto quarto = tipoQuartoFactory(numero, tipo, andar, precoDiaria);

        // Criando um DTO  com o quarto
        if (quarto != null) {
            QuartoDTO quartoDTO = new QuartoDTO();
            quartoDTO.setCodigoQuarto(quarto.getCodigoQuarto());
            quartoDTO.setNumero(quarto.getNumero());
            quartoDTO.setAndar(quarto.getAndar());
            quartoDTO.setPrecoDiaria(quarto.getPrecoDiaria());
            quartoDTO.setTipo(quarto.getTipo());
            quartoDTO.setCapacidadeMaxima(quarto.getCapacidadeMaxima());
    
            // Salvando o quarto no Banco de Dados
            QuartoDAO quartoDAO = new QuartoDAO();
            quartoDAO.cadastrarQuarto(quartoDTO);
            quarto.configurarOuvintes();
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
        Quarto q = new ReservaDAO().recuperarReserva(id).getQuarto();

        return tipoQuartoFactory(q.getNumero(), q.getTipo(), q.getAndar(), q.getPrecoDiaria()).getDescricaoBasica();
    }

    public static boolean removerQuarto(int numero, String categoria, int andar) {
        int id = new QuartoDAO().buscarIdQuarto(numero, categoria, andar);
        if (id != -1) {
            new QuartoDAO().removerQuarto(id);
            return true;
        }
        return false;
    }

    public static ArrayList<QuartoDTO> listarQuartos() {
        return new QuartoDAO().listarQuartos();
    }
}
