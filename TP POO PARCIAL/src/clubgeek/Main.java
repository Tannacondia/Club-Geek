package clubgeek;

import clubgeek.model.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ClubGeek club = new ClubGeek();

        club.cargarDatos();

        if (club.listarAdmins().isEmpty()) {
            Administrador defaultAdmin = new Administrador("Mister", "00000000", "admin@clubgeek.com", "admin123", "Coordinador", 0);
            club.agregarAdministrador(defaultAdmin);
            System.out.println("Admin por defecto creado: admin@clubgeek.com / admin123");
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("===== BIENVENIDO A CLUB GEEK =====");
        int entrada;
        do {
            System.out.println("\n1) Iniciar sesión");
            System.out.println("2) Registrarme como socio");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            entrada = Integer.parseInt(sc.nextLine());

            switch (entrada) {
                case 1 -> iniciarSesion(club, sc);
                case 2 -> registroSocioInteractivo(club, sc);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (entrada != 0);

        club.guardarDatos();
        sc.close();
    }

    private static void iniciarSesion(ClubGeek club, Scanner sc) {
        System.out.println("\n¿Ingresar como?");
        System.out.println("1) Administrador");
        System.out.println("2) Socio");
        System.out.print("Opción: ");
        int tipo = Integer.parseInt(sc.nextLine());

        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        if (tipo == 1) {
            Administrador a = club.loginAdmin(email, pass);
            if (a == null) {
                System.out.println("Credenciales inválidas (admin).");
                return;
            }
            menuAdmin(club, a, sc);
        } else if (tipo == 2) {
            Socio s = club.loginSocio(email, pass);
            if (s == null) {
                System.out.println("Credenciales inválidas (socio).");
                return;
            }
            menuSocio(club, s, sc);
        } else {
            System.out.println("Opción inválida.");
        }
    }

    private static void registroSocioInteractivo(ClubGeek club, Scanner sc) {
        System.out.println("\n=== Registro de nuevo socio ===");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("DNI: ");
        String dni = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        System.out.print("Membresía (Básica/Premium): ");
        String mem = sc.nextLine();

        boolean exists = club.getSocios().stream().anyMatch(x -> x.getEmail().equalsIgnoreCase(email));
        if (exists) {
            System.out.println("Ya existe un socio con ese email.");
            return;
        }

        Socio s = new Socio(nombre, dni, email, pass, mem);
        club.agregarSocio(s);
        System.out.println("Registro exitoso. Ya podés iniciar sesión.");
    }

    // ----- MENU ADMIN -----
    private static void menuAdmin(ClubGeek club, Administrador admin, Scanner sc) {
        int opt;
        do {
            System.out.println("\n=== PANEL ADMIN: " + admin.getNombre() + " ===");
            System.out.println("1) Agregar juego");
            System.out.println("2) Crear torneo");
            System.out.println("3) Listar torneos");
            System.out.println("4) Listar socios");
            System.out.println("5) Listar juegos");
            System.out.println("6) Resumen club geek");
            System.out.println("7) Guardar datos ahora");
            System.out.println("0) Cerrar sesión");
            System.out.print("Opción: ");
            opt = Integer.parseInt(sc.nextLine());

            switch (opt) {
                case 1 -> registrarJuegoInteractivo(club, sc);
                case 2 -> crearTorneoInteractivo(club, admin, sc);
                case 3 -> {
                    System.out.println("\nTorneos:");
                    for (Torneo t : club.listarTorneos()) System.out.println(t);
                }
                case 4 -> {
                    System.out.println("\nSocios:");
                    for (Socio s : club.listarSocios()) System.out.println(s);
                }
                case 5 -> {
                    System.out.println("\nJuegos:");
                    for (Juego j : club.listarJuegos()) System.out.println(j);
                }
                case 6 -> System.out.println(club);
                case 7 -> club.guardarDatos();
                case 0 -> System.out.println("Cerrando sesión admin...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opt != 0);
    }

    // === CAMBIADO: permitir volver con 0 ===
    private static void registrarJuegoInteractivo(ClubGeek club, Scanner sc) {
        System.out.println("\nTipo de juego:");
        System.out.println("1) Juego de Rol");
        System.out.println("2) Juego de Cartas");
        System.out.println("3) Juego de Estrategia");
        System.out.println("0) Volver al menú admin");
        System.out.print("Opción: ");
        int t = Integer.parseInt(sc.nextLine());
        if (t == 0) {
            System.out.println("Volviendo al menú admin...");
            return;
        }

        System.out.print("Nombre del juego (o 0 para cancelar): ");
        String nombre = sc.nextLine();
        if (nombre.equals("0")) {
            System.out.println("Operación cancelada. Volviendo al menú admin...");
            return;
        }

        switch (t) {
            case 1 -> {
                System.out.print("Cantidad jugadores: ");
                int c = Integer.parseInt(sc.nextLine());
                club.agregarJuego(new JuegoDeRol(nombre, true, c));
            }
            case 2 -> {
                System.out.print("Cantidad cartas: ");
                int c = Integer.parseInt(sc.nextLine());
                club.agregarJuego(new JuegoDeCartas(nombre, true, c));
            }
            case 3 -> {
                System.out.print("Nivel dificultad: ");
                String n = sc.nextLine();
                club.agregarJuego(new JuegoDeEstrategia(nombre, true, n));
            }
            default -> System.out.println("Tipo inválido.");
        }
    }
    private static void crearTorneoInteractivo(ClubGeek club, Administrador admin, Scanner sc) {
        System.out.print("Nombre torneo (o 0 para cancelar): ");
        String nombre = sc.nextLine();
        if (nombre.equals("0")) {
            System.out.println("Operación cancelada. Volviendo al menú admin...");
            return;
        }

        System.out.print("Premio: ");
        String premio = sc.nextLine();
        System.out.print("Juego asociado (nombre o 0 para cancelar): ");
        String juegoNombre = sc.nextLine();
        if (juegoNombre.equals("0")) {
            System.out.println("Operación cancelada. Volviendo al menú admin...");
            return;
        }

        Juego j = club.buscarJuegoPorNombre(juegoNombre);
        if (j == null) {
            System.out.println("Juego no encontrado.");
            return;
        }

        Torneo t = new Torneo((long)(Math.random()*100000), nombre, LocalDate.now().plusWeeks(1), premio, j, admin);
        club.agregarTorneo(t);
    }

    // ----- MENU SOCIO -----
    private static void menuSocio(ClubGeek club, Socio socio, Scanner sc) {
        int opt;
        do {
            System.out.println("\n=== PANEL SOCIO: " + socio.getNombre() + " ===");
            System.out.println("1) Registrar reserva");
            System.out.println("2) Registrar préstamo");
            System.out.println("3) Ver resumen del club");
            System.out.println("0) Cerrar sesión");
            System.out.print("Opción: ");
            opt = Integer.parseInt(sc.nextLine());
            switch (opt) {
                case 1 -> registrarReservaInteractiva(club, socio, sc);
                case 2 -> registrarPrestamoInteractiva(club, socio, sc);
                case 3 -> resumenSocio(club, socio, sc);
                case 0 -> System.out.println("Cerrando sesión socio...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opt != 0);
    }

    private static void resumenSocio(ClubGeek club, Socio socio, Scanner sc) {
        System.out.println(socio);
        System.out.println("\nReservas:");
        for (Reserva r : club.listarReservas()) System.out.println(r);
        System.out.println("\nPrestamos:");
        for (Prestamo p : club.listarPrestamos()) System.out.println(p);
    }

    // === CAMBIADO: permitir volver con 0 ===
    private static void registrarReservaInteractiva(ClubGeek club, Socio socio, Scanner sc) {
        System.out.println("\nJuegos disponibles:");
        for (Juego j : club.listarJuegos()) System.out.println(j);
        System.out.println("Ingrese el nombre del juego a reservar o 0 para volver:");
        System.out.print("> ");
        String juegoNombre = sc.nextLine();

        if (juegoNombre.equals("0")) {
            System.out.println("Volviendo al menú de socio...");
            return;
        }

        Juego j = club.buscarJuegoPorNombre(juegoNombre);
        if (j == null) {
            System.out.println("Juego no encontrado.");
            return;
        }

        Reserva r = new Reserva(socio, j, LocalDate.now());
        club.registrarReserva(r);
    }

    // === CAMBIADO: permitir volver con 0 ===
    private static void registrarPrestamoInteractiva(ClubGeek club, Socio socio, Scanner sc) {
        System.out.println("\nJuegos disponibles:");
        for (Juego j : club.listarJuegos()) System.out.println(j);
        System.out.println("Ingrese el nombre del juego a prestar o 0 para volver:");
        System.out.print("> ");
        String juegoNombre = sc.nextLine();

        if (juegoNombre.equals("0")) {
            System.out.println("Volviendo al menú de socio...");
            return;
        }

        Juego j = club.buscarJuegoPorNombre(juegoNombre);
        if (j == null) {
            System.out.println("Juego no encontrado.");
            return;
        }

        Prestamo p = new Prestamo(socio, j, LocalDate.now(), LocalDate.now().plusDays(7));
        club.registrarPrestamo(p);
    }
}








