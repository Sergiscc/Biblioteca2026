package educastur.es.sergioscc.biblioteca2025;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
     
/**
 * Biblioteca2025
 *
 * @author 1dawd09
 * @version 1.2
 */
public class Biblioteca2025 {

    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<Libro> libros = new ArrayList();
    private static ArrayList<Usuario> usuarios = new ArrayList();
    private static ArrayList<Prestamo> prestamos = new ArrayList();
    private static ArrayList<Prestamo> prestamosHist = new ArrayList();
    private static LocalDate hoy = LocalDate.now();

    public static void main(String[] args) {
        cargaDatos();
        //listadosConStreams();
        ordenarConStreams();
    }
    
    //#region CARGAR DATOS
    public static void cargaDatos() {
        cargaLibros();
        cargaUsuarios();
        cargaPrestamos();
        cargarPrestamosHist();
    }

    public static void listadosConStreams(){
        //Listados generales de libros y usuarios con STREAMS
        System.out.println("Libros listados desde un STREAM:");
        libros.stream().forEach(l->System.out.println(l));
        System.out.println("\nUsuarios listados desde un STREAM:");
        usuarios.stream().forEach(u->System.out.println(u));
        
        //Listados selectivos (filter) con STREAMS
        System.out.println("\nLibros de la seccion aventuras:");
        libros.stream().filter(l-> l.getGenero().equalsIgnoreCase("aventuras"))
                       .forEach(l->System.out.println(l));
        
        System.out.println("\nLibros de la seccion novela negra o del autor JRR tolkien:");
        libros.stream().filter(l-> l.getGenero().equalsIgnoreCase("novela negra")
                       || l.getAutor().equalsIgnoreCase("jrr tolkien"))
                       .forEach(l->System.out.println(l));
        
        System.out.println("\nPrestamos fuera de plazo:");
        prestamos.stream().filter(p-> p.getFechaDev().isBefore(LocalDate.now()))
                       .forEach(p->System.out.println(p));
        
        System.out.println("\nPrestamos activos y no activos del usuario(teclear NOMBRE):");
        String nombre=sc.next();
        prestamos.stream().filter(p->p.getUsuarioPrest().getNombre().equalsIgnoreCase(nombre))
                .forEach(p->System.out.println(p));
        prestamosHist.stream().filter(p->p.getUsuarioPrest().getNombre().equalsIgnoreCase(nombre))
                .forEach(p->System.out.println(p));
        
        System.out.println("\nPrestamos Fuera de plazo de un usuario(teclear NOMBRE):");
        String nombre2=sc.next();
        prestamos.stream().filter(p->p.getUsuarioPrest().getNombre().equalsIgnoreCase(nombre2)
                        && p.getFechaDev().isBefore(LocalDate.now()))
                        .forEach(p->System.out.println(p));
        
        System.out.println("\nPrestamos activos de libros del genero aventuras:");
        prestamos.stream().filter(p->p.getLibroPrest().getGenero().equalsIgnoreCase("aventuras"))
                .forEach(p->System.out.println(p)); 
    }
    
    public static void ordenarConStreams(){
        System.out.println("\nListado de Libros ordenados alfabeticamente por titulo:");
        libros.stream().sorted(Comparator.comparing(Libro::getTitulo)).forEach(l->System.out.println(l));       
    
    
        System.out.println("\nListado de Prestamos ordenados por fecha de prestamo:");
        prestamos.stream().sorted(Comparator.comparing(Prestamo::getFechaPrest)).forEach(p->System.out.println(p));
        
        System.out.println("\nListado de Libros ordenados por numeros de prestamo:");
        libros.stream().sorted(Comparator.comparing(l->numPrestamosLibro(l.getIsbn()))).forEach(l->System.out.println(l)); 
    }
    
    public static int numPrestamosLibro(String isbn) {
        int cont=0;
        for (Prestamo p : prestamos) {
           if (p.getLibroPrest().getIsbn().equalsIgnoreCase(isbn)){
                cont++;
            } 
        }  
        for (Prestamo p : prestamosHist) {
           if (p.getLibroPrest().getIsbn().equalsIgnoreCase(isbn)){
                cont++;
            } 
        }
        return cont;    
    }
    
