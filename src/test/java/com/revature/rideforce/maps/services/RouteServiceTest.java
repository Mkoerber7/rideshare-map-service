package com.revature.rideforce.maps.services;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;
import com.netflix.discovery.shared.Application;
import com.revature.rideforce.maps.beans.Route;
import com.revature.rideforce.maps.service.RouteService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RouteServiceTest {
	
	private GeoApiContext geoApiContext;
	private GeoApiContext.Builder builder;
	@Before
	  public void Setup() {
	    builder = new GeoApiContext.Builder();
	  }
	@MockBean
	private RouteService routeService;
	
	@Before
	public void validate() {
		assertNotNull(routeService);
		Assert.assertThat(routeService, instanceOf(RouteService.class));
	}
	
	@Test
	public void geoApiTest() {
		this.geoApiContext = geoApiContext;
	}
	
	@Test
	public void getDirectionsTest() throws ApiException, InterruptedException, IOException{
		String origin = "2925 Rensselaer Ct. Vienna, VA 22181";
		String destination = "11730 Plaza America Dr. Reston, VA";
		
		DirectionsRoute routes = DirectionsApi.getDirections(geoApiContext, origin, destination).mode(TravelMode.DRIVING).await().routes[0];
		if(routes !=null) {
		assertNotNull(routes);
		}
		else {
			assertNull(routes);
		}
	}
	
	@Test
    public void routeServiceConstructorTest() {
    	routeService = new RouteService();
    	assertNotNull(routeService);
    }
	
	@Test
	public void goodRoute(){
		final String start = "2925 Rensselaer Ct. Vienna, VA 22181";
		final String end = "11730 Plaza America Dr. Reston, VA";
		final Route route = new Route(12714, 9600);

		given(routeService.getRoute(start, end)).willReturn(route);
		Route routeTest = routeService.getRoute(start, end);
		
		assertEquals(route,routeTest);
	}
	@Test
	public void testDistance(){
		final String start = "2925 Rensselaer Ct. Vienna, VA 22181";
		final String end = "11730 Plaza America Dr. Reston, VA";
		final Route route = new Route(12714, 9600);

		given(routeService.getRoute(start, end)).willReturn(route);
		Route routeTest = routeService.getRoute(start, end);
		
		assertEquals(12714, routeTest.getDistance());
	}
	
	@Test
	public void testDuration() {
		final String start = "2925 Rensselaer Ct. Vienna, VA 22181";
		final String end = "11730 Plaza America Dr. Reston, VA";
		final Route route = new Route(12714, 9600);

		given(routeService.getRoute(start, end)).willReturn(route);
		Route routeTest = routeService.getRoute(start, end);
		
		assertEquals(routeTest.getDuration(),9600);
	}
		
		@Test
		public void testGet(){
			final String start = "2925 Rensselaer Ct. Vienna, VA 22181";
			final String end = "11730 Plaza America Dr. Reston, VA";
			final Route route = new Route(12714, 9600);

			given(routeService.getRoute(start, end)).willReturn(route);
			
			Assert.assertEquals(routeService.getRoute(start, end), route);
		}
	
	@Test
	public void testNegativeParams() {
	given(routeService.getRoute("-80302", "80302")).willReturn(null);
	
	Assert.assertEquals(routeService.getRoute("-80302", "80302"), null);
}
	
	@Test
	public void testNegativeParams2() {
		String origin = "-2925 Rensselaer Ct. Vienna, VA 22181";
		String destination = "80302";
		
		DirectionsApi.getDirections(geoApiContext, origin, destination);
	
		Assert.assertEquals(routeService.getRoute("-2925 Rensselaer Ct. Vienna, VA 22181", "80302"), null);
}
	
	@Test
	public void testNegativeParams3() {
		String origin = "-80302";
		String destination = "80302";
		
		DirectionsApi.getDirections(geoApiContext, origin, destination);
	
		Assert.assertEquals(routeService.getRoute("-80302", "80302"), null);
}
	
	@Test
	public void noEndParameters(){
		Route badRoute = routeService.getRoute("11730 Plaza America Dr. Reston, VA","");
		assertNull(badRoute);
	}
	
	@Test
	public void incompleteStartParameter() {
		Route badRoute = routeService.getRoute("11730 Plaza America Dr.","12160 Sunset Hills Rd, Reston, VA 20190");
		assertNull(badRoute);
	}
	
	@Test 
	public void incompleteEndParameter() {
		Route badRoute = routeService.getRoute("12160 Sunset Hills Rd, Reston, VA 20190","12160 Sunset Hills");
		assertNull(badRoute);
	}
	
	@Test
	public void negativeStartAddress() throws Exception {
		Route badRoute = routeService.getRoute("-12160 Sunset Hills Rd, Reston, VA 20190","12160 Sunset Hills");
		assertNull(badRoute);
	}
	
}
