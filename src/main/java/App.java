import controller.UsuarioController;
import views.TelaCadastroADM;
import views.TelaLogin;

public class App {
    public static void main (String[] args) {
        if (UsuarioController.verificarUsuarios()) {
            new TelaLogin();
        } else {
            new TelaCadastroADM();
        }
    }
}