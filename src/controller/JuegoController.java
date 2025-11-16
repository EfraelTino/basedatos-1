package controller;
import java.sql.ResultSet;

public class JuegoController {
    JuegoDAO juegoDAO = new JuegoDAO();

    public void CargarJuegos(ResultSet resultSet){
        juegoDAO.AgregarJuego(resultSet);
    }
    public void ImprimirTodosJuegos(){
        if (juegoDAO.getArrayListJuegos().isEmpty()){
            System.out.println("No hay juegos cargados en la lista");
            return;
        }
        for (int i = 0; i < juegoDAO.getArrayListJuegos().size(); i++) {
            juegoDAO.getArrayListJuegos().get(i).ImprimirDatos();
        }
    }

    public void AgregarJuego(int id, String titulo, String genero, String dev, String fecha){
        juegoDAO.AgregarJuego(id, titulo, genero, dev, fecha);
    }

    public void ActualizarJuego(int id, String nuevotitulo){
        juegoDAO.ActualizarJuego(id, nuevotitulo);
    }

    public void EliminarJuego(int id){
        juegoDAO.EliminarJuego(id);
    }

    public boolean ExisteJuego(int id){
        boolean existe = false;
        for (int i = 0; i < juegoDAO.getArrayListJuegos().size(); i++) {
            if (juegoDAO.getArrayListJuegos().get(i).getId_juego() == id){
                existe = true;
                break;
            }
        }
        return existe;
    }
}