    public static void cargaLibros() {
        libros.add(new Libro("1-11", "El Hobbit", "JRR Tolkien", "Aventuras", 3));
        libros.add(new Libro("1-22", "El Silmarillon", "JRR Tolkien", "Aventuras", 3));
        libros.add(new Libro("1-33", "El Medico", "N. Gordon", "Aventuras", 4));
        libros.add(new Libro("1-44", "Chaman", "N. Gordon", "Aventuras", 3));
        libros.add(new Libro("1-55", "Momo", "M. Ende", "Aventuras", 2));
        libros.add(new Libro("1-66", "Paraiso inhabitado", "A.M.Matute", "Aventuras", 2));
        libros.add(new Libro("1-77", "Olvidado Rey Gudu", "A.M.Matute", "Aventuras", 0));
        libros.add(new Libro("1-88", "El ultimo barco", "D.Villar", "Novela Negra", 3));
        libros.add(new Libro("1-99", "Ojos de agua", "D.Villar", "Novela Negra", 0));
    }

    public static void cargaUsuarios() {
        usuarios.add(new Usuario("11", "Ana", "ana@email.com", "621111111"));
        usuarios.add(new Usuario("22", "David", "david@email.com", "622222222"));
        usuarios.add(new Usuario("33", "Bea", "bea@email.com", "623333333"));
        usuarios.add(new Usuario("44", "Lucas", "lucas@email.com", "624444444"));
        usuarios.add(new Usuario("55", "Carlota", "carlota@email.com", "625555555"));
        usuarios.add(new Usuario("66", "Juan", "juan@email.com", "626666666"));
    }

    public static void cargaPrestamos() {
        //PRESTAMOS "NORMALES" REALIZADOS HOY Y QUE SE HAN DE DEVOLVER EN 15 DÍAS
        prestamos.add(new Prestamo(libros.get(0), usuarios.get(0), hoy, hoy.plusDays(15)));
        prestamos.add(new Prestamo(libros.get(1), usuarios.get(0), hoy, hoy.plusDays(15)));
        prestamos.add(new Prestamo(libros.get(5), usuarios.get(0), hoy, hoy.plusDays(15)));
        prestamos.add(new Prestamo(libros.get(6), usuarios.get(4), hoy, hoy.plusDays(15)));
        prestamos.add(new Prestamo(libros.get(6), usuarios.get(1), hoy, hoy.plusDays(15)));
        //PRESTAMOS QUE YA TENIAN QUE HABER SIDO DEVUELTOS PORQUE SU FECHA DE DEVOLUCIÓN ES ANTERIOR A HOY
        prestamos.add(new Prestamo(libros.get(5), usuarios.get(1), hoy.minusDays(17), hoy.minusDays(2)));
        prestamos.add(new Prestamo(libros.get(1), usuarios.get(4), hoy.minusDays(18), hoy.minusDays(3)));
        prestamos.add(new Prestamo(libros.get(2), usuarios.get(4), hoy.minusDays(20), hoy.minusDays(5)));
        prestamos.add(new Prestamo(libros.get(3), usuarios.get(4), hoy.minusDays(20), hoy.minusDays(5)));
    }

    public static void cargarPrestamosHist() {
        //PRESTAMOS HISTORICOS QUE YA HAN SIDO DEVUELTOS Y POR TANTO ESTÁN EN LA COLECCION prestamosHist
        prestamosHist.add(new Prestamo(libros.get(0), usuarios.get(0), hoy.minusDays(20), hoy.minusDays(5)));
        prestamosHist.add(new Prestamo(libros.get(2), usuarios.get(0), hoy.minusDays(20), hoy.minusDays(5)));
        prestamosHist.add(new Prestamo(libros.get(7), usuarios.get(4), hoy.minusDays(20), hoy.minusDays(5)));
        prestamosHist.add(new Prestamo(libros.get(5), usuarios.get(4), hoy.minusDays(20), hoy.minusDays(5)));
        prestamosHist.add(new Prestamo(libros.get(1), usuarios.get(1), hoy.minusDays(20), hoy.minusDays(5)));
        prestamosHist.add(new Prestamo(libros.get(7), usuarios.get(2), hoy.minusDays(15), hoy));
        prestamosHist.add(new Prestamo(libros.get(6), usuarios.get(3), hoy.minusDays(15), hoy));
    }

