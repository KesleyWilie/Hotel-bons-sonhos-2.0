package views.telasCliente;

import controller.UsuarioController;
import dto.ReservaDTO;
import views.JanelaPadrao;
import views.ObjetosTelas.Botao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.QuartoController;
import controller.ReservaController;
import views.ObjetosTelas.EspacoTexto;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VisualizarReservas extends JanelaPadrao {
    private String CPF;
    private Botao detalhar = new Botao(false);
    private Botao cancelar = new Botao(false);
    private Botao voltarMenu = new Botao(false);
    private DefaultTableModel modelo;
    private JTable tabela;
    private JScrollPane painelTabela;
    private EspacoTexto emailUsuario = new EspacoTexto();

    public VisualizarReservas(String CPF){
        this.CPF = CPF;
        objetos();
        ouvintes();
        tabela();
        fundo("VisualizarReservas");
    }

    private void ouvintes() {
        detalhar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linha = tabela.getSelectedRow();
                if (linha != -1){
                    Object idReserva = tabela.getValueAt(linha, 0);
                    String mensagem = QuartoController.recuperarInfoQuarto(Integer.parseInt(idReserva.toString()));
                    
                    JOptionPane.showMessageDialog(null, mensagem + idReserva, "INFO", JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma reserva para ver detalhes");
                }
            }
        });
        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                return;
            }
        });
        voltarMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarMenu(CPF);
            }
        });
    }

    public void tabela() {
        ArrayList<ReservaDTO> reservas = ReservaController.resgatarReservasDeClientes(CPF);
        modelo  = new DefaultTableModel();
        modelo.addColumn("ID reserva");
        modelo.addColumn("Tipo de quarto");
        modelo.addColumn("Checkin");
        modelo.addColumn("Checkout");
        modelo.addColumn("Preço Total");

        for(ReservaDTO reserva: reservas) {
            Object[] linha = new Object[5];
            linha[0] = reserva.getId();
            linha[1] = reserva.getQuarto().getTipo();
            linha[2] = reserva.getDataCheckin();
            linha[3] = reserva.getDataCheckout();
            linha[4] = reserva.getPrecoTotal();
            modelo.addRow(linha);
        }
        tabela = new JTable(modelo);
        tabela.setOpaque(false);
        tabela.setShowGrid(false);
        tabela.setBackground(new java.awt.Color(0, 0, 0, 0));
        tabela.setForeground(Color.BLACK);
        tabela.setFont(new Font("Arial",Font.PLAIN,26));
        tabela.setRowHeight(35);
        painelTabela = new JScrollPane(tabela);
        painelTabela.setOpaque(false);
        painelTabela.getViewport().setOpaque(false);
        painelTabela.setBounds(0,215,1280,288);
        add(painelTabela);
        repaint();
    }

    private void objetos() {
        detalhar.setBounds(324,559,262,87);
        cancelar.setBounds(708,559,262,87);
        voltarMenu.setBounds(1140,80,105,40);
        emailUsuario.setBounds(52,80,436,36);
        emailUsuario.setText(UsuarioController.recuperarEmail(CPF));
        add(cancelar);
        add(detalhar);
        add(voltarMenu);
        add(emailUsuario);
    }
}
