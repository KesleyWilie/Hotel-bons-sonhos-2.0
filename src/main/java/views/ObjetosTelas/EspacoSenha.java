package views.ObjetosTelas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EspacoSenha extends JPasswordField {
    public EspacoSenha(){
        setBackground(Color.white);
        setBorder(new EmptyBorder(0,0,0,0));
        setFont(new Font("Arial",1,26));
        setForeground(Color.white);
        setOpaque(false);
    }
}
