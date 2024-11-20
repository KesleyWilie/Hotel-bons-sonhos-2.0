package views.telasCliente;

import views.JanelaPadrao;
import views.ObjetosTelas.Botao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import java.text.ParseException;

import java.sql.Date;

import controller.QuartoController;
import controller.ReservaController;
import dto.QuartoDTO;
import utils.telas.ValidarCampos;
import views.ObjetosTelas.TextosTelas;

public class InformacoesQuarto extends JanelaPadrao {
    private Botao anterior = new Botao(false);
    private Botao reservar = new Botao(false);
    private Botao proximo = new Botao(false);
    private Botao maisImagens = new Botao(false);
    private TextosTelas codigo = new TextosTelas();
    private TextosTelas diaria = new TextosTelas();
    private TextosTelas capacidade = new TextosTelas();
    private TextosTelas numero = new TextosTelas();
    private TextosTelas andar = new TextosTelas();
    private TextosTelas tipo = new TextosTelas();
    private QuartoDTO quarto;
    private String CPFCliente;
    private Botao menu = new Botao(false);


    
    private ArrayList<QuartoDTO> quartos;
    private int indiceAtual = 0;

    public InformacoesQuarto(String CPFCliente) {
        this.CPFCliente = CPFCliente;
        quartos = QuartoController.listarQuartos();
        objetos();
        ouvintes();
        fundo("Informações de Quartos");
        exibirQuartoAtual();
        imagem(quartos.get(indiceAtual).getTipo());
    }

    private void ouvintes() {
        anterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indiceAtual > 0) {
                    indiceAtual--; 
                    exibirQuartoAtual(); 
                }
            }
        });

        proximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indiceAtual < quartos.size() - 1) {
                    indiceAtual++; 
                    exibirQuartoAtual(); 
                }
            }
        });

        reservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String checkin = JOptionPane.showInputDialog(null, "Digite a data de checkin: (dd/mm/yyyy)");
                String checkout = JOptionPane.showInputDialog(null, "Digite a data de checkout: (dd/mm/yyyy)");
        
                if (checkin != null && checkout != null) {
                    try {
                        // Validando datas
                        Date checkinDate = ValidarCampos.parseDate(checkin);
                        Date checkoutDate = ValidarCampos.parseDate(checkout);
        
                        if(ReservaController.consultarDisponibilidade(quartos.get(indiceAtual), checkinDate, checkoutDate)) {
                            
                            double precoTotal = ReservaController.reservarQuarto(quartos.get(indiceAtual), CPFCliente, checkinDate, checkoutDate);
                            
                            
                            JOptionPane.showMessageDialog(null, "Reserva realizada! Preço total: R$ " + precoTotal);
                        } else {
                            JOptionPane.showMessageDialog(null, "Quarto indisponível, porém, enviaremos um e-mail quando novos quartos forem adicionados!", "Quarto indisponível", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
        
                    } catch (ParseException pe) {
                        JOptionPane.showMessageDialog(null, "Formato de data inválido. Por favor, use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    } catch (IllegalArgumentException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
        });
        
        maisImagens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imagem(quartos.get(indiceAtual).getTipo());
            }
        });
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarMenu(CPFCliente);
            }
        });
    }

    private void objetos() {
        int b = 499, c = 163, d = 76;
        anterior.setBounds(517, b, c, d);
        reservar.setBounds(724, b, c, d);
        proximo.setBounds(932, b, c, d);
        maisImagens.setBounds(108,533,234,54);
        codigo.setBounds(726,208,33,35);
        diaria.setBounds(662,282,91,35);
        capacidade.setBounds(767,356,30,35);
        numero.setBounds(1022,208,50,35);
        andar.setBounds(994,282,30,35);
        tipo.setBounds(978,356,100,35);
        menu.setBounds(1127,84,124,52);
        add(codigo);
        add(diaria);
        add(capacidade);
        add(numero);
        add(andar);
        add(tipo);
        add(anterior);
        add(reservar);
        add(proximo);
        add(maisImagens);
        add(menu);
    }
    // imagens de quartos do tipo luxo,simples e suite
    private void imagem(String tipo){
        int numero = ((int)(1 + Math.random() * 3));
        remover();
        fundo(tipo+numero);
    }


    private void exibirQuartoAtual() {
        quarto = quartos.get(indiceAtual);
        codigo.setText(String.valueOf(quarto.getCodigoQuarto()));
        diaria.setText(String.valueOf(quarto.getPrecoDiaria()));
        capacidade.setText(String.valueOf(quarto.getCapacidadeMaxima()));
        numero.setText(String.valueOf(quarto.getNumero()));
        andar.setText(String.valueOf(quarto.getAndar()));
        tipo.setText(String.valueOf(quarto.getTipo()));
        repaint();
    }
}
