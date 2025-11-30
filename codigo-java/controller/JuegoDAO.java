package controller;
import model.Juego;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JuegoDAO implements IJuegoDAO{
    private ArrayList<Juego> arrayListJuegos = new ArrayList<>();
    public void AgregarJuego(ResultSet resultSet){
        arrayListJuegos.clear();
        try {
            while (resultSet.next()){
                Juego juego = new Juego(resultSet.getInt("id_juego"), resultSet.getString("titulo"),resultSet.getString("nombre_genero"),resultSet.getString("nombre_desarrollador"),resultSet.getDate("fecha_lanzamiento"));
                arrayListJuegos.add(juego);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void AgregarJuego(int id, String titulo, String genero, String dev, String fecha){
        try {
            Date fechadate = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
            Juego juego = new Juego(id, titulo, genero, dev, fechadate);
            arrayListJuegos.add(juego);
        } catch (ParseException e){
            System.out.println(e.getMessage());
        }
    }

    public void ActualizarJuego(int id, String nuevotitulo){
        for (int i = 0; i < arrayListJuegos.size(); i++) {
            Juego juego = arrayListJuegos.get(i);
            if (juego.getId_juego() == id){
                juego.setTitulo(nuevotitulo);
                break;
            }
        }
    }

    public void EliminarJuego(int id){
        for (int i = arrayListJuegos.size() - 1; i >= 0; i--) {
            Juego juego = arrayListJuegos.get(i);
            if (juego.getId_juego() == id){
                arrayListJuegos.remove(i);
            }
        }
    }

    public ArrayList<Juego> getArrayListJuegos(){
        return arrayListJuegos;
    }
}