    //#endregion
    //#region BUSCADORES
    /**
     * Buscar un libro utilizando ISBN
     *
     * @param libro ISBN del libro que se quiere buscar
     * @return la posición del libro si existe; -1 si no se encuentra
     */
    public static int buscarLibro(String libro) {
        int pos = -1;
        int i = 0;
        for (Libro l : libros) {
            if (l.getIsbn().equalsIgnoreCase(libro)) {
                pos = i;
                break;
            }
            i++;
        }
        return pos;
    }

    /**
     * Buscar un usuario utilizando DNI
     *
     * @param usuario DNI del usuario que se quiere buscar
     * @return la posición del usuario si existe; -1 si no se encuentra
     */
    public static int buscarUsuario(String usuario) {
        int pos = -1;
        int i = 0;
        for (Usuario u : usuarios) {
            if (u.getDni().equalsIgnoreCase(usuario)) {
                pos = i;
                break;
            }
            i++;
        }
        return pos;
    }

    /**
     * Buscar un prestamos utilizando DNI y el ISBN
     *
     * @param usuario DNI del usuario que se quiere buscar
     * @param libro ISBN del libro que se quiere buscar
     * @return la posición del prestamo si existe; -1 si no se encuentra
     */
    public static int buscarPrestamo(String usuario, String libro) {
        int usuarioPos = buscarUsuario(usuario);
        Usuario u = usuarios.get(usuarioPos);
        int libroPos = buscarLibro(libro);
        Libro l = libros.get(libroPos);
        int pos = -1;
        int i = 0;
        for (Prestamo p : prestamos) {
            if (p.getUsuarioPrest().equals(u) && p.getLibroPrest().equals(l)) {
                if (u.getDni().equalsIgnoreCase(usuario) && l.getIsbn().equalsIgnoreCase(libro)) {
                    pos = i;
                    break;
                }
            }
            i++;
        }
        return pos;
    }

    //#endregion
    //#region MENUS
    public static void menuPrincipal() {
        int opcion;
        do {
            System.out.println("\n\tMENU DE OPCIONES");
            System.out.println("\t| 0 - SALIR");
            System.out.println("\t| 1 - GESTION LIBROS");
            System.out.println("\t| 2 - GESTION USUARIOS");
            System.out.println("\t| 3 - GESTION PRESTAMOS");
            System.out.println("\t| 4 - LISTADOS");

            System.out.print("Teclea el numero: ");

            opcion = sc.nextInt();
            System.out.println();

            switch (opcion) {
                // MENU DE OPCIONES
                case 1 -> {
                    menuLibros();
                }
                case 2 -> {
                    menuUsuarios();
                }
                case 3 -> {
                    menuPrestamos();
                }
                case 4 -> {
                    menuListados();
                }
            }
        } while (opcion != 0);
    }

    public static void menuLibros() {
        int opcion;
        do {
            System.out.println("\n\tMENU DE LIBROS");
            System.out.println("\t| 0 - SALIR");
            System.out.println("\t| 1 - AÑADIR LIBRO");
            System.out.println("\t| 2 - LISTADO DE LIBROS");
            System.out.println("\t| 3 - MODIFICAR LIBRO");
            System.out.println("\t| 4 - ELIMINAR LIBRO");

            System.out.print("Teclea el numero: ");

            opcion = sc.nextInt();
            System.out.println();

            switch (opcion) {
                // MENU DE OPCIONES
                case 1 -> {
                    crearLibro();
                    sc.nextLine();
                }
                case 2 -> {
                    listarLibros();
                }
                case 3 -> {
                    menuModificarLibro();
                }
                case 4 -> {
                    eliminarLibro();
                }
            }
        } while (opcion != 0);
    }

    public static void menuUsuarios() {
        int opcion;
        do {
            System.out.println("\n\tMENU DE USUARIOS");
            System.out.println("\t| 0 - SALIR");
            System.out.println("\t| 1 - REGISTRAR USUARIO");
            System.out.println("\t| 2 - LISTADO DE USUARIOS");
            System.out.println("\t| 3 - MODIFICAR USUARIO");
            System.out.println("\t| 4 - ELIMINAR USUARIO");

            System.out.print("Teclea el numero: ");

            opcion = sc.nextInt();
            System.out.println();

            switch (opcion) {
                // MENU DE OPCIONES
                case 1 -> {
                    crearUsuario();
                }
                case 2 -> {
                    listarUsuarios();
                }
                case 3 -> {
                    menuModificarUsuario();
                }
                case 4 -> {
                    eliminarUsuario();
                }
            }
        } while (opcion != 0);
    }

