package clubgeek.model;

public abstract class Juego {
    protected String nombre;
    protected boolean disponible;

    public Juego(String nombre, boolean disponible) {
        this.nombre = nombre;
        this.disponible = disponible;
    }

    public String getNombre() { return nombre; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean d) { this.disponible = d; }

    public abstract void mostrarTipo();
}


