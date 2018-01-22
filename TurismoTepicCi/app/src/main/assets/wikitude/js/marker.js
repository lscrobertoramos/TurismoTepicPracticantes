var datos;
function Marker(poiData,drawable) {

    /*
        For creating the marker a new object AR.GeoObject will be created at the specified geolocation. An AR.GeoObject connects one or more AR.GeoLocations with multiple AR.Drawables. The AR.Drawables can be defined for multiple targets. A target can be the camera, the radar or a direction indicator. Both the radar and direction indicators will be covered in more detail in later examples.
    */
    this.poiData = poiData;

    // create the AR.GeoLocation from the poi data
    //var markerLocation = new AR.GeoLocation(poiData.latitude, poiData.longitude, poiData.altitude);
    var markerLocation = new AR.GeoLocation(poiData.latitude, poiData.longitude);

    // create an AR.ImageDrawable for the marker in idle state
    this.markerDrawable_idle = new AR.ImageDrawable(drawable, 1, {
        zOrder: 0,
        opacity: 1.0,
        /*
            To react on user interaction, an onClick property can be set for each AR.Drawable. The property is a function which will be called each time the user taps on the drawable. The function called on each tap is returned from the following helper function defined in marker.js. The function returns a function which checks the selected state with the help of the variable isSelected and executes the appropriate function. The clicked marker is passed as an argument.
        */
        onClick: Marker.prototype.getOnClickTrigger(this)
    });

    // create an AR.ImageDrawable for the marker in selected state
    this.markerDrawable_selected = new AR.ImageDrawable(World.markerDrawable_selected, 3, {
        zOrder: 0,
        offsetY: -3,
        opacity: 0.0,
        onClick: null
    });

    // create an AR.Label for the marker's title 
    this.titleLabel = new AR.Label(poiData.title.trunc(100), .6, {
        zOrder: 1,
        offsetY: -1,
        style: {
            textColor: '#FFFFFF',
            fontStyle: AR.CONST.FONT_STYLE.BOLD
        }
    });

    // create an AR.Label for the marker's description
    //var location1 = new AR.GeoLocation(parseInt(descripcion[i][5]),parseInt(descripcion[i][6]));
    //var distance= marker.distanceToUser();
    
    this.descriptionLabel = new AR.Label("hola  hola otra ", 0.8, {
        zOrder: 1,
        opacity:0,
        offsetY: -2.3,
        style: {
            textColor: '#000000'
        }
    });

//radar

 this.radarCircle = new AR.Circle(0.03, {
        horizontalAnchor: AR.CONST.HORIZONTAL_ANCHOR.CENTER,
        opacity: 0.8,
        style: {
            fillColor: "#ffffff"
        }
    });

    this.radarCircleSelected = new AR.Circle(0.05, {
        horizontalAnchor: AR.CONST.HORIZONTAL_ANCHOR.CENTER,
        opacity: 0.8,
        style: {
            fillColor: "#0066ff"
        }
    });

    this.radardrawables = [];
    this.radardrawables.push(this.radarCircle);

//

    // create the AR.GeoObject with the drawable objects
    this.markerObject = new AR.GeoObject(markerLocation, {
    // quitar titulo y dejar el puro icono
        drawables: {
            cam: [this.markerDrawable_idle, this.markerDrawable_selected],
            indicator: this.directionIndicatorDrawable,
                                    radar: this.radardrawables
            //cam: [this.markerDrawable_idle, this.markerDrawable_selected, this.titleLabel, this.descriptionLabel]
        }
    });

    return this;
}

Marker.prototype.getOnClickTrigger = function(marker) {

    /*
        The setSelected and setDeselected functions are prototype Marker functions.

        Both functions perform the same steps but inverted, hence only one function (setSelected) is covered in detail. Three steps are necessary to select the marker. First the state will be set appropriately. Second the background drawable will be enabled and the standard background disabled. This is done by setting the opacity property to 1.0 for the visible state and to 0.0 for an invisible state. Third the onClick function is set only for the background drawable of the selected marker.
    */

    return function() {

        if (marker.isSelected) {

            Marker.prototype.setDeselected(marker);

        } else {
            Marker.prototype.setSelected(marker);
            try {
                World.onMarkerSelected(marker);
            } catch (err) {
                alert(err);
            }

        }

        return true;
    };
};

Marker.prototype.setSelected = function(marker) {
    //marker.descriptionLabel.opacity=1;
    marker.isSelected = true;
    //marker.markerDrawable_idle.opacity = 0.0;
    /*marker.markerDrawable_selected.opacity = 1.0;
    marker.markerDrawable_idle.onClick = null;
    marker.markerDrawable_selected.onClick = Marker.prototype.getOnClickTrigger(marker);*/

         // update panel values
    $('#panel-poidetaile').show();
    $("#poi-detail-title").html(marker.poiData.title);
    $("#poi-detail-description").html(marker.poiData.description);

    var distanceToUserValue = (marker.distanceToUser > 999) ? ((marker.distanceToUser / 1000).toFixed(2) + " km") : (Math.round(marker.distanceToUser) + " m");

    $("#poi-detail-distance").html(marker.poiData.distancia);

    // show panel
    $("#panel-poidetail").panel("open", 123);

    $("#panel-poidetail").on("panelbeforeclose", function(event, ui) {
    World.currentMarker.setDeselected(World.currentMarker)});
};

Marker.prototype.setDeselected = function(marker) {
    
    //marker.descriptionLabel.opacity=0;
    $('#panel-poidetaile').hide();
    marker.isSelected = false;
    //marker.markerDrawable_idle.opacity = 1.0;
    marker.markerDrawable_selected.opacity = 0.0;

    marker.markerDrawable_idle.onClick = Marker.prototype.getOnClickTrigger(marker);
    marker.markerDrawable_selected.onClick = null;
};

// will truncate all strings longer than given max-length "n". e.g. "foobar".trunc(3) -> "foo..."
String.prototype.trunc = function(n) {
    return this.substr(0, n - 1) + (this.length > n ? '...' : '');
};