package org.example.proyectofinal.repositories;

import org.example.proyectofinal.HibernateUtil;
import org.example.proyectofinal.entities.Juego;
import org.hibernate.Session;

import java.util.List;

public class JuegoRepository {

    private Session sesion;

    public JuegoRepository() {
        sesion = HibernateUtil.getSessionFactory().openSession();
    }

    public void closeSession() {
        sesion.close();
    }

    public void insert(Juego juego) {
        sesion.beginTransaction();
        sesion.persist(juego);
        sesion.getTransaction().commit();
    }

    public void update(Juego juego) {
        sesion.beginTransaction();
        sesion.merge(juego);
        sesion.getTransaction().commit();
    }

    public void delete(Juego juego) {
        sesion.beginTransaction();
        sesion.remove(juego);
        sesion.getTransaction().commit();
    }

    public Juego findById(Long id) {
        return sesion.find(Juego.class, id);
    }

    public List<Juego> findAll() {
        return sesion.createQuery("Select j From Juego j", Juego.class).getResultList();
    }

}
