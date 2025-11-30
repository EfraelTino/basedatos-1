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

    public ResultSet EjecutarConsulta(String sql){
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = conexionbasededatos.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return resultSet;
    }

    public ResultSet CargarJuegos(){
        return EjecutarConsulta("SELECT * FROM V_DETALLE_JUEGOS");
    }
    public ResultSet CargarDesarrolladores(){
        return EjecutarConsulta("SELECT * FROM DESARROLLADORES ORDER BY ID_DESARROLLADOR");
    }
    public ResultSet CargarGeneros(){
        return EjecutarConsulta("SELECT * FROM GENEROS ORDER BY ID_GENERO");
    }

    public void AgregarJuego(int id, String titulo, String fecha, int idDev, int idGen){
        String sql = "{call SP_INSERTAR_JUEGO(?, ?, ?, ?, ?)}";
        try (CallableStatement callableStatement = conexionbasededatos.prepareCall(sql)){
            callableStatement.setInt(1,id);
            callableStatement.setString(2,titulo);
            callableStatement.setString(3, fecha);
            callableStatement.setInt(4, idDev);
            callableStatement.setInt(5, idGen);
            callableStatement.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            Rollback();
        }
    }

    public void AgregarDesarrollador(int id, String nombre){
        String sql = "{call SP_INSERTAR_DESARROLLADOR(?, ?)}";
        try (CallableStatement callableStatement = conexionbasededatos.prepareCall(sql)){
            callableStatement.setInt(1, id);
            callableStatement.setString(2, nombre);
            callableStatement.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            Rollback();
        }
    }

    public void AgregarGenero(int id, String nombre){
        String sql = "{call SP_INSERTAR_GENERO(?, ?)}";
        try (CallableStatement callableStatement = conexionbasededatos.prepareCall(sql)){
            callableStatement.setInt(1, id);
            callableStatement.setString(2, nombre);
            callableStatement.execute();
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
            try (PreparedStatement preparedStatement = conexionbasededatos.prepareStatement("DELETE FROM JUEGOS_PLATAFORMA WHERE id_juego = ?")){
                preparedStatement.setInt(1,id); preparedStatement.executeUpdate();
            }
            try (PreparedStatement preparedStatement = conexionbasededatos.prepareStatement("DELETE FROM JUEGOS WHERE id_juego = ?")){
                preparedStatement.setInt(1,id); preparedStatement.executeUpdate();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            Rollback();
        }
    }

    public void EliminarDesarrollador(int id){
        try (PreparedStatement preparedStatement = conexionbasededatos.prepareStatement("DELETE FROM DESARROLLADORES WHERE id_desarrollador = ?")){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            Rollback();
        }
    }

    public void EliminarGenero(int id){
        try (PreparedStatement preparedStatement = conexionbasededatos.prepareStatement("DELETE FROM GENEROS WHERE id_genero = ?")){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
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