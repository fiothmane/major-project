var addroom = new Vue({
    el: '#addroom',
    data () {
        return {
            /* HTTP data */
            rooms: null,
            /* Form data (v-model) */
            nbOfFloors: 0,
            buildingName: "",
            roomsList: [],
            /* Error handling */
            error: null,
        }
    },
    mounted () {
        this.getRooms();
    },
    methods : {
        getRooms() {
            axios
                .get('https://walid-ouchtiti.cleverapps.io/api/rooms')
                .then(response => (this.rooms = response.data))
        },
        addBuilding() {
            const requestBody = {
                name: this.buildingName,
                nbOfFloors: this.nbOfFloors,
                roomsIds: this.roomsList,
            };
            axios
                .post('https://walid-ouchtiti.cleverapps.io/api/buildings', requestBody, {
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
        }
    }
})
