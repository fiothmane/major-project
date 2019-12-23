var app = new Vue({
    el: '#app',
    data () {
        return {
            roomId: null,
            lights: null,
            loading: true,
            errored: false
        }
    },
    mounted () {
        this.getFunction();
    },
    created() {
        let uri = window.location.search.substring(1);
        let urlParams = new URLSearchParams(uri);
        this.roomId = urlParams.get("room");
    },
    methods : {
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
        autoRefresh(){
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
                    }
                }
                document.getElementById("bulb" + id).className = "fas fa-lightbulb fa-2x off";
                document.getElementById("switchl" + id).innerHTML = '<i class="fas fa-toggle-off fa-2x"></i>';
            }
            else {
                for (var i = 0; i < this.lights.length; i++) {
                    if (this.lights[i].id === id) {
                        this.lights[i].status = "ON";
                    }
                }
                document.getElementById("bulb" + id).className = "fas fa-lightbulb fa-2x on";
                document.getElementById("switchl" + id).innerHTML = '<i class="fas fa-toggle-on fa-2x"></i>';
            }
            axios
                .put('https://walid-ouchtiti.cleverapps.io/api/lights/' + id + '/switch')
                .then((response) => {console.log(response.data)});
        },
    }
})
