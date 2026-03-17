import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {

    private static final String URL = "jdbc:mysql://localhost:3306/EbacDever";
    private static final String USER = "aluno";
    private static final String PASSWORD = "ebac123";

    public static void main(String[] args) {
        System.out.println("Iniciando operações CRUD...\n");

        inserirUsuario("João Silva", "joao.antigo@email.com");
        inserirUsuario("Maria Oliveira", "maria@email.com");
        inserirUsuario("Carlos Souza", "carlos@email.com");

        System.out.println("\n--- Lista após Inserções ---");
        listarUsuarios();

        atualizarEmail("João Silva", "joao@email.com");

        System.out.println("\n--- Lista após Atualização ---");
        listarUsuarios();

        excluirUsuario("Maria Oliveira");

        System.out.println("\n--- Lista Final após Exclusão ---");
        listarUsuarios();
    }

    // Método para Obter Conexão
    private static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // C: Create (Inserir)
    public static void inserirUsuario(String nome, String email) {
        String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Usuário inserido com sucesso: " + nome);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    public static void listarUsuarios() {
        String sql = "SELECT id, nome, email FROM usuarios";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");

                // Formatação exigida
                System.out.println("ID: " + id + " - Nome: " + nome + " - Email: " + email);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
    }

    public static void atualizarEmail(String nome, String novoEmail) {
        String sql = "UPDATE usuarios SET email = ? WHERE nome = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoEmail);
            stmt.setString(2, nome);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Email atualizado com sucesso para o usuário: " + nome);
            } else {
                System.out.println("Nenhum usuário encontrado com o nome: " + nome);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar email: " + e.getMessage());
        }
    }

    public static void excluirUsuario(String nome) {
        String sql = "DELETE FROM usuarios WHERE nome = ?";

        try (Connection connection = conectar();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nome);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Usuário excluído com sucesso: " + nome);
            } else {
                System.out.println("Nenhum usuário encontrado com o nome: " + nome);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
        }
    }
}