    public static void menuPrestamos() {
        int opcion;
        do {
            System.out.println("\n\tMENU DE PRESTAMOS");
            System.out.println("\t| 0 - SALIR");
            System.out.println("\t| 1 - PRESTAMO");
            System.out.println("\t| 2 - DEVOLUCION");
            System.out.println("\t| 3 - PRORROGAR PRESTAMO");
            System.out.println("\t| 4 - LISTADO DE PRESTAMOS");
            System.out.println("\t| 5 - PRESTAMOS POR USUARIO");

            System.out.print("Teclea el numero: ");

            opcion = sc.nextInt();
            System.out.println();

            switch (opcion) {
                // MENU DE OPCIONES
                case 1 -> {
                    crearPrestamo();
                }
                case 2 -> {
                    eliminarPrestamo(); // Crear devolucion
                }
                case 3 -> {
                    prorrogarPrestamo();
                }
                case 4 -> {
                    listarPrestamos();
                }
                case 5 -> {
                    listarPrestamosPorUsuario(null);
                }
            }
        } while (opcion != 0);
    }

    public static void menuListados() {
        int opcion;
        do {
            System.out.println("\n\tMENU DE LISTADOS");
            System.out.println("\t| 0 - SALIR");
            System.out.println("\t| 1 - LISTADO DE LIBROS");
            System.out.println("\t| 2 - LISTADO DE USUARIOS");
            System.out.println("\t| 3 - LISTADO DE PRESTAMOS");
            System.out.println("\t| 4 - PRESTAMOS POR USUARIO");

            System.out.print("Teclea el numero: ");

            opcion = sc.nextInt();
            System.out.println();

            switch (opcion) {
                // MENU DE OPCIONES
                case 1 -> {
                    listarLibros();
                }
                case 2 -> {
                    listarUsuarios();
                }
                case 3 -> {
                    listarPrestamos();
                }
                case 4 -> {
                    listarPrestamosPorUsuario(null);
                }
            }
        } while (opcion != 0);
    }

    //#region MENUS MODIFICAR
    public static void menuModificarLibro() {
        System.out.print("Escribe el ISBN del libro a modificar: ");
        int libroPos = buscarLibro(sc.next());
        if (libroPos == -1) {
            System.out.println("El libro no existe.");
            return;
        }
        Libro l = libros.get(libroPos);
        System.out.println("Titulo:\t" + l.getTitulo());
        System.out.println("Autor:\t" + l.getAutor());
        System.out.println("Genero:\t" + l.getGenero());
        System.out.println("Ejemplares:\t" + l.getEjemplares());
        int opcion;
        do {
            System.out.println("\n\tMENU DE MODIFICAR LIBROS");
            System.out.println("\t| 0 - SALIR");
            System.out.println("\t| 1 - ISBN");
            System.out.println("\t| 2 - TITULO");
            System.out.println("\t| 3 - AUTOR");
            System.out.println("\t| 4 - GENERO");
            System.out.println("\t| 5 - EJEMPLARES");

            System.out.print("Teclea el numero: ");

            opcion = sc.nextInt();
            System.out.println();

            switch (opcion) {
                // MENU DE OPCIONES
                case 1 -> {
                    System.out.println("Se va a modificar el ISBN: " + l.getIsbn());
                    System.out.print("Escribe el nuevo ISBN: ");
                    l.setIsbn(sc.next());
                }
                case 2 -> {
                    System.out.println("Se va a modificar el titulo: " + l.getTitulo());
                    System.out.print("Escribe el nuevo titulo: ");
                    sc.nextLine();
                    l.setTitulo(sc.nextLine());
                }
                case 3 -> {
                    System.out.println("Se va a modificar el autor: " + l.getTitulo());
                    System.out.print("Escribe el nuevo autor: ");
                    l.setAutor(sc.next());
                }
                case 4 -> {
                    System.out.println("Se va a modificar el genero: " + l.getGenero());
                    System.out.print("Escribe el nuevo autor: ");
                    l.setGenero(sc.next());
                }
                case 5 -> {
                    System.out.println("Se va a modificar la cantidad de ejemplares: " + l.getEjemplares());
                    System.out.print("Escribe la cantidad de ejemplares: ");
                    sc.nextLine();
                    l.setEjemplares(sc.nextInt());
                }
            }
        } while (opcion != 0);
    }

