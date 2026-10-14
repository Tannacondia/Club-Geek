package clubgeek.gui;

import clubgeek.model.*;
import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    private JPanel panelMain;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegistrar;
    private JComboBox<String> comboTipoUsuario;
    private JLabel lblStatus;
    private JLabel lblTitulo;

    private final ClubGeek club;

    public LoginForm(ClubGeek club) {
        this.club = club;
        setTitle("Club Geek - Inicio de Sesión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 420);
        setLocationRelativeTo(null);
        setVisible(true);

        // Panel principal
        panelMain = new JPanel();
        panelMain.setLayout(new GridBagLayout());
        panelMain.setBackground(new Color(245, 245, 245));
        setContentPane(panelMain);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        lblTitulo = new JLabel("Ω Bienvenido a Club Geek Ω", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));

        txtEmail = new JTextField();
        txtPassword = new JPasswordField();
        comboTipoUsuario = new JComboBox<>(new String[]{"Administrador", "Socio"});
        btnLogin = new JButton("Iniciar sesión");
        btnRegistrar = new JButton("Registrar socio");
        lblStatus = new JLabel(" ", SwingConstants.CENTER);

        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 14);
        btnLogin.setBackground(new Color(66, 133, 244));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(fuenteBoton);
        btnLogin.setFocusPainted(false);

        btnRegistrar.setBackground(new Color(52, 168, 83));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(fuenteBoton);
        btnRegistrar.setFocusPainted(false);

        // Componentes al panel
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        panelMain.add(lblTitulo, c);

        c.gridy++; panelMain.add(new JLabel("Email:"), c);
        c.gridy++; panelMain.add(txtEmail, c);
        c.gridy++; panelMain.add(new JLabel("Contraseña:"), c);
        c.gridy++; panelMain.add(txtPassword, c);
        c.gridy++; panelMain.add(comboTipoUsuario, c);

        c.gridy++; c.gridwidth = 1;
        c.gridx = 0; panelMain.add(btnLogin, c);
        c.gridx = 1; panelMain.add(btnRegistrar, c);

        c.gridx = 0; c.gridy++; c.gridwidth = 2;
        panelMain.add(lblStatus, c);

        // Eventos
        btnLogin.addActionListener(e -> login());
        btnRegistrar.addActionListener(e -> registrarSocio());

        setVisible(true);
    }

    private void login() {
        String email = txtEmail.getText().trim();
        String pass = new String(txtPassword.getPassword());
        String tipo = (String) comboTipoUsuario.getSelectedItem();

        if (email.isEmpty() || pass.isEmpty()) {
            lblStatus.setText("Por favor complete todos los campos.");
            lblStatus.setForeground(Color.RED);
            return;
        }

        if (tipo.equals("Administrador")) {
            Administrador admin = club.loginAdmin(email, pass);
            if (admin != null) {
                lblStatus.setForeground(new Color(0, 128, 0));
                lblStatus.setText("Inicio de sesión correcto (Admin)");
                JOptionPane.showMessageDialog(this, "Bienvenido, " + admin.getNombre());
                new MenuAdminForm(club, admin);
                dispose();
            } else {
                lblStatus.setForeground(Color.RED);
                lblStatus.setText("Credenciales inválidas.");
            }
        } else {
            Socio socio = club.loginSocio(email, pass);
            if (socio != null) {
                lblStatus.setForeground(new Color(0, 128, 0));
                lblStatus.setText("Inicio de sesión correcto (Socio)");
                JOptionPane.showMessageDialog(this, "Bienvenido, " + socio.getNombre());
                new MenuSocioForm(club, socio);
                dispose();
            } else {
                lblStatus.setForeground(Color.RED);
                lblStatus.setText("Credenciales inválidas.");
            }
        }
    }

    private void registrarSocio() {
        JTextField txtNombre = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtEmailNuevo = new JTextField();
        JPasswordField txtPassNuevo = new JPasswordField();
        JComboBox<String> comboMembresia = new JComboBox<>(new String[]{"Básica", "Premium"});

        int r = JOptionPane.showConfirmDialog(this, new Object[]{
                "Nombre:", txtNombre,
                "DNI:", txtDni,
                "Email:", txtEmailNuevo,
                "Contraseña:", txtPassNuevo,
                "Membresía:", comboMembresia
        }, "Registro de nuevo socio", JOptionPane.OK_CANCEL_OPTION);

        if (r == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String dni = txtDni.getText().trim();
            String email = txtEmailNuevo.getText().trim();
            String pass = new String(txtPassNuevo.getPassword());
            String membresia = (String) comboMembresia.getSelectedItem();

            if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠ Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Evitar duplicado por email
            if (club.loginSocio(email, pass) != null) {
                JOptionPane.showMessageDialog(this, "✘ Este email ya está en uso.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Socio nuevo = club.registrarSocio(nombre, dni, email, pass, membresia);
            if (nuevo != null) {
                JOptionPane.showMessageDialog(this, "✔ Socio registrado correctamente. Ya puede iniciar sesión.");
            } else {
                JOptionPane.showMessageDialog(this, "✘ No se pudo registrar el socio.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        ClubGeek club = new ClubGeek();
        club.cargarDatos();
        new LoginForm(club);
    }
}



