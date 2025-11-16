package model;
import java.util.Date;

public class Juego {
    private int id_juego;
    private String titulo;
    private String nombre_genero;
    private String nombre_desarrollador;
    private Date fecha_lanzamiento;

    public Juego(int id_juego, String titulo, String nombre_genero, String nombre_desarrollador, Date fecha_lanzamiento){
        this.id_juego = id_juego;
        this.titulo = titulo;
        this.nombre_genero = nombre_genero;
        this.nombre_desarrollador = nombre_desarrollador;
        this.fecha_lanzamiento = fecha_lanzamiento;
    }

    public void ImprimirDatos(){
        System.out.println("ID: " + id_juego + " Titulo: " + titulo + " Genero: " + nombre_genero + " Desarrollador: " + nombre_desarrollador + " Lanzamiento: " + fecha_lanzamiento);
    }
    public void setId_juego(int id_juego){
        this.id_juego = id_juego;
    }
    public int getId_juego(){
        return id_juego;
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public String getTitulo(){
        return titulo;
    }
    public void setNombre_genero(String nombre_genero){
        this.nombre_genero = nombre_genero;
    }
    public String getNombre_genero(){
        return nombre_genero;
    }
    public void setNombre_desarrollador(String nombre_desarrollador){
        this.nombre_desarrollador = nombre_desarrollador;
    }
    public String getNombre_desarrollador(){
        return nombre_desarrollador;
    }
    public void setFecha_lanzamiento(Date fecha_lanzamiento){
        this.fecha_lanzamiento = fecha_lanzamiento;
    }
    public Date getFecha_lanzamiento(){
        return fecha_lanzamiento;
    }
}