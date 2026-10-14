package clubgeek.model;

public class JuegoDeRol extends Juego {
    private int cantidadJugadores;

    public JuegoDeRol(String nombre, boolean disponible, int cantidadJugadores) {
        super(nombre, disponible);
        this.cantidadJugadores = cantidadJugadores;
    }

    public int getCantidadJugadores() { return cantidadJugadores; }

    @Override
    public void mostrarTipo() {
        System.out.println(nombre + " - Juego de Rol para " + cantidadJugadores + " jugadores.");
    }

    @Override
    public String toString() {
        return nombre + " (Rol, " + cantidadJugadores + " players) " + (disponible ? "[OK]" : "[NO]");
    }
}


