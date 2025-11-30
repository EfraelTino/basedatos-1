package views;
import controller.JuegoController;
import services.ConectorBasedeDatos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MainView {
    ConectorBasedeDatos conectorBasedeDatos;
    JuegoController juegoController;
    public void Main2(){
        boolean ejecutar = true;
        Scanner scanner = new Scanner(System.in);
        juegoController = new JuegoController();
        while (ejecutar){
            System.out.println("1. Conectar a la base de datos");
            System.out.println("2. Mostrar...");
            System.out.println("3. Insertar...");
            System.out.println("4. Actualizar titulo de juego");
            System.out.println("5. Eliminar...");
            System.out.println("6. Salir");
            System.out.print("Ingrese una opcion: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion){
                case 1:
                    ConectorBasedeDatos(scanner);
                    break;
                case 2:
                    MenuMostrar(scanner);
                    break;
                case 3:
                    MenuInsertar(scanner);
                    break;
                case 4:
                    ActualizarJuego(scanner);
                    break;
                case 5:
                    MenuEliminar(scanner);
                    break;
                case 6:
                    ejecutar = false;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Ingrese una opcion valida porfavor");
                    break;
            }
        }
    }
    private void ConectorBasedeDatos(Scanner scanner){
        System.out.print("Ingrese el usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String contraseña = scanner.nextLine();
        conectorBasedeDatos = new ConectorBasedeDatos();
        conectorBasedeDatos.EstablecerConexion(usuario, contraseña);
        System.out.println("Base de datos conectada");
    }

    private void ImprimirResulset(ResultSet resultSet, String colID, String colNombre){
        try {
            while (resultSet.next()){
                System.out.println("ID: " + resultSet.getInt(colID) + " Nombre: " + resultSet.getString(colNombre));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void MenuMostrar(Scanner scanner){
        if (conectorBasedeDatos == null){
            System.out.println("Primero conectese a la base de datos");
            return;
        }
        System.out.println("1. Ver Juegos");
        System.out.println("2. Ver Desarroladores");
        System.out.println("3. Ver Generos");
        System.out.print("Ingrese una opcion: ");
        int opcion = Integer.parseInt(scanner.nextLine());
        if (opcion == 1){
            ResultSet resultSet = conectorBasedeDatos.CargarJuegos();
            juegoController.CargarJuegos(resultSet);
            juegoController.ImprimirTodosJuegos();
        }
        else if (opcion == 2){
            ImprimirResulset(conectorBasedeDatos.CargarDesarrolladores(),"ID_DESARROLLADOR", "NOMBRE_DESARROLLADOR");
        }
        else if (opcion == 3){
            ImprimirResulset(conectorBasedeDatos.CargarGeneros(),"ID_GENERO", "NOMBRE_GENERO");
        }
        else {
            System.out.println("Opcion invalida");
        }
    }

    private void MenuInsertar(Scanner scanner){
        if (conectorBasedeDatos == null){
            System.out.println("Primero conectese a la base de datos");
            return;
        }
        System.out.println("1. Insertar Juego");
        System.out.println("2. Insertar Desarrollador");
        System.out.println("3. Insertar Genero");
        System.out.print("Ingrese una opcion: ");

        int opcion = Integer.parseInt(scanner.nextLine());

        if (opcion == 1){
            System.out.print("Ingrese el id del nuevo juego(101-999): ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese el titulo del juego: ");
            String titulo = scanner.nextLine();
            System.out.print("Ingrese la fecha de lanzamiento (YYYY-MM-DD): ");
            String fecha = scanner.nextLine();
            System.out.print("Ingrese el ID del desarrollador: ");
            int idDev = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese el ID del genero del videojuego: ");
            int idGen = Integer.parseInt(scanner.nextLine());

            conectorBasedeDatos.AgregarJuego(id, titulo, fecha, idDev, idGen);
            conectorBasedeDatos.Commit();
            juegoController.AgregarJuego(id,titulo, "N/A", "N/A",fecha);
            System.out.println("Juego agregado.");
        }
        else if (opcion == 2){
            System.out.print("Ingrese el ID del nuevo desarrollador(1 - 9): ");
            int id =Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese el nombre del desarrollador: ");
            String nombre = scanner.nextLine();

            conectorBasedeDatos.AgregarDesarrollador(id, nombre);
            conectorBasedeDatos.Commit();
            System.out.println("Desarrollador agregado.");
        }
        else if (opcion == 3){
            System.out.print("Ingrese el ID del nuevo genero(10-100): ");
            int id =Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese el nombre del genero: ");
            String nombre = scanner.nextLine();

            conectorBasedeDatos.AgregarGenero(id, nombre);
            conectorBasedeDatos.Commit();
            System.out.println("Género agregado.");
        }
        else {
            System.out.println("Opcion invalida");
        }
    }

    private void ActualizarJuego(Scanner scanner){
        System.out.print("Ingrese el ID del juego a actualizar: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (juegoController.ExisteJuego(id) == false){
            System.out.println("El juego no existe en la lista");
             return;
        }
        System.out.print("Ingrese el nuevo titulo: ");
        String nuevotitulo = scanner.nextLine();
        conectorBasedeDatos.ActualizarJuego(id,nuevotitulo);
        conectorBasedeDatos.Commit();
        juegoController.ActualizarJuego(id,nuevotitulo);
        juegoController.ImprimirTodosJuegos();
    }

    private void MenuEliminar(Scanner scanner){
        if (conectorBasedeDatos == null){
            System.out.println("Primero conectese a la base de datos");
            return;
        }
        System.out.println("1. Eliminar un Juego");
        System.out.println("2. Eliminar un Desarrollador");
        System.out.println("3. Eliminar un Genero");
        System.out.print("Ingrese una opcion: ");
        int opcion = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese el id a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (opcion == 1){
            conectorBasedeDatos.EliminarJuego(id);
            juegoController.EliminarJuego(id);
        } else if (opcion == 2){
            conectorBasedeDatos.EliminarDesarrollador(id);
        } else if (opcion == 3){
            conectorBasedeDatos.EliminarGenero(id);
        }
        else {
            System.out.println("Opcion invalida");
        }
        conectorBasedeDatos.Commit();
    }
}