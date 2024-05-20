package org.example.proyectofinal.repositories;

import org.example.proyectofinal.HibernateUtil;
import org.example.proyectofinal.entities.Juego;
import org.example.proyectofinal.entities.Jugador;
import org.hibernate.Session;

import java.util.List;

public class JugadorRepository {

    private Session sesion;

    public JugadorRepository() {
        sesion = HibernateUtil.getSessionFactory().openSession();
    }

    public void closeSession() {
        sesion.close();
    }

    public void insert(Jugador jugador) {
        sesion.beginTransaction();
        sesion.persist(jugador);
        sesion.getTransaction().commit();
    }

    public void update(Jugador jugador) {
        sesion.beginTransaction();
        sesion.merge(jugador);
        sesion.getTransaction().commit();
    }

    public void delete(Jugador jugador) {
        sesion.beginTransaction();
        sesion.remove(jugador);
        sesion.getTransaction().commit();
    }

    public Jugador findById(Long id) {
        return sesion.find(Jugador.class, id);
    }

    public List<Jugador> findAll() {
        return sesion.createQuery("Select j From Jugador j", Jugador.class).getResultList();
    }

}
