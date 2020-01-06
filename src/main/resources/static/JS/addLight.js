var addroom = new Vue({
    el: '#addroom',
    data () {
        return {
            /* HTTP data */
            rooms: null,
            light: null,
            /* Form data (v-model) */
            lightId: 0,
            roomId: "",
            error: "",
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
        addLight() {
            axios
                .get('https://walid-ouchtiti.cleverapps.io/api/lights/' + this.lightId)
                .then(response => {this.error = "lightAlreadyExist"})
                .catch(error => {
                    console.log(error)
                            /* Request body for the rest api */
                            const requestBody = {
                                id: this.lightId,
                                level: 0,
                                status: "OFF",
                                roomId: this.roomId,
                            };
                            axios
                                .post('https://walid-ouchtiti.cleverapps.io/api/lights', requestBody, {
                                    headers: {
                                        "Accept": "application/json",
                                        "Content-Type": "application/json;charset=UTF-8",
                                        "access-control-allow-origin": "*",
                                        "access-control-allow-credentials": "true",
                                        "Access-Control-Allow-Methods": "GET, POST",
                                        "access-control-allow-headers": "Origin,Accept,X-Requested-With,Content-Type,X-Auth-Token,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization",
                                    }
                                })
                                .then(response => {
                                    this.error = "false"
                                    // window.location.href = "building.html"
                                })
                                .catch(error => {
                                    console.log(error)
                                    this.error = "true"
                                });
                });
        }
    }
})
