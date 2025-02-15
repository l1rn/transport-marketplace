package com.example.transport_marketplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/routes")
public class RouteController {
    @Autowired
    private RouteService routeService;


    // Получение всех маршрутов
    @GetMapping
    public ResponseEntity<List<Route>> getRoutes() throws IOException{
        List<Route> routes = routeService.getRoutes();
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }
    // Добавление маршрута (POST)
    @PostMapping
    public ResponseEntity<Route> addRoute(@RequestBody Route route){
        Route savedRoute = routeService.addRoute(route);
        return new ResponseEntity<>(savedRoute, HttpStatus.CREATED);
    }

    // Поиск маршрутов по дате
    @GetMapping("/searchByDate")
    public ResponseEntity<List<Route>> searchByDate(@RequestParam String date){
        List<Route> filteredRoutes = routeService.getRoutes().stream()
                .filter(route -> route.getDate().equals(date))
                .collect(Collectors.toList());
        return new ResponseEntity<>(filteredRoutes, HttpStatus.OK);
    }

    // Поиск маршрута по транспорту
    @GetMapping("/searchByTransport")
    public ResponseEntity<List<Route>> searchByTransport(@RequestParam String transport){
        List<Route> filteredRoutes = routeService.getRoutes().stream()
                .filter(route -> route.getTransport().equals(transport))
                .collect(Collectors.toList());
        return new ResponseEntity<>(filteredRoutes, HttpStatus.OK);
    }

    // Удаление маршрута по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable int id){
        boolean deleted = routeService.deleteRoute(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // Обновление маршрута по ID
    @PutMapping("/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable int id, @RequestBody Route updatedRoute){
        Route route = routeService.updateRoute(id, updatedRoute);
        return route != null ? new ResponseEntity<>(route, HttpStatus.OK)
                            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
