var nombre ="Edwin";
var descripcion=JSON.parse("{}");
var blat,blon;
function newData(jsonData){
	  descripcion = JSON.parse(jsonData);

	  /*for(var i = 0; i < jsonObject.length; i++){

	    // use jsonObject[i] data to create the AR objects for each POI
	  }*/
	}


	function sortByKey(array, key) {
    return array.sort(function(a, b) {
        var x = a[key]; var y = b[key];
        return ((x < y) ? -1 : ((x > y) ? 1 : 0));
    });
}

	function fondo(categoria){
		drawable=null;
	  switch(categoria){
	  	case 1:
	  		drawable = new AR.ImageResource("assets/hoteles.png");
	  		break;
	  	case 2:
	  		drawable = new AR.ImageResource("assets/restaurantes.png");
	  		break;
	  	case 3:
	  		drawable = new AR.ImageResource("assets/monumentos.png");
	  		break;
	  	case 4:
	  		drawable = new AR.ImageResource("assets/museos.png");
	  		break;
	  	case 5:
	  		drawable = new AR.ImageResource("assets/bancos.png");
	  		break;
	  	case 6:
	  		drawable = new AR.ImageResource("assets/farmacias.png");
	  		break;
	  	case 7:
	  		drawable = new AR.ImageResource("assets/tiendas.png");
	  		break;
	  	case 8:
	  		drawable = new AR.ImageResource("assets/plazas.png");
	  		break;
	  	case 9:
	  		drawable = new AR.ImageResource("assets/parques.png");
	  		break;
	  	case 10:
	  		drawable = new AR.ImageResource("assets/otros.png");
	  		break;
	  	default:
	  		drawable = new AR.ImageResource("assets/monumentos.png");
	  }
	  return drawable;
	}
