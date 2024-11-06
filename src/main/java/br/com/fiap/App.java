import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";
    private static final String USUARIO = "RM557788";
    private static final String SENHA = "230502";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA)) {
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
        }
    }
}