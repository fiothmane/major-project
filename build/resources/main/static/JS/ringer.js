var app = new Vue({
    el: '#app',
    data () {
        return {
            roomId: null,
            ringers: null,
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
                .get('https://walid-ouchtiti.cleverapps.io/api/ringers')
                .then(response => {
                    this.ringers = response.data
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
        switchRinger(id) {
            var elt = document.getElementById("ringer" + id);
            if (elt.textContent === "ON") {
                for (var i = 0; i < this.ringers.length; i++) {
                    if (this.ringers[i].id === id) {
                        this.ringers[i].status = "OFF";
                    }
                }
                document.getElementById("ringer" + id).textContent = "";
                document.getElementById("ringer" + id).appendChild(document.createTextNode("OFF"));
                document.getElementById("switchn" + id).innerHTML = '<i class="fas fa-toggle-off fa-2x"></i>';
            }
            else {
                for (var i = 0; i < this.ringers.length; i++) {
                    if (this.ringers[i].id === id) {
                        this.ringers[i].status = "ON";
                    }
                }
                document.getElementById("ringer" + id).appendChild(document.createTextNode("ON"));
                document.getElementById("switchn" + id).innerHTML = '<i class="fas fa-toggle-on fa-2x"></i>';
            }
            axios
                .put('http://localhost:8080/api/ringers/' + id + '/switch')
                .then((response) => {console.log(response.data)});
        },
    }
})
