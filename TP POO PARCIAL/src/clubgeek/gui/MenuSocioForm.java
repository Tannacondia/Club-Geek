package clubgeek.gui;

import clubgeek.model.*;
import javax.swing.*;
import java.awt.*;

public class MenuSocioForm extends JFrame {
    private JPanel panelMain;
    private JLabel lblBienvenida;
    private JButton btnReservar;
    private JButton btnPrestar;
    private JButton btnResumen;
    private JButton btnSalir;

    public MenuSocioForm(ClubGeek club, Socio socio) {
        setTitle("Panel de Socio - Club Geek");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);
        setVisible(true);

        panelMain = new JPanel(new GridLayout(5, 1, 10, 10));
        panelMain.setBackground(new Color(245, 245, 245));
        setContentPane(panelMain);

        Font fuente = new Font("Segoe UI", Font.BOLD, 15);
        Color azul = new Color(66, 133, 244);

        lblBienvenida = new JLabel("👋 Hola, " + socio.getNombre(), SwingConstants.CENTER);
        btnReservar = new JButton("- Reservar Juego");
        btnPrestar = new JButton(" - Registrar Préstamo");
        btnResumen = new JButton("- Ver Resumen");
        btnSalir = new JButton("⎗ Cerrar Sesión");

        for (JButton b : new JButton[]{btnReservar, btnPrestar, btnResumen, btnSalir}) {
            b.setBackground(azul);
            b.setForeground(Color.WHITE);
            b.setFont(fuente);
            b.setFocusPainted(false);
            panelMain.add(b);
        }

        panelMain.add(lblBienvenida, 0);

        btnSalir.addActionListener(e -> {
            new LoginForm(club);
            dispose();
        });

        btnResumen.addActionListener(e -> {
            String resumen = "📅 Reservas activas: " + socio.getReservas().size() +
                    "\n📘 Préstamos activos: " + socio.getPrestamos().size();
            JOptionPane.showMessageDialog(this, resumen);
        });

        btnReservar.addActionListener(e -> new AccionesSocioForm(club, socio, "reserva"));
        btnPrestar.addActionListener(e -> new AccionesSocioForm(club, socio, "prestamo"));
    }
}


