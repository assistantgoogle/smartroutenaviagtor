// Variables for Google Maps components
let map, marker, trafficLayer, geofenceCircle, searchBox, directionsService, directionsRenderer, routeLine;
let traveledPath = [];
let routePath;
let currentPositionIndex = 0;

const routeCoordinates = [
    { lat: 12.986827124108899, lng: 77.54024586414629 },
    { lat: 12.990559331136799, lng: 77.53800758944871 },
    
];

const routeData = {
    startCoordinates: routeCoordinates[0],
    endCoordinates: routeCoordinates[1],
    routeName: "Adventure to the Lakes",
    startPoint: "Sankalpa technologies",
    endPoint: "priyadarshini",
   
    
    
};

const vehicleStartPosition = { lat: 12.992870202884179, lng: 77.53395402660377 };

function initMap() {
    const startPosition = routeCoordinates[0];
    map = new google.maps.Map(document.getElementById("map"), {
        center: startPosition,
        zoom: 13,
        mapId: "308cd4aff7e3008c",
    });

    const { AdvancedMarkerElement } = google.maps.marker;
    marker = new AdvancedMarkerElement({
        position: vehicleStartPosition,
        map: map,
        title: "Vehicle Location",
    });

    // Path followed by the vehicle
    routePath = new google.maps.Polyline({
        path: traveledPath,
        geodesic: true,
        strokeColor: "#FF0000",
        strokeOpacity: 1.0,
        strokeWeight: 2,
    });
    routePath.setMap(map);

    // Route line from vehicle to start point
    routeLine = new google.maps.Polyline({
        path: [vehicleStartPosition, routeData.startCoordinates],
        geodesic: true,
        strokeColor: "#0000FF",
        strokeOpacity: 1.0,
        strokeWeight: 2,
    });
    routeLine.setMap(map);

    // Traffic layer
    trafficLayer = new google.maps.TrafficLayer();

    // Geofence circle
    geofenceCircle = new google.maps.Circle({
        strokeColor: "#FF0000",
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: "#FF0000",
        fillOpacity: 0.35,
        map: map,
        center: startPosition,
        radius: 600,
    });
    geofenceCircle.setVisible(false); // Initially hidden

    // Search Box
    const input = document.getElementById("locationSearch");
    searchBox = new google.maps.places.SearchBox(input);
    searchBox.addListener("places_changed", handlePlacesChanged);

    // Directions services
    directionsService = new google.maps.DirectionsService();
    directionsRenderer = new google.maps.DirectionsRenderer({ map: map });

    // Add route markers
    createRouteMarkers();

   
    requestRouteDirections();

   
    document.getElementById('trafficToggleButton').addEventListener('click', toggleTrafficLayer);
    document.getElementById('geofenceToggleButton').addEventListener('click', toggleGeofence);

    
    calculateDistanceAndTime(vehicleStartPosition, routeData.startCoordinates);
    calculateDistanceAndTime(routeData.startCoordinates, routeData.endCoordinates);
}

function sendUpdatedLocation(position) {
    fetch('/ptt/tracking/loadlocation', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ machineId: 'MACHINE_001', lat: position.lat, lng: position.lng }),
    })
        .then(response => response.json())
        .then(data => console.log("Location update:", data))
        .catch(error => console.error("Error in fetch:", error));
}

function createRouteMarkers() {
    new google.maps.Marker({
        position: routeData.startCoordinates,
        map: map,
        title: `Start: ${routeData.startPoint}`,
        icon: 'http://maps.google.com/mapfiles/ms/icons/green-dot.png',
    });

    new google.maps.Marker({
        position: routeData.endCoordinates,
        map: map,
        title: `End: ${routeData.endPoint}`,
        icon: 'http://maps.google.com/mapfiles/ms/icons/red-dot.png',
    });
}

function requestRouteDirections() {
    directionsService.route(
        {
            origin: routeData.startCoordinates,
            destination: routeData.endCoordinates,
            travelMode: google.maps.TravelMode.DRIVING,
        },
        (response, status) => {
            if (status === "OK") {
                directionsRenderer.setDirections(response);
            } else {
                console.error("Directions request failed:", status);
            }
        }
    );
}

function calculateDistanceAndTime(origin, destination) {
    const distanceMatrixService = new google.maps.DistanceMatrixService();

    distanceMatrixService.getDistanceMatrix(
        {
            origins: [origin],
            destinations: [destination],
            travelMode: google.maps.TravelMode.DRIVING,
        },
        (response, status) => {
            if (status === "OK") {
                const result = response.rows[0].elements[0];
                const distance = result.distance.text;
                const duration = result.duration.text;

                console.log(`Distance: ${distance}, Duration: ${duration}`);
                document.getElementById("distance").innerText = `Distance: ${distance}`;
                document.getElementById("time").innerText = `Time: ${duration}`;
            } else {
                console.error("Error calculating distance and time:", status);
            }
        }
    );
}

function handlePlacesChanged() {
    const places = searchBox.getPlaces();
    if (places.length === 0) return;

    const place = places[0];
    if (!place.geometry) {
        console.error("No geometry");
        return;
    }

    updateMarker(place.geometry.location);
}

function toggleTrafficLayer() {
    trafficLayer.setMap(trafficLayer.getMap() ? null : map);
}

function toggleGeofence() {
    geofenceCircle.setVisible(!geofenceCircle.getVisible());
}


window.initMap = initMap;