package com.wipro.simplyfly.restcontroller;
import java.util.List;
	import java.util.stream.Collectors;

	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

	import com.wipro.simplyfly.dto.RouteDTO;
	import com.wipro.simplyfly.entity.Route;
	import com.wipro.simplyfly.repository.RouteRepository;
	@PreAuthorize("hasAuthority('OWNER')")
	@RestController
	@RequestMapping("/api/routes")
	@CrossOrigin(origins = "*")
	public class RouteController {

	    @Autowired
	    private RouteRepository routeRepository;

	    //CREATE ROUTE

	    @PostMapping
	    public RouteDTO createRoute(@RequestBody RouteDTO routeDTO) {

	        Route route = new Route();
	        route.setSource(routeDTO.getSource());
	        route.setDestination(routeDTO.getDestination());
	        route.setDistance(routeDTO.getDistance());
	        route.setEstimatedDuration(routeDTO.getEstimatedDuration());

	        Route savedRoute = routeRepository.save(route);

	        routeDTO.setId(savedRoute.getId());
	        return routeDTO;
	    }

	    //Route
	    @GetMapping("/api/owner/routes")
	    public List<RouteDTO> getRoutes(){
	        return routeRepository.findAll()
	                .stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }
	    @GetMapping
	    public List<RouteDTO> getAllRoutes() {

	        return routeRepository.findAll()
	                .stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }

	    //GET ROUTE BY ID

	    @GetMapping("/{id}")
	    public RouteDTO getRouteById(@PathVariable int id) {

	        Route route = routeRepository.findById(id).orElseThrow();

	        return convertToDTO(route);
	    }

	    //SEARCH ROUTE

	    @GetMapping("/search")
	    public List<RouteDTO> searchRoute(
	            @RequestParam String source,
	            @RequestParam String destination) {

	        return routeRepository
	                .findBySourceAndDestination(source, destination)
	                .stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }

	    //UPDATE ROUTE

	    @PutMapping("/{id}")
	    public RouteDTO updateRoute(@PathVariable int id,
	                                @RequestBody RouteDTO routeDTO) {

	        Route route = routeRepository.findById(id).orElseThrow();

	        route.setSource(routeDTO.getSource());
	        route.setDestination(routeDTO.getDestination());
	        route.setDistance(routeDTO.getDistance());
	        route.setEstimatedDuration(routeDTO.getEstimatedDuration());

	        routeRepository.save(route);

	        return convertToDTO(route);
	    }

	    //DELETE ROUTE
	    @DeleteMapping("/{id}")
	    public void deleteRoute(@PathVariable int id) {

	        if (!routeRepository.existsById(id)) {
	            throw new RuntimeException("Route not found with id: " + id);
	        }

	        routeRepository.deleteById(id);
	    }

	    //CONVERT METHOD

	    private RouteDTO convertToDTO(Route route) {

	        return new RouteDTO(
	                route.getId(),
	                route.getSource(),
	                route.getDestination(),
	                route.getDistance(),
	                route.getEstimatedDuration()
	        );
	    }
	
}
