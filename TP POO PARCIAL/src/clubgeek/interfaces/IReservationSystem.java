package clubgeek.interfaces;

import clubgeek.model.Reserva;
import clubgeek.model.Prestamo;

public interface IReservationSystem {
    void registrarReserva(Reserva reserva);
    void registrarPrestamo(Prestamo prestamo);
}


