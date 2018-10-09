package com.revature.rideforce.maps.service;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;
import com.revature.rideforce.maps.beans.Route;

/**
 * The route service
 * @author Revature Java batch
 * @Component
 */
@Component
public class RouteService {
	private static final Logger log = LoggerFactory.getLogger(RouteService.class);

  	/**
  	 * Injecting the GeoApiContext
  	 */
	@Autowired
	private GeoApiContext geoApiContext;
	public GeoApiContext getGeoApiContext() {
	/**
	 * get the route
	 * @param origin
	 * @param destination
	 * @return Route
	 * @throws Exception 
	 */
	public Route getRoute(String origin, String destination) throws Exception {
		try {
			DirectionsRoute route = DirectionsApi.getDirections(geoApiContext, origin, destination)
					.mode(TravelMode.DRIVING).await().routes[0];
			if(StringUtils.isNumeric(origin)) {
				if(Integer.parseInt(origin)<0) {
					throw new Exception("Can't input negative numbers");
				}
			}
			long distance = 0;
			long duration = 0;

			for (DirectionsLeg leg : route.legs) {
				distance += leg.distance.inMeters;
				duration += leg.duration.inSeconds;
			}
			return new Route(distance, duration);
		} catch (ApiException | InterruptedException | IOException e) {
			log.error("Unexpected exception when fetching route.", e);
			Thread.currentThread().interrupt();
			return null;
		}
	}
}
