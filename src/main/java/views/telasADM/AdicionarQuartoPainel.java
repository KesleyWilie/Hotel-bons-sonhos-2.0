package views.telasADM;

import controller.QuartoController;
import utils.telas.ValidarCampos;
import views.ObjetosTelas.Botao;
import views.ObjetosTelas.EspacoTexto;
import views.PainelPadrao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdicionarQuartoPainel extends PainelPadrao {
    private EspacoTexto numero = new EspacoTexto();
    private EspacoTexto categoria = new EspacoTexto();
    private EspacoTexto andar = new EspacoTexto();
    private EspacoTexto precoDiaria = new EspacoTexto();
    private Botao adicionar = new Botao(false);

    public AdicionarQuartoPainel(){
        objetos();
        ouvinte();
        fundo("Adicionar quarto");
    }

    private void ouvinte() {
    adicionar.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            if (ValidarCampos.isVazio(numero, categoria, andar)) {
                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                return;
            }
            
            int numeroQuarto = Integer.parseInt(numero.getText());
            String tipoQuarto = categoria.getText().toLowerCase();
            int andarQuarto = Integer.parseInt(andar.getText());
            double precoDiariaQuarto = Double.parseDouble(precoDiaria.getText());
            
            try {
                QuartoController.criarQuarto(numeroQuarto, tipoQuarto, andarQuarto, precoDiariaQuarto);
                JOptionPane.showMessageDialog(null, "O quarto foi adicionado. ");
            } catch (IllegalArgumentException i) {
                JOptionPane.showMessageDialog(null, "Digite uma categoria de quarto válida e tente novamente", "Categoria inválida", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
}

    private void objetos() {
        int a = 363, c = 345,d = 57;
        numero.setBounds(a,196,c,d);
        categoria.setBounds(a,290,c,d);
        andar.setBounds(a,384,c,d);
        precoDiaria.setBounds(a,479,c,d);
        adicionar.setBounds(409,565,252,72);
        add(numero);
        add(categoria);
        add(andar);
        add(precoDiaria);
        add(adicionar);
    }
}
