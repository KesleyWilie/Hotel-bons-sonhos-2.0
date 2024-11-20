package views.telasADM;

import controller.UsuarioController;
import dto.UsuarioDTO;
import utils.telas.ValidarCampos;
import views.ObjetosTelas.Botao;
import views.ObjetosTelas.EspacoTexto;
import views.ObjetosTelas.TextosTelas;
import views.PainelPadrao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InformacoesCliente extends PainelPadrao {
    private EspacoTexto dado = new EspacoTexto();
    private Botao pesquisar = new Botao(false);
    private TextosTelas nome = new TextosTelas();
    private TextosTelas email = new TextosTelas();
    private TextosTelas telefone = new TextosTelas();
    private TextosTelas senha = new TextosTelas();
    private TextosTelas CPF = new TextosTelas();

    public InformacoesCliente(){
        objetos();
        ouvintes();
        fundo("informacoesCliente");
    }

    private void ouvintes() {
        pesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (ValidarCampos.isVazio(dado) || !ValidarCampos.isCPFValido(dado)) {
                    JOptionPane.showMessageDialog(null, "Por favor, digite um CPF válido.");
                    return;
                }

                UsuarioDTO cliente = UsuarioController.resgatarCliente(dado.getText());
                if (cliente != null){
                    nome.setText(cliente.getNome());
                    email.setText(cliente.getEmail());
                    telefone.setText(cliente.getTelefone());
                    CPF.setText(cliente.getCPF());
                    senha.setText(cliente.getSenha());
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(null,"Não foi possível encontrar o CPF digitado", "Cliente não encontrado", JOptionPane.ERROR_MESSAGE);
                    pesquisar.setText("");
                    return;
                }
            }
        });
    }

    private void objetos() {
        int a = 392,c = 299,d = 50;
        dado.setBounds(158,79,812,63);
        pesquisar.setBounds(101,83,56,56);
        nome.setBounds(a,195,c,d);
        email.setBounds(a,275,c,d);
        senha.setBounds(a,355,c,d);
        telefone.setBounds(a,435,c,d);
        CPF.setBounds(a,515,c,d);
        add(dado);
        add(pesquisar);
        add(nome);
        add(email);
        add(telefone);
        add(senha);
        add(CPF);
    }
}
