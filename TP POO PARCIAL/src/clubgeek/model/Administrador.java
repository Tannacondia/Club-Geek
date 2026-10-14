package clubgeek.model;

import java.util.ArrayList;
import java.util.List;

public class Administrador extends Persona {
    private String cargo;
    private double salario;
    private List<Torneo> torneos;

    public Administrador(String nombre, String dni, String email, String password, String cargo, double salario) {
        super(nombre, dni, email, password);
        this.cargo = cargo;
        this.salario = salario;
        this.torneos = new ArrayList<>();
    }

    public void agregarTorneo(Torneo t) { torneos.add(t); }
    public List<Torneo> getTorneos() { return torneos; }

    public String getCargo() { return cargo; }
    public double getSalario() { return salario; }

    @Override
    public String toString() {
        return "Administrador " + nombre + " <" + email + ">";
    }
}



