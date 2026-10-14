package clubgeek.gui;

import clubgeek.model.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class CrearTorneoForm extends JFrame {
    public CrearTorneoForm(ClubGeek club, Administrador admin) {
        setTitle("Crear Torneo - Club Geek");
        setSize(400, 350);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtNombre = new JTextField();
        JTextField txtPremio = new JTextField();
        JTextField txtFecha = new JTextField("2025-12-01");
        JComboBox<String> comboJuego = new JComboBox<>();

        club.listarJuegos().forEach(j -> comboJuego.addItem(j.getNombre()));

        JButton btnCrear = new JButton("Crear");

        panel.add(new JLabel("Nombre del torneo:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Fecha (AAAA-MM-DD):"));
        panel.add(txtFecha);
        panel.add(new JLabel("Premio:"));
        panel.add(txtPremio);
        panel.add(new JLabel("Juego asociado:"));
        panel.add(comboJuego);
        panel.add(new JLabel(""));
        panel.add(btnCrear);

        add(panel);
        setVisible(true);

        btnCrear.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String premio = txtPremio.getText().trim();
                LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());
                Juego juego = club.buscarJuegoPorNombre((String) comboJuego.getSelectedItem());

                if (nombre.isEmpty() || juego == null) {
                    JOptionPane.showMessageDialog(this, "Debe completar todos los campos.");
                    return;
                }

                Torneo nuevo = new Torneo(System.currentTimeMillis(), nombre, fecha, premio, juego, admin);
                club.agregarTorneo(nuevo);
                JOptionPane.showMessageDialog(this, "🏆 Torneo creado correctamente.");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear el torneo: " + ex.getMessage());
            }
        });
    }
}

