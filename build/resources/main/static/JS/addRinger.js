var addroom = new Vue({
    el: '#addroom',
    data () {
        return {
            /* HTTP data */
            rooms: null,
            /* Form data (v-model) */
            ringerLevel: 0,
            ringerStatus: "",
            roomId: "",
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
                .get('http://localhost:8080/api/rooms')
                .then(response => (this.rooms = response.data))
        },
        addRinger() {
            const requestBody = {
                level: this.ringerLevel,
                status: this.ringerStatus,
                roomId: this.roomId,
            };
            axios
                .post('http://localhost:8080/api/ringers', requestBody, {
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
