package clubgeek.model;

import java.time.LocalDate;

public class Reserva {
    private Socio socio;
    private Juego juego;
    private LocalDate fecha;

    public Reserva(Socio socio, Juego juego, LocalDate fecha) {
        this.socio = socio;
        this.juego = juego;
        this.fecha = fecha;
    }

    public Socio getSocio() { return socio; }
    public Juego getJuego() { return juego; }
    public LocalDate getFecha() { return fecha; }

    @Override
    public String toString() {
        return socio.getNombre() + " reserved " + juego.getNombre() + " on " + fecha;
    }
}