    public static void menuModificarUsuario() {
        System.out.print("Escribe el DNI del usuario a modificar: ");
        int usuarioPos = buscarUsuario(sc.next());
        if (usuarioPos == -1) {
            System.out.println("El usuario no existe.");
            return;
        }
        Usuario u = usuarios.get(usuarioPos);
        System.out.println("Nombre:\t" + u.getNombre());
        System.out.println("Email:\t" + u.getEmail());
        System.out.println("Telefono:\t" + u.getTelefono());
        int opcion;
        do {
            System.out.println("\n\tMENU DE MODIFICAR USUARIOS");
            System.out.println("\t| 0 - SALIR");
            System.out.println("\t| 1 - DNI");
            System.out.println("\t| 2 - NOMBRE");
            System.out.println("\t| 3 - EMAIL");
            System.out.println("\t| 4 - TELEFONO");

            System.out.print("Teclea el numero: ");

            opcion = sc.nextInt();
            System.out.println();

            switch (opcion) {
                // MENU DE OPCIONES
                case 1 -> {
                    System.out.println("Se va a modificar el DNI: " + u.getDni());
                    System.out.print("Escribe el nuevo DNI: ");
                    u.setDni(sc.next());
                }
                case 2 -> {
                    System.out.println("Se va a modificar el nombre: " + u.getNombre());
                    System.out.print("Escribe el nuevo nombre: ");
                    u.setNombre(sc.next());
                }
                case 3 -> {
                    System.out.println("Se va a modificar el email: " + u.getEmail());
                    System.out.print("Escribe el nuevo email: ");
                    u.setEmail(sc.next());
                }
                case 4 -> {
                    System.out.println("Se va a modificar el telefono: " + u.getTelefono());
                    System.out.print("Escribe el nuevo telefono: ");
                    u.setTelefono(sc.next());
                }
            }
        } while (opcion != 0);
    }
    //#endregion

    //#endregion
    //#region CREAR
    public static void crearLibro() {
        System.out.println("Vamos a registrar un libro nuevo");
        System.out.print("Escribe el ISBN del libro: ");
        String isbn = sc.next();
        System.out.print("Escribe el titulo: ");
        sc.nextLine();
        String titulo = sc.nextLine();
        System.out.print("Escribe el nombre del autor: ");
        String autor = sc.nextLine();
        System.out.print("Escribe el genero del libro: ");
        String genero = sc.nextLine();
        System.out.print("Escribe la cantidad de ejemplares: ");
        int ejemplares = sc.nextInt();
        libros.add(new Libro(isbn, titulo, autor, genero, ejemplares));
        System.out.println("ISBN:\t" + isbn);
        System.out.println("Titulo:\t" + titulo);
        System.out.println("Autor:\t" + autor);
        System.out.println("Genero:\t" + genero);
        System.out.println("Ejemplares:\t" + ejemplares);
    }

    public static void crearUsuario() {
        System.out.println("Vamos a registrar un usuario nuevo");
        System.out.print("Escribe el DNI del usuario: ");
        String dni = sc.next();
        System.out.print("Escribe el nombre: ");
        String nombre = sc.next();
        System.out.print("Escribe el email: ");
        String email = sc.next();
        System.out.print("Escribe el telefono: ");
        String telefono = sc.next();
        usuarios.add(new Usuario(dni, nombre, email, telefono));
        System.out.println("DNI:\t" + dni);
        System.out.println("Nombre:\t" + nombre);
        System.out.println("Email:\t" + email);
        System.out.println("Telefono:\t" + telefono);
    }

