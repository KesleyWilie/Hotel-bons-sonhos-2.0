package views.telasCliente;

import views.JanelaPadrao;
import views.ObjetosTelas.Botao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaMenuCliente extends JanelaPadrao {
    private Botao buscar = new Botao(false);
    private Botao visualizar = new Botao(false);
    private String CPFCLiente;



    public TelaMenuCliente(String CPFCLiente){
        this.CPFCLiente = CPFCLiente;
        objetos();
        ouvintes();
        fundo("menuCliente");
    }

    private void ouvintes() {
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InformacoesQuarto(CPFCLiente);
                dispose();
            }
        });
        visualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizarReservas(CPFCLiente);
                dispose();
            }
        });
    }

    private void objetos() {
        buscar.setBounds(174,260,382,238);
        visualizar.setBounds(731,260,382,238);
        add(buscar);
        add(visualizar);
    }
}
