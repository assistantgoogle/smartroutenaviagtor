let map;
let trafficLayer;
let geofenceCircle;
let infoWindow;
let geocoder;

function initMap() {
    const location = { lat: 12.947028892253815, lng: 77.47392308422249 };
    map = new google.maps.Map(document.getElementById("map"), {
        zoom: 14,
        center: location
    });

    infoWindow = new google.maps.InfoWindow(); // Create the info window
    geocoder = new google.maps.Geocoder(); // Initialize geocoder

    const marker = new google.maps.Marker({
        position: location,
        map: map
    });

    // Use reverse geocoding to get the address
    geocodeLatLng(geocoder, location, map, marker);

    // Add hover event to display address info in the info window
    marker.addListener('mouseover', function() {
        infoWindow.open(map, marker);
    });

    // Add click event to display more info in the info window
    marker.addListener('click', function() {
        infoWindow.setContent(
            `<strong>Sankalp Technologies</strong><br>` +
            `Rating: 4.5<br>` +
            `Address: 77, Some Street, Bengaluru`
        );
        infoWindow.open(map, marker);
    });

    trafficLayer = new google.maps.TrafficLayer();

    // Add geofencing
    geofenceCircle = new google.maps.Circle({
        strokeColor: "#FF0000",
        strokeOpacity: 0.8,
        strokeWeight: 2,
        fillColor: "#FF0000",
        fillOpacity: 0.35,
        map,
        center: location,
        radius: 100, // 100 meters
    });
    geofenceCircle.setVisible(false);
}

// Function to reverse geocode latitude and longitude into an address
function geocodeLatLng(geocoder, location, map, marker) {
    geocoder.geocode({ location: location }, function(results, status) {
        if (status === "OK") {
            if (results[0]) {
                infoWindow.setContent(results[0].formatted_address); // Set the address in the InfoWindow
            } else {
                console.log("No results found");
            }
        } else {
            console.log("Geocoder failed due to: " + status);
        }
    });
}

// Toggle map display
document.getElementById('liveTrackingLink').addEventListener('click', function () {
    document.getElementById('map').style.display = 'block';
    initMap();
});

// Toggle traffic layer
document.getElementById('trafficToggle').addEventListener('click', function () {
    if (trafficLayer.getMap() == null) {
        trafficLayer.setMap(map);
    } else {
        trafficLayer.setMap(null);
    }
});

// Toggle geofence visibility
document.getElementById('geofenceToggle').addEventListener('click', function () {
    geofenceCircle.setVisible(!geofenceCircle.getVisible());
});
