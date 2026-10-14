package clubgeek.model;

import java.util.ArrayList;
import java.util.List;

public class Socio extends Persona {
    private String membresia;
    private List<Reserva> reservas;
    private List<Prestamo> prestamos;

    public Socio(String nombre, String dni, String email, String password, String membresia) {
        super(nombre, dni, email, password);
        this.membresia = membresia;
        this.reservas = new ArrayList<>();
        this.prestamos = new ArrayList<>();
    }

    public String getMembresia() { return membresia; }
    public void setMembresia(String m) { this.membresia = m; }

    public void agregarReserva(Reserva r) { reservas.add(r); }
    public void agregarPrestamo(Prestamo p) { prestamos.add(p); }

    public List<Reserva> getReservas() { return reservas; }
    public List<Prestamo> getPrestamos() { return prestamos; }

    @Override
    public String toString() {
        return nombre + " (" + email + ") - " + membresia;
    }
}


