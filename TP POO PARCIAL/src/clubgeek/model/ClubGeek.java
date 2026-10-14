package clubgeek.model;

import clubgeek.interfaces.IClubManagement;
import clubgeek.interfaces.IReservationSystem;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ClubGeek implements IClubManagement, IReservationSystem {

    private List<Socio> socios;
    private List<Juego> juegos;
    private List<Reserva> reservas;
    private List<Prestamo> prestamos;
    private List<Torneo> torneos;
    private List<Administrador> administradores;

    // Archivos
    private static final String SOCIOS_FILE = "socios.txt";
    private static final String ADMINS_FILE = "admins.txt";
    private static final String JUEGOS_FILE = "juegos.txt";
    private static final String TORNEOS_FILE = "torneos.txt";
    private static final String RESERVAS_FILE = "reservas.txt";
    private static final String PRESTAMOS_FILE = "prestamos.txt";

    public ClubGeek() {
        this.socios = new ArrayList<>();
        this.juegos = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.torneos = new ArrayList<>();
        this.administradores = new ArrayList<>();
    }

    // =========================
    // GESTIÓN DE SOCIOS
    // =========================
    @Override
    public void agregarSocio(Socio socio) {
        socios.add(socio);
        System.out.println("✅ Socio agregado: " + socio.getNombre());
        guardarSocios(); // persistir
    }

    public Socio buscarSocioPorNombre(String nombre) {
        if (nombre == null) return null;
        for (Socio s : socios) {
            if (s.getNombre() != null && s.getNombre().equalsIgnoreCase(nombre.trim())) {
                return s;
            }
        }
        return null;
    }
    public Socio registrarSocio(String nombre, String dni, String email, String password, String membresia) {
        // Evitamos duplicados por nombre (ya que usás buscarSocioPorNombre)
        if (buscarSocioPorNombre(nombre) != null) {
            System.out.println("⚠️ Ya existe un socio con ese nombre.");
            return null;
        }

        // Crear nuevo socio
        Socio socio = new Socio(nombre,dni, email, password, membresia);

        // Reutilizar tu método existente
        agregarSocio(socio);

        System.out.println("✅ Nuevo socio registrado: " + socio.getNombre());
        return socio;
    }


    public Socio loginSocio(String email, String password) {
        return socios.stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email) && s.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    public List<Socio> listarSocios() { return socios; }

    // =========================
    // GESTIÓN DE JUEGOS
    // =========================
    @Override
    public void agregarJuego(Juego juego) {
        juegos.add(juego);
        System.out.println("🎮 Juego agregado: " + juego.getNombre());
        guardarJuegos();
    }

    public Juego buscarJuegoPorNombre(String nombre) {
        if (nombre == null) return null;
        for (Juego j : juegos) {
            if (j.getNombre() != null && j.getNombre().equalsIgnoreCase(nombre.trim())) {
                return j;
            }
        }
        return null;
    }

    public List<Juego> listarJuegos() { return juegos; }

    // =========================
    // GESTIÓN DE RESERVAS Y PRÉSTAMOS
    // =========================
    @Override
    public void registrarReserva(Reserva reserva) {
        reservas.add(reserva);
        reserva.getSocio().agregarReserva(reserva);
        System.out.println("📅 Reserva registrada: " + reserva);
        guardarReservas();
        guardarSocios(); // socio cambió (lista de reservas)
    }

    @Override
    public void registrarPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
        prestamo.getSocio().agregarPrestamo(prestamo);
        System.out.println("📘 Préstamo registrado: " + prestamo);
        guardarPrestamos();
        guardarSocios();
    }

    public List<Reserva> listarReservas() { return reservas; }
    public List<Prestamo> listarPrestamos() { return prestamos; }

    // =========================
    // GESTIÓN DE TORNEOS
    // =========================
    public void agregarTorneo(Torneo torneo) {
        torneos.add(torneo);
        if (torneo.getAdministrador() != null) torneo.getAdministrador().agregarTorneo(torneo);
        System.out.println("🏆 Torneo agregado: " + torneo.getNombre());
        guardarTorneos();
    }

    public List<Torneo> listarTorneos() { return torneos; }

    public Torneo buscarTorneoPorNombre(String nombre) {
        for (Torneo t : torneos) {
            if (t.getNombre().equalsIgnoreCase(nombre)) return t;
        }
        return null;
    }

    public void eliminarTorneo(Torneo torneo) {
        torneos.remove(torneo);
        System.out.println("❌ Torneo eliminado: " + torneo.getNombre());
        guardarTorneos();
    }

    // =========================
    // ADMIN - login y gestión
    // =========================
    public void agregarAdministrador(Administrador a) {
        administradores.add(a);
        guardarAdmins();
    }

    public Administrador loginAdmin(String email, String password) {
        return administradores.stream()
                .filter(a -> a.getEmail().equalsIgnoreCase(email) && a.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    public List<Administrador> listarAdmins() { return administradores; }

    // =========================
    // PERSISTENCIA (archivos TXT)
    // =========================

    public void cargarDatos() {
        cargarAdmins();
        cargarSocios();
        cargarJuegos();
        cargarTorneos();     // necesidades: juegos y admins ya cargados
        cargarReservas();    // necesidades: socios y juegos cargados
        cargarPrestamos();   // necesidades: socios y juegos cargados
        System.out.println("📂 Datos cargados desde archivos.");
    }

    public void guardarDatos() {
        guardarAdmins();
        guardarSocios();
        guardarJuegos();
        guardarTorneos();
        guardarReservas();
        guardarPrestamos();
        System.out.println("💾 Datos guardados en archivos de texto.");
    }

    // ----- SOCIOS -----
    private void guardarSocios() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SOCIOS_FILE))) {
            for (Socio s : socios) {
                pw.println(escape(s.getNombre()) + ";" + escape(s.getDni()) + ";" +
                        escape(s.getEmail()) + ";" + escape(s.getPassword()) + ";" +
                        escape(s.getMembresia()));
            }
        } catch (IOException e) { System.out.println("Error guardando socios: " + e.getMessage()); }
    }

    private void cargarSocios() {
        socios.clear();
        File f = new File(SOCIOS_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";", -1);
                if (p.length >= 5) {
                    Socio s = new Socio(unescape(p[0]), unescape(p[1]), unescape(p[2]), unescape(p[3]), unescape(p[4]));
                    socios.add(s);
                }
            }
        } catch (IOException e) { /* ignore */ }
    }

    // ----- ADMINS -----
    private void guardarAdmins() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ADMINS_FILE))) {
            for (Administrador a : administradores) {
                pw.println(escape(a.getNombre()) + ";" + escape(a.getDni()) + ";" +
                        escape(a.getEmail()) + ";" + escape(a.getPassword()) + ";" +
                        escape(a.getCargo()) + ";" + a.getSalario());
            }
        } catch (IOException e) { System.out.println("Error guardando admins: " + e.getMessage()); }
    }

    private void cargarAdmins() {
        administradores.clear();
        File f = new File(ADMINS_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";", -1);
                if (p.length >= 6) {
                    Administrador a = new Administrador(unescape(p[0]), unescape(p[1]), unescape(p[2]), unescape(p[3]),
                            unescape(p[4]), Double.parseDouble(p[5]));
                    administradores.add(a);
                }
            }
        } catch (IOException e) { /* ignore */ }
    }

    // ----- JUEGOS -----
    private void guardarJuegos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(JUEGOS_FILE))) {
            for (Juego j : juegos) {
                String tipo = j.getClass().getSimpleName();
                String extra = "";
                if (j instanceof JuegoDeRol r) extra = String.valueOf(r.getCantidadJugadores());
                if (j instanceof JuegoDeCartas c) extra = String.valueOf(c.getCantidadCartas());
                if (j instanceof JuegoDeEstrategia e) extra = e.getNivelDificultad();
                pw.println(tipo + ";" + escape(j.getNombre()) + ";" + j.isDisponible() + ";" + escape(extra));
            }
        } catch (IOException e) { System.out.println("Error guardando juegos: " + e.getMessage()); }
    }

    private void cargarJuegos() {
        juegos.clear();
        File f = new File(JUEGOS_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";", -1);
                if (p.length >= 4) {
                    String tipo = p[0];
                    String nombre = unescape(p[1]);
                    boolean disp = Boolean.parseBoolean(p[2]);
                    String extra = unescape(p[3]);
                    Juego j = switch (tipo) {
                        case "JuegoDeRol" -> new JuegoDeRol(nombre, disp, parseIntSafe(extra, 1));
                        case "JuegoDeCartas" -> new JuegoDeCartas(nombre, disp, parseIntSafe(extra, 52));
                        case "JuegoDeEstrategia" -> new JuegoDeEstrategia(nombre, disp, extra);
                        default -> null;
                    };
                    if (j != null) juegos.add(j);
                }
            }
        } catch (IOException e) { /* ignore */ }
    }

    // ----- TORNEOS -----
    private void guardarTorneos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(TORNEOS_FILE))) {
            for (Torneo t : torneos) {
                String juegoName = t.getJuego() != null ? t.getJuego().getNombre() : "";
                String adminEmail = t.getAdministrador() != null ? t.getAdministrador().getEmail() : "";
                pw.println(t.getId() + ";" + escape(t.getNombre()) + ";" + t.getFecha() + ";" +
                        escape(t.getPremio()) + ";" + escape(juegoName) + ";" + escape(adminEmail));
            }
        } catch (IOException e) { System.out.println("Error guardando torneos: " + e.getMessage()); }
    }

    private void cargarTorneos() {
        torneos.clear();
        File f = new File(TORNEOS_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";", -1);
                if (p.length >= 6) {
                    Long id = Long.parseLong(p[0]);
                    String nombre = unescape(p[1]);
                    LocalDate fecha = LocalDate.parse(p[2]);
                    String premio = unescape(p[3]);
                    String juegoName = unescape(p[4]);
                    String adminEmail = unescape(p[5]);

                    Juego juego = buscarJuegoPorNombre(juegoName);
                    Administrador admin = administradores.stream()
                            .filter(a -> a.getEmail().equalsIgnoreCase(adminEmail)).findFirst().orElse(null);

                    Torneo t = new Torneo(id, nombre, fecha, premio, juego, admin);
                    torneos.add(t);
                    if (admin != null) admin.agregarTorneo(t);
                }
            }
        } catch (IOException e) { /* ignore */ }
    }

    // ----- RESERVAS -----
    private void guardarReservas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RESERVAS_FILE))) {
            for (Reserva r : reservas) {
                pw.println(escape(r.getSocio().getEmail()) + ";" + escape(r.getJuego().getNombre()) + ";" + r.getFecha());
            }
        } catch (IOException e) { System.out.println("Error guardando reservas: " + e.getMessage()); }
    }

    private void cargarReservas() {
        reservas.clear();
        File f = new File(RESERVAS_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";", -1);
                if (p.length >= 3) {
                    String socioEmail = unescape(p[0]);
                    String juegoName = unescape(p[1]);
                    LocalDate fecha = LocalDate.parse(p[2]);

                    Socio s = socios.stream().filter(x -> x.getEmail().equalsIgnoreCase(socioEmail)).findFirst().orElse(null);
                    Juego j = buscarJuegoPorNombre(juegoName);
                    if (s != null && j != null) {
                        Reserva r = new Reserva(s, j, fecha);
                        reservas.add(r);
                        s.agregarReserva(r);
                    }
                }
            }
        } catch (IOException e) { /* ignore */ }
    }

    // ----- PRESTAMOS -----
    private void guardarPrestamos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PRESTAMOS_FILE))) {
            for (Prestamo pr : prestamos) {
                pw.println(escape(pr.getSocio().getEmail()) + ";" + escape(pr.getJuego().getNombre()) + ";" +
                        pr.getFechaPrestamo() + ";" + pr.getFechaDevolucion());
            }
        } catch (IOException e) { System.out.println("Error guardando prestamos: " + e.getMessage()); }
    }

    private void cargarPrestamos() {
        prestamos.clear();
        File f = new File(PRESTAMOS_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(";", -1);
                if (p.length >= 4) {
                    String socioEmail = unescape(p[0]);
                    String juegoName = unescape(p[1]);
                    LocalDate inicio = LocalDate.parse(p[2]);
                    LocalDate fin = LocalDate.parse(p[3]);

                    Socio s = socios.stream().filter(x -> x.getEmail().equalsIgnoreCase(socioEmail)).findFirst().orElse(null);
                    Juego j = buscarJuegoPorNombre(juegoName);
                    if (s != null && j != null) {
                        Prestamo pr = new Prestamo(s, j, inicio, fin);
                        prestamos.add(pr);
                        s.agregarPrestamo(pr);
                    }
                }
            }
        } catch (IOException e) { /* ignore */ }
    }

    // ----- UTILIDADES -----
    private static int parseIntSafe(String s, int fallback) {
        try { return Integer.parseInt(s); } catch (Exception e) { return fallback; }
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace(";", "\\;"); // simple escaping
    }
    private static String unescape(String s) {
        if (s == null) return "";
        return s.replace("\\;", ";").replace("\\\\", "\\");
    }

    // getters / setters for lists (used por persistencia externa si necesitás)
    public List<Socio> getSocios() { return socios; }
    public List<Juego> getJuegos() { return juegos; }
    public List<Reserva> getReservas() { return reservas; }
    public List<Prestamo> getPrestamos() { return prestamos; }
    public List<Torneo> getTorneos() { return torneos; }

    public void setSocios(List<Socio> s) { this.socios = s; }
    public void setJuegos(List<Juego> j) { this.juegos = j; }
    public void setReservas(List<Reserva> r) { this.reservas = r; }
    public void setPrestamos(List<Prestamo> p) { this.prestamos = p; }
    public void setTorneos(List<Torneo> t) { this.torneos = t; }

    @Override
    public String toString() {
        return "ClubGeek{" +
                "socios=" + socios.size() +
                ", juegos=" + juegos.size() +
                ", reservas=" + reservas.size() +
                ", prestamos=" + prestamos.size() +
                ", torneos=" + torneos.size() +
                ", administradores=" + administradores.size() +
                '}';
    }
    
}




