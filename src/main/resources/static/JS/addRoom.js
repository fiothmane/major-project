var addRoom = new Vue({
    el: '#addRoom',
    data () {
        return {
            /* HTTP data */
            room: null,
            buildings: null,
            lights: null,
            ringers: null,
            thermostats: null,
            /* Form data (v-model) */
            floor: 0,
            roomName: "",
            buildingId: "",
            lightList: [],
            ringerId: "",
            thermostatId: "",
            /* Error handling */
            error: null,
        }
    },
    mounted () {
        this.getBuildings();
        this.getLights();
        this.getRingers();
        this.getThermostats();
    },
    created() {
        let uri = window.location.search.substring(1);
        let urlParams = new URLSearchParams(uri);
        this.roomId = urlParams.get("room");
    },
    methods : {
        getBuildings() {
            axios
                .get('https://walid-ouchtiti.cleverapps.io/api/buildings')
                .then(response => (this.buildings = response.data))
        },
        getBuildingFloors(event) {
            for (var i = 0; i < this.buildings.length; i++) {
                if (this.buildings[i].id == event.target.value) {
                    /* Delete all elements in floors list */
                    var floorsList = document.getElementById("floor");
                    floorsList.disabled = false;
                    floorsList.innerHTML = null;
                    var floor = document.createElement('option');
                    floor.value = "";
                    floor.innerHTML = "Choose a floor";
                    floor.selected = true;
                    floor.disabled = true;
                    /* Add floors to the list */
                    floorsList.appendChild(floor);
                    for (var j = 0; j <= this.buildings[i].nbOfFloors; j++) {
                        var floor = document.createElement('option');
                        floor.value = j;
                        floor.innerHTML = j;
                        floorsList.appendChild(floor);
                    }
                }
            }
        },
        getLights() {
            axios
                .get('https://walid-ouchtiti.cleverapps.io/api/lights')
                .then(response => (this.lights = response.data))
        },
        getRingers() {
            axios
                .get('https://walid-ouchtiti.cleverapps.io/api/ringers')
                .then(response => (this.ringers = response.data))
        },
        getThermostats() {
            axios
                .get('https://walid-ouchtiti.cleverapps.io/api/thermostats')
                .then(response => (this.thermostats = response.data))
        },
        addRoom() {
            if (document.getElementById("floor").disabled === true) {
                this.floor = -10;
            }
            const requestBody = {
                name: this.roomName,
                floor: this.floor,
                lightsIds: this.lightList,
                ringerId: this.ringerId,
                thermostatId: this.thermostatId,
                buildingId: this.buildingId,
            };
            axios
                .post('https://walid-ouchtiti.cleverapps.io/api/rooms', requestBody, {
                    headers: {
                        "Accept": "application/json",
                        "Content-Type": "application/json;charset=UTF-8",
                        "access-control-allow-origin": "*",
                        "access-control-allow-credentials": "true",
                        "Access-Control-Allow-Methods": "GET, POST",
                        "access-control-allow-headers": "Origin,Accept,X-Requested-With,Content-Type,X-Auth-Token,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization",
                    }
                })
                .then(response => {this.error = false
                    // window.location.href = "building.html"
                    this.room = response.data;

                    /* Add the auto light controller for the room */
                    const requestBody2 = {
                        sunriseTime: "07:00:00",
                        sunsetTime: "20:00:00",
                        autoLightControlState: "OFF",
                        roomId: this.room.id,
                    };
                    axios
                        .post('https://walid-ouchtiti.cleverapps.io/api/autoLightControllers', requestBody2, {
                            headers: {
                                "Accept": "application/json",
                                "Content-Type": "application/json;charset=UTF-8",
                                "access-control-allow-origin": "*",
                                "access-control-allow-credentials": "true",
                                "Access-Control-Allow-Methods": "GET, POST",
                                "access-control-allow-headers": "Origin,Accept,X-Requested-With,Content-Type,X-Auth-Token,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization",
                            }
                        })
                        .then(response => {this.error = false
                            // window.location.href = "building.html"
                        })
                        .catch(error => {
                            console.log(error)
                            this.error = true
                        });

                })
                .catch(error => {
                    console.log(error)
                    this.error = true
                });

        },
    }
})

