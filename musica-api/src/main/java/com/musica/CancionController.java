package com.musica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/musica-api/canciones")
public class CancionController {

    @Autowired
    private CancionRepository cancionRepository;

    // 1. Crear una nueva canción -> 201 Created
    @PostMapping
    public ResponseEntity<Cancion> crearCancion(@RequestBody Cancion cancion) {
        Cancion nuevaCancion = cancionRepository.save(cancion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCancion);
    }

    // 2. Consultar todas las canciones -> 200 OK
    @GetMapping
    public ResponseEntity<List<Cancion>> listarCanciones() {
        List<Cancion> canciones = cancionRepository.findAll();
        return ResponseEntity.ok(canciones);
    }

    // 3. Consultar una canción por ID -> 200 OK o 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<Cancion> buscarPorId(@PathVariable Long id) {
        Optional<Cancion> cancion = cancionRepository.findById(id);
        if (cancion.isPresent()) {
            return ResponseEntity.ok(cancion.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Consulta personalizada -> GET /musica-api/canciones/buscar?genero=Rock
    @GetMapping("/buscar")
    public ResponseEntity<List<Cancion>> buscarPorGenero(@RequestParam String genero) {
        List<Cancion> resultados = cancionRepository.findByGeneroIgnoreCase(genero);
        if (resultados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(resultados);
    }

    // 4. Actualizar una canción existente -> 200 OK o 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<Cancion> actualizarCancion(@PathVariable Long id, @RequestBody Cancion detalles) {
        Optional<Cancion> cancionOpt = cancionRepository.findById(id);
        
        if (cancionOpt.isPresent()) {
            Cancion cancion = cancionOpt.get();
            cancion.setTitulo(detalles.getTitulo());
            cancion.setArtista(detalles.getArtista());
            cancion.setGenero(detalles.getGenero());
            cancion.setAnio(detalles.getAnio());

            Cancion actualizada = cancionRepository.save(cancion);
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 5. Eliminar una canción -> 204 No Content o 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCancion(@PathVariable Long id) {
        if (cancionRepository.existsById(id)) {
            cancionRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}