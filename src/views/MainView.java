package views;

import services.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainView {
    DatabaseConnector databaseConnector = new DatabaseConnector();

    public void Run() {
        PrintAllGames();
    }

    private void PrintAllGames() {
        try {
            String query = "SELECT * FROM JUEGOS";
            ResultSet resultSet = databaseConnector.resultSet(query);
            while (resultSet.next()) {
                String id = resultSet.getString("id_juego");
                String title = resultSet.getString("titulo");
                String gender = resultSet.getString("genero");
                String dateL = resultSet.getString("fecha_lanzamiento");
                System.out.println("Código: " + id + " Título: " + title + " Género: " + gender +
                        " Fecha Lanzamiento: " + dateL);
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
