package com.example.transport_marketplace;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.RouteMatcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteService {
    private static final String ROUTES_FILE_PATH = "src/main/resources/routes.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Route> routes = new ArrayList<>();

    public RouteService(){
        loadRoutes();
    }
    public void loadRoutes() {
        File file = new File(ROUTES_FILE_PATH);
        if (file.exists()) {
            try {
                routes = objectMapper.readValue(file, new TypeReference<List<Route>>() {});
            }
            catch (IOException e){
                System.err.println("Ошибка загрузки маршрутов: " + e.getMessage());
            }
        }
    }
    private void saveRoutes() {
        try {
            objectMapper.writeValue(new File(ROUTES_FILE_PATH), routes);
        } catch (IOException e) {
            System.err.println("Ошибка сохрания маршрутов: " + e.getMessage());
        }
    }

    public Route addRoute(Route route){
        int newID = routes.stream().mapToInt(Route::getId).max().orElse(0) + 1;
        route.setId(newID);
        routes.add(route);
        saveRoutes();
        return route;
    }

    public List<Route> getRoutes(){
        return routes;
    }
    public boolean deleteRoute(int id){
        boolean removed = routes.removeIf(route -> route.getId() == id);
        if (removed) {
            saveRoutes();
        }
        return removed;
    }
    public Route updateRoute(int id, Route updatedRoute){
        Optional<Route> existingRoute = routes.stream().filter(route -> route.getId() == id).findFirst();
        if(existingRoute.isPresent()){
            Route route = existingRoute.get();
            route.setRoute(updatedRoute.getRoute());
            route.setDate(updatedRoute.getDate());
            route.setTime(updatedRoute.getTime());
            route.setTransport(updatedRoute.getTransport());
            route.setPrice(updatedRoute.getPrice());
            saveRoutes();
            return route;
        }
        saveRoutes();
        return null;
    }
    public List<Route> searchRoutes(String route, String date, String transport){
        return routes.stream()
                .filter(r -> (route == null || r.getRoute().trim().equalsIgnoreCase(route.trim())) &&
                                    (date == null || r.getDate().trim().equals(date.trim())) &&
                                    (transport == null || r.getTransport().trim().equalsIgnoreCase(transport.trim())))
                .collect(Collectors.toList());
    }

}
