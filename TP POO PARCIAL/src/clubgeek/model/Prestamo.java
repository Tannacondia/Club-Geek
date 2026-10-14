package clubgeek.model;

import java.time.LocalDate;

public class Prestamo {
    private Socio socio;
    private Juego juego;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Prestamo(Socio socio, Juego juego, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.socio = socio;
        this.juego = juego;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Socio getSocio() { return socio; }
    public Juego getJuego() { return juego; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }

    @Override
    public String toString() {
        return socio.getNombre() + " loaned " + juego.getNombre() + " from " + fechaPrestamo + " to " + fechaDevolucion;
    }
}