    public static void crearPrestamo() {
        System.out.println("Vamos a hacer un prestamo");
        System.out.print("Escribe el DNI del usuario: ");
        String usuarioPrest = sc.next();
        int usuarioPos = buscarUsuario(usuarioPrest);
        if (usuarioPos == -1) {
            System.out.println("Ese usuario no esta registrado");
        } else {
            Usuario usuario = usuarios.get(usuarioPos);
            System.out.println("Nombre:\t" + usuario.getNombre());
            System.out.println("Email:\t" + usuario.getEmail());
            System.out.println("Telefono:\t" + usuario.getTelefono());
            System.out.println("Escribe el ISBN del libro: ");
            String libroPrest = sc.next();
            int libroPos = buscarLibro(libroPrest);
            if (libroPos == -1) {
                System.out.println("Ese libro no existe en la biblioteca");
            } else {
                Libro libro = libros.get(libroPos);
                if (libro.getEjemplares() <= 0) {
                    System.out.println("No quedan ejemplares disponibles de este libro.");
                } else {
                    System.out.println("Titulo:\t" + libro.getTitulo());
                    System.out.println("Autor:\t" + libro.getAutor());
                    System.out.println("Genero:\t" + libro.getGenero());
                    System.out.println("Ejemplares:\t" + libro.getEjemplares());
                    LocalDate fechaPrest = hoy;
                    LocalDate fechaDev = hoy.plusDays(15);
                    prestamos.add(new Prestamo(libro, usuario, fechaPrest, fechaDev));
                    libro.setEjemplares(libro.getEjemplares() - 1);
                    System.out.println("Prestamo realizado correctamente. Quedan " + libro.getEjemplares() + " ejemplares de " + libro.getTitulo() + ".");
                    System.out.println(usuario.getNombre() + " debe devolverlo antes de " + fechaDev);
                }
            }
        }
    }

    //#endregion
    //#region LISTAR
    public static void listar() {
        System.out.println("\n\tLibros\n");
        listarLibros();
        System.out.println("\n\tUsuarios\n");
        listarUsuarios();
        System.out.println("\n\tPrestamos\n");
        listarPrestamos();
    }

    public static void listarLibros() {
        for (Libro l : libros) {
            System.out.print(l.getIsbn() == null ? "" : l.getIsbn() + "\t");
            System.out.print(l.getTitulo() == null ? "" : l.getTitulo() + "\t");
            System.out.print(l.getAutor() == null ? "" : l.getAutor() + "\t");
            System.out.print(l.getGenero() == null ? "" : l.getGenero() + "\t");
            System.out.print(l.getEjemplares() + "");
            System.out.println();
        }
    }

    public static void listarUsuarios() {
        for (Usuario u : usuarios) {
            System.out.print(u.getDni() == null ? "" : u.getDni() + "\t");
            System.out.print(u.getNombre() == null ? "" : u.getNombre() + "\t");
            System.out.print(u.getEmail() == null ? "" : u.getEmail() + "\t");
            System.out.print(u.getTelefono() == null ? "" : u.getTelefono() + "");
            System.out.println();
        }
    }

    public static void listarPrestamos() {
        for (Prestamo p : prestamos) {
            System.out.print(p.getLibroPrest() == null ? "" : p.getLibroPrest() + "\t");
            System.out.print(p.getUsuarioPrest() == null ? "" : p.getUsuarioPrest() + "\t[fechaPrest= ");
            System.out.print(p.getFechaPrest() == null ? "" : p.getFechaPrest() + "\tfechaDev= ");
            System.out.print(p.getFechaDev() == null ? "" : p.getFechaDev() + "]");
            System.out.println();
        }
    }

    public static void listarPrestamosPorUsuario(String usuario) {
        int usuarioPos;
        if (usuario == null) {
            System.out.print("Escribe el DNI del usuario para ver sus prestamos: ");
            usuario = sc.next();
        }
        usuarioPos = buscarUsuario(usuario);
        if (usuarioPos == -1) {
            System.out.println("El usuario no existe.");
            return;
        }
        Usuario u = usuarios.get(usuarioPos);
        System.out.println("Nombre:\t" + u.getNombre());
        System.out.println("Email:\t" + u.getEmail());
        System.out.println("Telefono:\t" + u.getTelefono());
        System.out.println();
        for (Prestamo p : prestamos) {
            if (p.getUsuarioPrest().equals(u)) {
                System.out.print(p.getLibroPrest() == null ? "" : p.getLibroPrest() + "-");
                System.out.print(p.getFechaPrest() == null ? "" : p.getFechaPrest() + "-");
                System.out.print(p.getFechaDev() == null ? "" : p.getFechaDev() + "");
                System.out.println();
            }
        }
    }

