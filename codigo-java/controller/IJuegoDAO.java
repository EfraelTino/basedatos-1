package controller;
import java.sql.ResultSet;

public interface IJuegoDAO {
    public void AgregarJuego(ResultSet resultSet);
    public void AgregarJuego(int id, String titulo, String genero, String dev, String fecha);
    public void ActualizarJuego(int id, String nuevotitulo);
    public void EliminarJuego(int id);
}