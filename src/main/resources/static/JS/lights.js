var app = new Vue({
    el: '#app',
    data () {
        return {
            roomController: null,
            room: null,
            roomId: null,
            lights: null,
            loading: true,
            deleteMessage: "",
            errored: false
        }
    },
    mounted () {
        this.getFunction();
        this.getRoomInfo();
        this.getRoomAutoLightControllerInfo();
    },
    created() {
        let uri = window.location.search.substring(1);
        let urlParams = new URLSearchParams(uri);
        this.roomId = urlParams.get("room");
    },
    methods : {
        getRoomAutoLightControllerInfo() {
            axios
                .get('https://walid-ouchtiti.cleverapps.io/api/autoLightControllers/')
                .then(response => {
                    var autoLightControllers = response.data;
                    for (var i = 0; i < autoLightControllers.length; i++) {
                        if (autoLightControllers[i].roomId == this.roomId) {
                            this.roomController = autoLightControllers[i];
                            break;
                        }
                    }
                })
        },
        getRoomInfo() {
            /* Add room name as title in the page once loaded */
            axios
                .get('https://walid-ouchtiti.cleverapps.io/api/rooms/' + this.roomId)
                .then(response => {
                    this.room = response.data;
                })
        },
        getFunction(){
            axios
                .get('https://walid-ouchtiti.cleverapps.io/api/lights')
                .then(response => {
                    this.lights = response.data
                })
                .catch(error => {
                    console.log(error)
                    this.errored = true
                })
                .finally(() => this.loading = false);
        },
        autoRefresh() {
            setInterval(() => {
                this.getFunction();
            },2000);
        },
        switchLight (id) {
            var elt = document.getElementById("bulb" + id);
            if (elt.className === "fas fa-lightbulb fa-2x on") {
                for (var i = 0; i < this.lights.length; i++) {
                    if (this.lights[i].id === id) {
                        this.lights[i].status = "OFF";

                        /* Request to philips hue */
                        const philipsHueRequestBody = {
                            on: false,
                        };
                        axios
                            .put('192.168.1.131/api/TwKkhAqEICM5i2W4d1wnEEjhHaR1ZDmMAUlGnZ7a/lights/' + id + '/state', philipsHueRequestBody)
                            .then(response => console.log(response.status))
                    }
                }
                document.getElementById("bulb" + id).className = "fas fa-lightbulb fa-2x off";
                document.getElementById("switchl" + id).innerHTML = '<i class="fas fa-toggle-off fa-2x"></i>';
            }
            else {
                for (var i = 0; i < this.lights.length; i++) {
                    if (this.lights[i].id === id) {
                        this.lights[i].status = "ON";

                        /* Request to philips hue */
                        const philipsHueRequestBody = {
                            on: true,
                        };
                        axios
                            .put('192.168.1.131/api/TwKkhAqEICM5i2W4d1wnEEjhHaR1ZDmMAUlGnZ7a/lights/' + id + '/state', philipsHueRequestBody)
                            .then(response => console.log(response.status))
                    }
                }
                document.getElementById("bulb" + id).className = "fas fa-lightbulb fa-2x on";
                document.getElementById("switchl" + id).innerHTML = '<i class="fas fa-toggle-on fa-2x"></i>';
            }
            /* Change light state in the rest api */
            axios
                .put('https://walid-ouchtiti.cleverapps.io/api/lights/' + id + '/switch')
                .then((response) => {
                    // console.log(response.data)
                });
        },
        changeLightLevel(lightId) {
            /* Change light level in the rest api */
            var lightLevel = document.getElementById("lightLevel" + lightId).value;
            const restApiBody = {
                level: parseInt(lightLevel),
            };
            axios
                .put('https://walid-ouchtiti.cleverapps.io/api/lights/' + lightId + '/level', restApiBody)
                .then((response) => {
                    // console.log(response.data)
                });

            /* Change philips hue light level */
            const philipsHueRequestBody = {
                bri: parseInt(lightLevel),
            };
            axios
                .put('192.168.1.131/api/TwKkhAqEICM5i2W4d1wnEEjhHaR1ZDmMAUlGnZ7a/lights/' + lightId + '/state', philipsHueRequestBody)
                .then(response => console.log(response.status))
        },
        changeLightColor(lightId) {
            /* Change light color in the rest api */
            var lightColor = document.getElementById("lightColor" + lightId).value;
            const restApiBody = {
                color: parseInt(lightColor),
            };
            axios
                .put('https://walid-ouchtiti.cleverapps.io/api/lights/' + lightId + '/color', restApiBody)
                .then((response) => {
                    // console.log(response.data)
                });

            /* Change philips hue light color */
            const philipsHueRequestBody = {
                hue: parseInt(lightColor),
            };
            axios
                .put('192.168.1.131/api/TwKkhAqEICM5i2W4d1wnEEjhHaR1ZDmMAUlGnZ7a/lights/' + lightId + '/state', philipsHueRequestBody)
                .then(response => console.log(response.status))
        },
        deleteLight(lightId) {
            axios
                .delete('https://walid-ouchtiti.cleverapps.io/api/lights/' + lightId, {
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json;charset=UTF-8",
                        "access-control-allow-origin": "*",
                        "access-control-allow-credentials": "true",
                        "Access-Control-Allow-Methods": "GET, POST, DELETE",
                        "access-control-allow-headers": "Origin,Accept,X-Requested-With,Content-Type,X-Auth-Token,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization",
                    }
                })
                .then(
                    roomId = this.roomId,
                    document.getElementById('loading').hidden = false,
                    this.deleteMessage = "success",

                    /* Timer before reloading page */
                    setTimeout(function(){
                        document.getElementById('deleteMessage').innerHTML = "The data has been successfully deleted, you will be redirected to the page after 2 seconds"
                    }, 1000),
                    setTimeout(function(){
                        document.getElementById('deleteMessage').innerHTML = "The data has been successfully deleted, you will be redirected to the page after 1 second"
                    }, 2000),

                    /* Reload the page to refresh info */
                    setTimeout(function(){
                        window.location.href = 'lights.html?room=' + roomId;
                    }, 3000),

                )
                .catch(error => {
                    console.log(error)
                    this.deleteMessage = "problem"
                })
        },
        controlWithArduino(lightId) {
            /* Publish light id in topic in order for arduino to control the light */
            axios
                .put('https://walid-ouchtiti.cleverapps.io/api/lights/' + lightId + '/arduino')
                .then(response => console.log(response.status))
        },
        switchAutoLightControl(roomId) {
            /* Change the page icons */
            var elt = document.getElementById("auto" + roomId);
            if (elt.className === "fas fa-toggle-on fa-2x") {
                this.roomController.autoLightControlState = "OFF"
                elt.className = "fas fa-toggle-off fa-2x";
            }
            else {
                this.roomController.autoLightControlState = "ON";
                elt.className = "fas fa-toggle-on fa-2x";
            }

            /* Get user position */
            navigator.geolocation.getCurrentPosition(function(position) {
                /* Get sunset and sunrise hours */
                axios
                    .get('https://api.sunrise-sunset.org/json?lat=' + position.coords.latitude + '&lng=' + position.coords.longitude + '&date=today' + '&date=today')
                    .then(response => {
                        var sunTimes = response.data;
                        /* Get the room's auto light controller id */
                        axios
                            .get('https://walid-ouchtiti.cleverapps.io/api/autoLightControllers/')
                            .then(response => {
                                var autoLightControlers = response.data;
                                for (var i = 0; i < autoLightControlers.length; i++) {
                                    if (autoLightControlers[i].roomId == roomId) {
                                        /* Switch the state of the auto controller */
                                        axios
                                            .put('https://walid-ouchtiti.cleverapps.io/api/autoLightControllers/' + autoLightControlers[i].id + '/switch')
                                            .then((response) => {
                                                // console.log(response.data)
                                            });

                                        /* Change sunset and sunrise info */
                                        const requestBody = {
                                            sunriseTime: sunTimes.results.sunrise,
                                            sunsetTime: sunTimes.results.sunset,
                                        };
                                        axios
                                            .put('https://walid-ouchtiti.cleverapps.io/api/autoLightControllers/' + autoLightControlers[i].id + '/sunset-sunrise', requestBody)
                                            .then((response) => {
                                                // console.log(response.data)
                                            });
                                    }
                                }
                            })

                    })

            });
        }
    }
})