    //#endregion
    //#region ELIMINAR
    public static void eliminarLibro() {
        System.out.print("Escribe el ISBN del libro a eliminar: ");
        int libroPos = buscarLibro(sc.next());
        if (libroPos == -1) {
            System.out.println("El libro no existe.");
            return;
        }
        Libro l = libros.get(libroPos);
        System.out.println("Titulo:\t" + l.getTitulo());
        System.out.println("Autor:\t" + l.getAutor());
        System.out.println("Genero:\t" + l.getGenero());
        System.out.println("Ejemplares:\t" + l.getEjemplares());
        System.out.println("¿Esta informacion es correcta y deseas eliminar el libro? (Si/No)");
        if (sc.next().equalsIgnoreCase("Si")) {
            libros.remove(libroPos);
        }
    }

    public static void eliminarUsuario() {
        System.out.print("Escribe el DNI del usuario a eliminar: ");
        int usuarioPos = buscarUsuario(sc.next());
        if (usuarioPos == -1) {
            System.out.println("El usuario no existe.");
            return;
        }
        Usuario u = usuarios.get(usuarioPos);
        System.out.println("Nombre:\t" + u.getNombre());
        System.out.println("Email:\t" + u.getEmail());
        System.out.println("Telefono:\t" + u.getTelefono());
        System.out.println("¿Esta informacion es correcta y deseas eliminar el usuario? (Si/No)");
        if (sc.next().equalsIgnoreCase("Si")) {
            usuarios.remove(usuarioPos);
        }
    }

    public static void eliminarPrestamo() {
        System.out.print("Escribe el DNI del usuario de la devolucion: ");
        String usuario = sc.next();
        int usuarioPos = buscarUsuario(usuario);
        if (usuarioPos == -1) {
            System.out.println("El usuario no existe.");
            return;
        }
        listarPrestamosPorUsuario(usuario);
        System.out.print("Escribe el ISBN del libro a devolver: ");
        String libro = sc.next();
        int libroPos = buscarLibro(libro);
        if (libroPos == -1) {
            System.out.println("El libro no existe.");
            return;
        }
        int prestamoPos = buscarPrestamo(usuario, libro);
        if (prestamoPos == -1) {
            System.out.println("El usuario no tiene ese libro prestado.");
            return;
        }
        Libro l = libros.get(libroPos);
        System.out.println();
        System.out.println("Titulo:\t" + l.getTitulo());
        System.out.println("Autor:\t" + l.getAutor());
        System.out.println("Genero:\t" + l.getGenero());
        System.out.println("Ejemplares:\t" + l.getEjemplares());
        System.out.println("¿Esta informacion es correcta y deseas realizar la devolucion? (Si/No)");
        if (sc.next().equalsIgnoreCase("Si")) {
            Prestamo p = prestamos.get(libroPos);
            p.setFechaDev(hoy);
            prestamosHist.add(p);
            prestamos.remove(prestamoPos);
            l.setEjemplares(l.getEjemplares() + 1);
            System.out.println("Devolucion realizada correctamente. Quedan " + l.getEjemplares() + " ejemplares.");
        }
    }

    //#endregion
    //#region Extra
    public static void prorrogarPrestamo() {
        System.out.print("Escribe el DNI del usuario para prorrogar su prestamo: ");
        String usuario = sc.next();
        int usuarioPos = buscarUsuario(usuario);
        if (usuarioPos == -1) {
            System.out.println("El usuario no existe.");
            return;
        }
        listarPrestamosPorUsuario(usuario);
        System.out.print("Escribe el ISBN del libro a prorrogar: ");
        String libro = sc.next();
        int libroPos = buscarLibro(libro);
        if (libroPos == -1) {
            System.out.println("El libro no existe.");
            return;
        }
        int PrestamoPos = buscarPrestamo(usuario, libro);
        if (PrestamoPos == -1) {
            System.out.println("El usuario no tiene ese libro prestado.");
            return;
        }
        Libro l = libros.get(libroPos);
        System.out.println();
        System.out.println("Titulo:\t" + l.getTitulo());
        System.out.println("Autor:\t" + l.getAutor());
        System.out.println("Genero:\t" + l.getGenero());
        System.out.println("¿Esta informacion es correcta y deseas realizar la prorroga? (Si/No)");
        if (sc.next().equalsIgnoreCase("Si")) {
            Prestamo p = prestamos.get(PrestamoPos);
            System.out.print("Escribe la cantidad de dias que se prorrogara la devolucion: ");
            p.setFechaDev(p.getFechaDev().plusDays(sc.nextInt()));
            System.out.println("Prorroga realizada correctamente. El libro debe ser devuelto en " + p.getFechaDev() + ".");
        }
    }
}
