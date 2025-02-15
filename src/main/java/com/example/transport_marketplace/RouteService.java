package com.example.transport_marketplace;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.RouteMatcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    private static final String ROUTES_FILE_PATH = "src/main/resources/routes.json";
    private List<Route> routes = new ArrayList<>();

    public RouteService(){
        try {
            loadRoutes();
        } catch (IOException e) {
            System.out.println("Ошибка загрузки маршрутов: " + e.getMessage());
        }
    }
    public void loadRoutes() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(ROUTES_FILE_PATH);
        if (file.exists()) {
            routes = objectMapper.readValue(file,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Route.class));
        }
    }
    private void saveRoutes() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ROUTES_FILE_PATH), routes);
    }

    public Route addRoute(Route route){
        route.setId(routes.size() + 1);
        routes.add(route);
        try {
            saveRoutes();
        } catch (IOException e) {
            System.out.println("Ошибка сохранения маршрутов: " + e.getMessage());
        }
        return route;
    }

    public List<Route> getRoutes(){
        return routes;
    }
    public boolean deleteRoute(int id){
        return routes.removeIf(route -> route.getId() == id);
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
            return route;
        }
        return null;
    }
}
