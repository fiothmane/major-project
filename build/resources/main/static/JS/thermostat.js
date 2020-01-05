var app = new Vue({
    el: '#app',
    data () {
        return {
            room: null,
            roomId: null,
            thermostats: null,
            loading: true,
            deleteMessage: "",
            errored: false
        }
    },
    mounted () {
        this.getFunction();
        this.getRoomInfo();
    },
    created() {
        let uri = window.location.search.substring(1);
        let urlParams = new URLSearchParams(uri);
        this.roomId = urlParams.get("room");
    },
    methods : {
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
                .get('https://walid-ouchtiti.cleverapps.io/api/thermostats')
                .then(response => {
                    this.thermostats = response.data
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
        switchThermostat(id) {
            var elt = document.getElementById("thermostat");
            if (elt.textContent === "ON") {
                for (var i = 0; i < this.thermostats.length; i++) {
                    if (this.thermostats[i].id === id) {
                        this.thermostats[i].status = "OFF";
                    }
                }
                document.getElementById("thermostat").textContent = "";
                document.getElementById("thermostat").appendChild(document.createTextNode("OFF"));
                document.getElementById("switchn" + id).innerHTML = '<i class="fas fa-toggle-off fa-2x"></i>';
            }
            else {
                for (var i = 0; i < this.thermostats.length; i++) {
                    if (this.thermostats[i].id === id) {
                        this.thermostats[i].status = "ON";
                    }
                }
                document.getElementById("thermostat").textContent = "";
                document.getElementById("thermostat").appendChild(document.createTextNode("ON"));
                document.getElementById("switchn" + id).innerHTML = '<i class="fas fa-toggle-on fa-2x"></i>';
            }
            axios
                .put('https://walid-ouchtiti.cleverapps.io/api/thermostats/' + id + '/switch')
                .then((response) => {
                    // console.log(response.data)
                });
        },
        changeThermostatLevel(thermostatId) {
            var thermostatLevel = document.getElementById("thermostatLevel" + thermostatId).value;
            const restApiBody = {
                level: parseInt(thermostatLevel),
            };
            axios
                .put('https://walid-ouchtiti.cleverapps.io/api/thermostats/' + thermostatId + '/level', restApiBody)
                .then((response) => {
                    // console.log(response.data)
                });
        },
        deleteThermostat(thermostatId) {
            axios
                .delete('https://walid-ouchtiti.cleverapps.io/api/thermostats/' + thermostatId, {
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
                        window.location.href = 'thermostat.html?room=' + roomId;
                    }, 3000),

                )
                .catch(error => {
                    console.log(error)
                    this.deleteMessage = "problem"
                })
        }
    }
})
