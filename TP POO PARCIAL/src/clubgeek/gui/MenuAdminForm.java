package clubgeek.gui;

import clubgeek.model.*;
import javax.swing.*;
import java.awt.*;

public class MenuAdminForm extends JFrame {
    private JPanel panelMain;
    private JLabel lblBienvenida;
    private JButton btnAgregarJuego;
    private JButton btnCrearTorneo;
    private JButton btnVerJuegos;
    private JButton btnVerSocios;
    private JButton btnVerTorneos;
    private JButton btnSalir;

    public MenuAdminForm(ClubGeek club, Administrador admin) {
        setTitle("Panel de Administrador - Club Geek");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 480);
        setLocationRelativeTo(null);
        setVisible(true);

        panelMain = new JPanel(new GridLayout(7, 1, 10, 10));
        panelMain.setBackground(new Color(245, 245, 245));
        setContentPane(panelMain);

        Font fuente = new Font("Segoe UI", Font.BOLD, 15);
        Color azul = new Color(66, 133, 244);

        lblBienvenida = new JLabel("👋 Bienvenido, " + admin.getNombre(), SwingConstants.CENTER);
        btnAgregarJuego = new JButton(" - Agregar Juego");
        btnCrearTorneo = new JButton("- Crear Torneo");
        btnVerJuegos = new JButton(" - Ver Juegos");
        btnVerSocios = new JButton("- Ver Socios");
        btnVerTorneos = new JButton(" - Ver Torneos");
        btnSalir = new JButton(" ⎗ Cerrar Sesión");

        for (JButton b : new JButton[]{btnAgregarJuego, btnCrearTorneo, btnVerJuegos, btnVerSocios, btnVerTorneos, btnSalir}) {
            b.setBackground(azul);
            b.setForeground(Color.WHITE);
            b.setFont(fuente);
            b.setFocusPainted(false);
            panelMain.add(b);
        }

        panelMain.add(lblBienvenida, 0);

        // Acciones
        btnSalir.addActionListener(e -> {
            new LoginForm(club);
            dispose();
        });

        btnAgregarJuego.addActionListener(e -> new GestionJuegosForm(club));
        btnCrearTorneo.addActionListener(e -> new CrearTorneoForm(club, admin));

        btnVerJuegos.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("🎮 Juegos disponibles:\n\n");
            club.listarJuegos().forEach(j -> sb.append(j).append("\n"));
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        btnVerSocios.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("👥 Socios registrados:\n\n");
            club.listarSocios().forEach(s -> sb.append(s).append("\n"));
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        btnVerTorneos.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("🏆 Torneos:\n\n");
            club.listarTorneos().forEach(t -> sb.append(t).append("\n"));
            JOptionPane.showMessageDialog(this, sb.toString());
        });
    }
}


