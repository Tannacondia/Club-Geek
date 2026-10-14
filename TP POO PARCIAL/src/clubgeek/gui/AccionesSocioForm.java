package clubgeek.gui;

import clubgeek.model.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class AccionesSocioForm extends JFrame {
    public AccionesSocioForm(ClubGeek club, Socio socio, String tipo) {
        setTitle((tipo.equals("reserva") ? "Registrar Reserva" : "Registrar Préstamo") + " - Club Geek");
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JComboBox<String> comboJuego = new JComboBox<>();
        club.listarJuegos().forEach(j -> comboJuego.addItem(j.getNombre()));

        JTextField txtFechaInicio = new JTextField(LocalDate.now().toString());
        JTextField txtFechaFin = new JTextField(LocalDate.now().plusDays(7).toString());
        JButton btnGuardar = new JButton("Registrar");

        panel.add(new JLabel("Juego:"));
        panel.add(comboJuego);
        panel.add(new JLabel("Fecha inicio:"));
        panel.add(txtFechaInicio);
        if (tipo.equals("prestamo")) {
            panel.add(new JLabel("Fecha devolución:"));
            panel.add(txtFechaFin);
        }
        panel.add(new JLabel(""));
        panel.add(btnGuardar);

        add(panel);
        setVisible(true);

        btnGuardar.addActionListener(e -> {
            Juego juego = club.buscarJuegoPorNombre((String) comboJuego.getSelectedItem());
            if (juego == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un juego válido.");
                return;
            }

            try {
                if (tipo.equals("reserva")) {
                    Reserva r = new Reserva(socio, juego, LocalDate.parse(txtFechaInicio.getText()));
                    club.registrarReserva(r);
                    JOptionPane.showMessageDialog(this, "📅 Reserva registrada correctamente.");
                } else {
                    Prestamo p = new Prestamo(socio, juego,
                            LocalDate.parse(txtFechaInicio.getText()),
                            LocalDate.parse(txtFechaFin.getText()));
                    club.registrarPrestamo(p);
                    JOptionPane.showMessageDialog(this, "📘 Préstamo registrado correctamente.");
                }
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en fechas: " + ex.getMessage());
            }
        });
    }
}