// implementation of AR-Experience (aka "World")
var World = {
	// true once data was fetched
	initiallyLoadedData: false,

	// different POI-Marker assets
	markerDrawable_idle: null,
	markerDrawable_selected: null,

	// list of AR.GeoObjects that are currently shown in the scene / World
	markerList: [],

	// The last selected marker
	currentMarker: null,

	// called to inject new POI data
	loadPoisFromJsonData: function loadPoisFromJsonDataFn(poiData) {

		//show radar & set click-listener
                		PoiRadar.show();
                		//$('#radarContainer').unbind('click');

		// empty list of visible markers
		World.markerList = [];
		//AR.context.destroyAll();

		// start loading marker assets
		//World.markerDrawable_idle = new AR.ImageResource("assets/marker_idle.png");
		
			World.markerDrawable_idle = new AR.ImageResource("assets/farmacias.png");
			World.markerDrawable_selected = new AR.ImageResource("assets/cuadro.png");
			World.markerDrawable_directionIndicator = new AR.ImageResource("assets/indi.png");
		// loop through POI-information and create an AR.GeoObject (=Marker) per POI
		for (var currentPlaceNr = 0; currentPlaceNr < poiData.length; currentPlaceNr++){
			drawable = fondo(parseInt(poiData[currentPlaceNr].cat));
			var singlePoi = {
				"id": poiData[currentPlaceNr].id,
				"latitude": parseFloat(poiData[currentPlaceNr].latitude),
				"longitude": parseFloat(poiData[currentPlaceNr].longitude),
				"altitude": parseFloat(poiData[currentPlaceNr].altitude),
				"title": poiData[currentPlaceNr].name,
				"description": poiData[currentPlaceNr].description,
				"distancia":poiData[currentPlaceNr].distancia
			};

			/*
				To be able to deselect a marker while the user taps on the empty screen, 
				the World object holds an array that contains each marker.
			*/
			World.markerList.push(new Marker(singlePoi,drawable));
		}

		World.updateStatusMessage(currentPlaceNr + ' Sitios cercanos cargados');
	},

	// updates status message shon in small "i"-button aligned bottom center
	updateStatusMessage: function updateStatusMessageFn(message, isWarning) {

		var themeToUse = isWarning ? "e" : "c";
		var iconToUse = isWarning ? "alert" : "info";

		$("#status-message").html(message);
		$("#popupInfoButton").buttonMarkup({
			theme: themeToUse
		});
		$("#popupInfoButton").buttonMarkup({
			icon: iconToUse
		});
	},

	// location updates, fired every time you call architectView.setLocation() in native environment
	locationChanged: function locationChangedFn(lat, lon, alt, acc) {

		/*
			The custom function World.onLocationChanged checks with the flag World.initiallyLoadedData if the function was already called. With the first call of World.onLocationChanged an object that contains geo information will be created which will be later used to create a marker using the World.loadPoisFromJsonData function.
		*/
		var l2=new AR.GeoLocation(parseFloat(lat),parseFloat(lon));

		
		if (blat==null)
		{
			blat=lat;
			blon=lon;
			AR.logger.warning(blat+","+blon);
			World.requestDataFromLocal(lat, lon,alt);
		}else{
			AR.logger.warning(blat+","+blon);
			var walk = l2.distanceTo(new AR.GeoLocation(parseFloat(blat),parseFloat(blon)));
			AR.logger.warning("Walk = "+walk);
			if (walk>10){
				AR.logger.warning("cambió más de 10m");
				World.requestDataFromLocal(lat, lon,alt);	
				blat=lat;
				blon=lon;
			}
		}




		

		


		/*if (!World.initiallyLoadedData) {
			 
				requestDataFromLocal with the geo information as parameters (latitude, longitude) creates different poi data to a random location in the user's vicinity.
			
			
			World.requestDataFromLocal(lat, lon);
			World.initiallyLoadedData = true;
			
		}*/
	},

	// fired when user pressed maker in cam
	onMarkerSelected: function onMarkerSelectedFn(marker) {

		// deselect previous marker
		if (World.currentMarker) {
			if (World.currentMarker.poiData.id == marker.poiData.id) {
				return;
			}
			World.currentMarker.setDeselected(World.currentMarker);
		}

		// highlight current one
		marker.setSelected(marker);
		World.currentMarker = marker;


	




	},

	// screen was clicked but no geo-object was hit
	onScreenClick: function onScreenClickFn() {
		if (World.currentMarker) {
			World.currentMarker.setDeselected(World.currentMarker);
		}
	},

	// request POI data
	requestDataFromLocal: function requestDataFromLocalFn(centerPointLatitude, centerPointLongitude, centerPointAltitude) {

		// cuantos puntos crear
		var poisToCreate = 20;
		var poisID=[];
		AR.logger.warning("Altitud" + centerPointAltitude);
		for(var i=0;i<descripcion.length;i++){

			var location= new AR.GeoLocation(parseFloat(descripcion[i][5]),parseFloat(descripcion[i][6]));
			var dist = location.distanceToUser();
			//AR.logger.warning("id: "+descripcion[i][0]+"  dist: "+dist);
			poisID.push({
				"id" : descripcion[i][0],
				"distancia": dist
			});
		}
		poisID=sortByKey(poisID,"distancia");
		var poiData = [];
		


		//var distanceToUserValue = (markerLocation.distanceToUser() > 999) ? ((markerLocation.distanceToUser() / 1000).toFixed(2) + " km") : (Math.round(markerLocation.distanceToUser()) + " m");
		for (var i = 0; i < poisToCreate; i++) {
			var id=poisID[i].id-1;
			var distancia=poisID[i].distancia;
			poiData.push({
				"id": (id + 1),
				"longitude": (descripcion[id][6]), 
				"latitude": (descripcion[id][5]),
				"altitude": (centerPointAltitude+(i*10)),
				//"description": (String(dist)+" m"),
				"distancia": (String ((distancia > 999) ? ((distancia / 1000).toFixed(2) + " km") : (Math.round(distancia) + " m") )),
				"description": (descripcion[id][4]),
				"altitude": "990.0",
				"cat": (descripcion[id][1]),
				"name": (String(descripcion[id][2]))
			});
		}
		
		World.loadPoisFromJsonData(poiData);
	}

};

/* 
	Set a custom function where location changes are forwarded to. There is also a possibility to set AR.context.onLocationChanged to null. In this case the function will not be called anymore and no further location updates will be received. 
*/
AR.context.onLocationChanged = World.locationChanged;

/*
	To detect clicks where no drawable was hit set a custom function on AR.context.onScreenClick where the currently selected marker is deselected.
*/
AR.context.onScreenClick = World.onScreenClick;