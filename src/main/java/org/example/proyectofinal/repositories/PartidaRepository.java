package org.example.proyectofinal.repositories;

import org.example.proyectofinal.HibernateUtil;
import org.example.proyectofinal.entities.Juego;
import org.example.proyectofinal.entities.Jugador;
import org.example.proyectofinal.entities.Partida;
import org.hibernate.Session;

import java.util.List;

public class PartidaRepository {

    private Session sesion;

    public PartidaRepository() {
        sesion = HibernateUtil.getSessionFactory().openSession();
    }

    public void closeSession() {
        sesion.close();
    }

    public void insert(Partida partida) {
        sesion.beginTransaction();
        sesion.persist(partida);
        sesion.getTransaction().commit();
    }

    public void update(Partida partida) {
        sesion.beginTransaction();
        sesion.merge(partida);
        sesion.getTransaction().commit();
    }

    public void delete(Partida partida) {
        sesion.beginTransaction();
        sesion.remove(partida);
        sesion.getTransaction().commit();
    }

    public Partida findById(Long id) {
        return sesion.find(Partida.class, id);
    }

    public List<Partida> findAll() {
        return sesion.createQuery("Select p From Partida p", Partida.class).getResultList();
    }

    /**
     * Partidas que ha jugado un jugador
     * ESTE MÉTODO CON MUCHOS DATOS ES MUY LENTO -----------------------------
     * @param idJugador
     * @return
     */
    public Long totalPartidasJugadas(Long idJugador) {

        //ESTO VA LENTO PORQUE p.getJugadores() SE TRANSFORMA EN UN JOIN QUE SE
        //EJECUTA TANTAS VECES COMO PARTIDAS HAY
        /*
        List<Partida> partidas = findAll();
        return partidas.stream()
                .filter(p -> p.getJugadores().stream()
                        .anyMatch(j -> j.getId() == idJugador))
                .distinct()
                .count();
        */

        List<Partida> partidas = sesion.createQuery(
                "SELECT p FROM Partida p JOIN FETCH p.jugadores",
                Partida.class
        ).getResultList();

       return partidas.stream()
                .filter(p -> p.getJugadores().stream()
                        .anyMatch(j -> j.getId() == idJugador))
                .distinct()
                .count();

        /*
        return sesion.createQuery("SELECT COUNT(p) FROM Partida p JOIN p.jugadores j WHERE j.id = :jugadorId", Partida.class)
                .setParameter("jugadorId", idJugador )
                .getResultCount();
        */
    }

    /**
     * Cuántas partidas hay de un juego determinado
     * @param idJuego
     * @return
     */
    public Long totalPartidasDeUnJuego(Long idJuego) {
        List<Partida> partidas = findAll();
        return partidas.stream()
                .filter(p -> p.getJuego().getId() == idJuego)
                .count();
    }

}
