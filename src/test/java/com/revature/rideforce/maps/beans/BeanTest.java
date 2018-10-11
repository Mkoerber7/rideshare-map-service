package com.revature.rideforce.maps.beans;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
public class BeanTest{
	private static ValidatorFactory validatorFactory;
    private static Validator validator;
    
    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @AfterClass
    public static void close() {
        validatorFactory.close();
    }
    
    @Test
    public void validateRouteTest() {
    	Route route = new Route(12714, 9600);
        Set<ConstraintViolation<Route>> violations
            = validator.validate(route);

        assertTrue(violations.isEmpty());
    }
    
    @Test
    public void violateConstraintTest() {
    	Route route = new Route(16000, 9600);
     
        Set<ConstraintViolation<Route>> violations
            = validator.validate(route);
     
        assertEquals(violations.size(), 1);
     
        ConstraintViolation<Route> violation
            = violations.iterator().next();
        assertEquals("must be less than or equal to 15000",
                     violation.getMessage());
        assertEquals("distance", violation.getPropertyPath().toString());
        assertEquals(16000L, violation.getInvalidValue());
    }
    
    @Test
    public void validateCachedLocationTest() {
    	CachedLocation location = new CachedLocation("2925 Rensselaer Ct. Vienna, VA 22181", 38.95, -77.35);
        Set<ConstraintViolation<CachedLocation>> violations
            = validator.validate(location);

        assertTrue(violations.isEmpty());
    }
    
    @Test
    public void violateConstraintTest2() {
    	CachedLocation location = new CachedLocation("2925 Rensselaer Rensselaer Rensselaer Rensselaer Rensselaer Rensselaer Rensselaer Rensselaer Rensselaer Rensselaer Ct. Vienna, VA 22181", 38.95, -77.35);
     
        Set<ConstraintViolation<CachedLocation>> violations
            = validator.validate(location);
     
        assertEquals(1, violations.size());
     
        ConstraintViolation<CachedLocation> violation
            = violations.iterator().next();
        assertEquals("size must be between 0 and 85",
                     violation.getMessage());
        assertEquals("address", violation.getPropertyPath().toString());
        assertEquals("2925 Rensselaer Rensselaer Rensselaer Rensselaer Rensselaer Rensselaer Rensselaer Rensselaer Rensselaer Rensselaer Ct. Vienna, VA 22181", violation.getInvalidValue());
    }
    
    @Test
    public void routeEqualsReflexive(){	
    	Route route = new Route(12714, 9600);
        assertEquals(route, route);
    }
    
    @Test
    public void routeEqualsSymmetric(){	
    	Route route = new Route(12714, 9600);
    	Route route1 = new Route(12714, 9600);
        assertEquals(route, route1);
        assertEquals(route1, route);
    }
    
    @Test
    public void routeEqualsTransitive() {
    	Route route = new Route(12714, 9600);
    	Route route1 = new Route(12714, 9600);
    	Route route2 = new Route(12714, 9600);
        assertThat(route.equals(route1));
        assertThat(route1.equals(route2));
        assertThat(route.equals(route2));
    }
    
    @Test
    public void hashCodeRouteConsistencyTest()
    {
    	Route route = new Route(12714, 9600);
       
        assertEquals(route.hashCode(), route.hashCode());
        
    }
    
    @Test
    public void hashCodeRouteTest()
    {
    	Route route = new Route(12714, 9600);
    	Route route1 = new Route(12714, 9600);
       
        assertEquals(route.hashCode(), route1.hashCode());
    }
    
    @Test
    public void cachedLocationEqualsReflexive(){	
    	CachedLocation location = new CachedLocation("2925 Rensselaer Ct. Vienna, VA 22181", 38.95, -77.35);
        assertEquals(location, location);
    }
    
    @Test
    public void cachedLocationEqualsSymmetric() {
    	CachedLocation location = new CachedLocation("2925 Rensselaer Ct. Vienna, VA 22181", 38.95, -77.35);
    	CachedLocation location1 = new CachedLocation("2925 Rensselaer Ct. Vienna, VA 22181", 38.95, -77.35);
        assertThat(location.equals(location1));
        assertThat(location1.equals(location));
    }
    
    @Test
    public void cachedLocationEqualsTransitive() {
    	CachedLocation location = new CachedLocation("2925 Rensselaer Ct. Vienna, VA 22181", 38.95, -77.35);
    	CachedLocation location1 = new CachedLocation("2925 Rensselaer Ct. Vienna, VA 22181", 38.95, -77.35);
    	CachedLocation location2 = new CachedLocation("2925 Rensselaer Ct. Vienna, VA 22181", 38.95, -77.35);
        assertThat(location.equals(location1));
        assertThat(location1.equals(location2));
        assertThat(location.equals(location2));
    }
   
    
    @Test
    public void routeToStringTest()
    {
    	Route route = new Route(12714, 9600);
        String expected = "Route [distance=" + 12714 + ", duration=" + 9600 + "]";
        Assert.assertEquals(expected, route.toString());
    }
    
    @Test
    public void locationToStringTest()
    {
    	CachedLocation location = new CachedLocation("2925 Rensselaer Ct. Vienna, VA 22181", 38.95, -77.35);
        String expected = "Location [address=" + "2925 Rensselaer Ct. Vienna, VA 22181" + ", latitude=" + 38.95 + ", longitude=" + -77.35 + "]";
        Assert.assertEquals(expected, location.toString());
    }
    
    @Test
    public void routeConstructorTest() {
    	new Route();
    }
    
    @Test
    public void locationConstructorTest() {
    	new CachedLocation();
    }
    
    @Test
    public void routeSetterAndGetterTest() {
        Route route = new Route();
        route.setDistance(12714);
        assertEquals(route.getDistance(), 12714);
    }
    
    @Test
    public void routeSetterAndGetterTest2() {
        Route route = new Route();
        route.setDuration(9600);
        assertEquals(route.getDuration(), 9600);
    }
    
    @Test
    public void locationSetterAndGetterTest() {
    	CachedLocation location = new CachedLocation();
        location.setAddress("2925 Rensselaer Ct. Vienna, VA 22181");
        assertEquals(location.getAddress(), "2925 Rensselaer Ct. Vienna, VA 22181");
    }
    
    @Test
    public void locationSetterAndGetterTest2() {
    	CachedLocation location = new CachedLocation();
        location.setLatitude(38.95);
        assertThat(location.getLatitude()==38.95);
    }
    
    @Test
    public void locationSetterAndGetterTest3() {
    	CachedLocation location = new CachedLocation();
        location.setLongitude(-77.35);
        assertThat(location.getLongitude()==-77.35);
    }
    
}
