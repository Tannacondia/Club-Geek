package clubgeek.gui;

import clubgeek.model.*;
import javax.swing.*;
import java.awt.*;

public class GestionJuegosForm extends JFrame {

    private JTextField txtNombre;
    private JCheckBox chkDisponible;
    private JComboBox<String> comboTipo;
    private JTextField txtExtra;
    private JButton btnGuardar;
    private JLabel lblDatoAdicional;

    public GestionJuegosForm(ClubGeek club) {
        setTitle("Agregar Juego - Club Geek");
        setSize(400, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNombre = new JLabel("Nombre del juego:");
        txtNombre = new JTextField();

        JLabel lblTipo = new JLabel("Tipo de juego:");
        comboTipo = new JComboBox<>(new String[]{"JuegoDeRol", "JuegoDeCartas", "JuegoDeEstrategia"});

        lblDatoAdicional = new JLabel("Dato adicional:");
        txtExtra = new JTextField();

        chkDisponible = new JCheckBox("Disponible", true);
        btnGuardar = new JButton("Guardar");

        // Estilo de botón
        btnGuardar.setBackground(new Color(66, 133, 244));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setFocusPainted(false);

        // Layout de componentes
        c.gridx = 0; c.gridy = 0;
        panel.add(lblNombre, c);
        c.gridx = 1;
        panel.add(txtNombre, c);

        c.gridx = 0; c.gridy++;
        panel.add(lblTipo, c);
        c.gridx = 1;
        panel.add(comboTipo, c);

        c.gridx = 0; c.gridy++;
        panel.add(lblDatoAdicional, c);
        c.gridx = 1;
        panel.add(txtExtra, c);

        c.gridx = 0; c.gridy++;
        panel.add(new JLabel("Disponibilidad:"), c);
        c.gridx = 1;
        panel.add(chkDisponible, c);

        c.gridx = 0; c.gridy++; c.gridwidth = 2;
        panel.add(btnGuardar, c);

        // Evento: cambiar etiqueta según tipo de juego
        comboTipo.addActionListener(this::comboTipoActionPerformed);

        // Evento guardar
        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String tipo = (String) comboTipo.getSelectedItem();
            String extra = txtExtra.getText().trim();
            boolean disponible = chkDisponible.isSelected();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un nombre.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Juego nuevo = switch (tipo) {
                case "JuegoDeRol" -> new JuegoDeRol(nombre, disponible, Integer.parseInt(extra.isEmpty() ? "1" : extra));
                case "JuegoDeCartas" -> new JuegoDeCartas(nombre, disponible, Integer.parseInt(extra.isEmpty() ? "52" : extra));
                case "JuegoDeEstrategia" -> new JuegoDeEstrategia(nombre, disponible, extra.isEmpty() ? "Media" : extra);
                default -> null;
            };

            if (nuevo != null) {
                club.agregarJuego(nuevo);
                JOptionPane.showMessageDialog(this, "✅ Juego agregado correctamente.");
                dispose();
            }
        });

        setVisible(true);
    }

    //  Método que cambia el texto según el tipo de juego
    private void comboTipoActionPerformed(java.awt.event.ActionEvent evt) {
        String tipo = (String) comboTipo.getSelectedItem();
        switch (tipo) {
            case "JuegoDeRol" -> lblDatoAdicional.setText("Cantidad de jugadores:");
            case "JuegoDeCartas" -> lblDatoAdicional.setText("Cantidad de cartas:");
            case "JuegoDeEstrategia" -> lblDatoAdicional.setText("Dificultad:");
            default -> lblDatoAdicional.setText("Dato adicional:");
        }
    }
}



