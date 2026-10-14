package clubgeek.model;

public class JuegoDeCartas extends Juego {
    private int cantidadCartas;

    public JuegoDeCartas(String nombre, boolean disponible, int cantidadCartas) {
        super(nombre, disponible);
        this.cantidadCartas = cantidadCartas;
    }

    public int getCantidadCartas() { return cantidadCartas; }

    @Override
    public void mostrarTipo() {
        System.out.println(nombre + " - Juego de Cartas con " + cantidadCartas + " cartas.");
    }

    @Override
    public String toString() {
        return nombre + " (Cartas, " + cantidadCartas + " cards) " + (disponible ? "[OK]" : "[NO]");
    }
}


