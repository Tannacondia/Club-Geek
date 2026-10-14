package clubgeek.model;

public class JuegoDeEstrategia extends Juego {
    private String nivelDificultad;

    public JuegoDeEstrategia(String nombre, boolean disponible, String nivelDificultad) {
        super(nombre, disponible);
        this.nivelDificultad = nivelDificultad;
    }

    public String getNivelDificultad() { return nivelDificultad; }

    @Override
    public void mostrarTipo() {
        System.out.println(nombre + " - Juego de Estrategia (dificultad: " + nivelDificultad + ").");
    }

    @Override
    public String toString() {
        return nombre + " (Estrategia, " + nivelDificultad + ") " + (disponible ? "[OK]" : "[NO]");
    }
}


