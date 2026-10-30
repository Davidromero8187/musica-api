package com.musica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Long> {

    // Consulta personalizada exigida por la actividad (buscar por género)
    List<Cancion> findByGeneroIgnoreCase(String genero);
}