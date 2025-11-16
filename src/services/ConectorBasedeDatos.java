package services;
import java.sql.*;

public class ConectorBasedeDatos {
    String jdbcurl = "jdbc:oracle:thin:@localhost:1521:xe";
    String usuario = "";
    String contraseña = "";
    Connection conexionbasededatos;

    public void EstablecerConexion(String usuario, String contraseña){
        this.usuario = usuario;
        this.contraseña = contraseña;
        try {
            conexionbasededatos = DriverManager.getConnection(jdbcurl, usuario, contraseña);
            conexionbasededatos.setAutoCommit(false);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ResultSet CargarJuegos(){
        ResultSet resultSet = null;
        String sqlquery = "SELECT J.ID_JUEGO, J.TITULO, G.NOMBRE_GENERO, D.NOMBRE_DESARROLLADOR, J.FECHA_LANZAMIENTO "
                + "FROM JUEGOS J "
                + "LEFT JOIN GENEROS G ON J.ID_GENERO = G.ID_GENERO "
                + "LEFT JOIN DESARROLLADORES D ON J.ID_DESARROLLADOR = D.ID_DESARROLLADOR "
                + "ORDER BY J.ID_JUEGO";
        try {
            PreparedStatement preparedStatement = conexionbasededatos.prepareStatement(sqlquery);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return resultSet;
    }

    public void AgregarJuego(int id, String titulo, String fecha, int idDev, int idGen){
        String sql = "INSERT INTO JUEGOS(id_juego, titulo, fecha_lanzamiento, id_desarrollador, id_genero) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";
        try (PreparedStatement preparedStatement = conexionbasededatos.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,titulo);
            preparedStatement.setString(3, fecha);
            preparedStatement.setInt(4, idDev);
            preparedStatement.setInt(5, idGen);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            Rollback();
        }
    }

    public void ActualizarJuego(int id, String nuevotitulo){
        String sql = "UPDATE JUEGOS SET titulo = ? WHERE id_juego = ?";
        try (PreparedStatement preparedStatement = conexionbasededatos.prepareStatement(sql)){
            preparedStatement.setString(1,nuevotitulo);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            Rollback();
        }
    }

    public void EliminarJuego(int id){
        try {
            String sqlhija = "DELETE FROM JUEGOS_PLATAFORMA WHERE id_juego = ?";
            try (PreparedStatement preparedStatementhija = conexionbasededatos.prepareStatement(sqlhija)){
                preparedStatementhija.setInt(1,id);
                preparedStatementhija.executeUpdate();
            }
            String sqlpadre = "DELETE FROM JUEGOS WHERE id_juego = ?";
            try (PreparedStatement preparedStatementpadre = conexionbasededatos.prepareStatement(sqlpadre)){
                preparedStatementpadre.setInt(1,id);
                preparedStatementpadre.executeUpdate();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            Rollback();
        }
    }
    public void Commit(){
        try {
            if (conexionbasededatos != null){
                conexionbasededatos.commit();
                System.out.println("Cambios guardados");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void Rollback(){
        try {
            if (conexionbasededatos != null){
                conexionbasededatos.rollback();
                System.out.println("Revirtiendo cambios");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}