package utils.telas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Locale;

import java.sql.Date;

public class ValidarCampos {
    public static boolean isVazio(JTextField... campos) {
        for (JTextField campo : campos) {
            if (campo.getText() == null || campo.getText().trim().isEmpty()) {
                return true; 
            }
        }
        return false; 
    }    

    public static boolean verificarSenhas(JPasswordField senha, JPasswordField confirmarSenha) {
        String senhaString = String.valueOf(senha.getPassword());
        String confirmar = String.valueOf(confirmarSenha.getPassword());
        
        return senhaString.equals(confirmar);
    }

    public static boolean isEmailValido(JTextField campo) {
        String email = campo.getText().trim();
        
        // Expressão regular para validar o e-mail
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        
        return matcher.matches();
    }

    public static boolean isCPFValido(JTextField campo) {
        String cpf = campo.getText().trim();
        
        // Remover pontos e traços do CPF
        cpf = cpf.replace(".", "").replace("-", "");
        
        // Verificar se o CPF tem exatamente 11 caracteres e é numérico
        if (cpf.length() == 11 && cpf.matches("\\d+")) {
            return true; 
        } else {
            return false; 
        }
    }

    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        java.util.Date utilDate = DATE_FORMAT.parse(dateString);

        return new Date(utilDate.getTime());
    }
}
