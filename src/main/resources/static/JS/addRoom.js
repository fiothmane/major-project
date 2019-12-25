var addRoom = new Vue({
    el: '#addRoom',
    data () {
        return {
            /* HTTP data */
            buildings: null,
            lights: null,
            ringers: null,
            /* Form data (v-model) */
            floor: 0,
            roomName: "",
            buildingId: "",
            lightList: [],
            ringerId: "",
            /* Error handling */
            error: null,
        }
    },
    mounted () {
        this.getBuildings();
        this.getLights();
        this.getRingers();
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
        addRoom() {
            if (document.getElementById("floor").disabled === true) {
                this.floor = -10;
            }
            const requestBody = {
                name: this.roomName,
                floor: this.floor,
                lightsIds: this.lightList,
                ringerId: this.ringerId,
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
                })
                .catch(error => {
                    console.log(error)
                    this.error = true
                });
        },
    }
})

