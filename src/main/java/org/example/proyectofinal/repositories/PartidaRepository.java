package org.example.proyectofinal.repositories;

import org.example.proyectofinal.HibernateUtil;
import org.example.proyectofinal.entities.Juego;
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

}
