package clubgeek.model;

import java.time.LocalDate;

public class Torneo {
    private Long id;
    private String nombre;
    private LocalDate fecha;
    private String premio;
    private Juego juego;
    private Administrador administrador;

    public Torneo(Long id, String nombre, LocalDate fecha, String premio, Juego juego, Administrador administrador) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.premio = premio;
        this.juego = juego;
        this.administrador = administrador;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public LocalDate getFecha() { return fecha; }
    public String getPremio() { return premio; }
    public Juego getJuego() { return juego; }
    public Administrador getAdministrador() { return administrador; }

    public void setJuego(Juego j) { this.juego = j; }
    public void setAdministrador(Administrador a) { this.administrador = a; }

    @Override
    public String toString() {
        return "Torneo{" + id + " - " + nombre + " | " + fecha + " | juego=" +
                (juego != null ? juego.getNombre() : "N/A") + " | admin=" +
                (administrador != null ? administrador.getNombre() : "N/A") + "}";
    }
}



