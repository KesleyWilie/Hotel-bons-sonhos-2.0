package views;

import controller.UsuarioController;
import dto.UsuarioDTO;
import views.ObjetosTelas.Botao;
import views.ObjetosTelas.EspacoSenha;
import views.ObjetosTelas.EspacoTexto;
import utils.telas.ValidarCampos;
import views.telasADM.TelaMenuADM;
import views.telasCliente.TelaMenuCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JanelaPadrao{
    private Botao botao = new Botao(false);
    private EspacoTexto usuario = new EspacoTexto();
    private EspacoSenha senha = new EspacoSenha();
    private Botao cadastrar = new Botao(false);

    public TelaLogin(){
        objetos();
        ouvintes();
        fundo("login");
    }

    private void ouvintes() {
        botao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ValidarCampos.isVazio(usuario, senha)) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                    return;
                }
                UsuarioDTO dto = new UsuarioDTO(null, usuario.getText(), null, null, new String(senha.getPassword()), false);
                UsuarioDTO usuario = UsuarioController.verificarLogin(dto);
                if (usuario != null) {
                    if (usuario.isAdmin()) {
                        new TelaMenuADM();
                    }else{
                        new TelaMenuCliente(usuario.getCPF());
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Email e/ou senha incorretos!");
                }
            }
        });

        cadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(UsuarioController.verificarUsuarios()){
                    new TelaCadastrarClientes();
                }else{
                    new TelaCadastroADM();
                }
                dispose();
            }
        });
    }

    private void objetos() {
        botao.setBounds(520,530,241,69);
        usuario.setBounds(502,235,277,49);
        senha.setBounds(502,323,277,49);
        cadastrar.setBounds(676,377,106,39);
        add(botao);
        add(usuario);
        add(senha);
        add(cadastrar);
    }
}
