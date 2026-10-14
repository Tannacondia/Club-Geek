package clubgeek.model;

public abstract class Persona {
    protected String nombre;
    protected String dni;
    protected String email;
    protected String password; // nuevo

    public Persona(String nombre, String dni, String email, String password) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.password = password;
    }

    public String getNombre() { return nombre; }
    public String getDni() { return dni; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDni(String dni) { this.dni = dni; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return nombre + " <" + email + ">";
    }
}


