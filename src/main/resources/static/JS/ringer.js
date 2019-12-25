var app = new Vue({
    el: '#app',
    data () {
        return {
            roomId: null,
            ringers: null,
            loading: true,
            deleteMessage: "",
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
            var elt = document.getElementById("ringer");
            if (elt.textContent === "ON") {
                for (var i = 0; i < this.ringers.length; i++) {
                    if (this.ringers[i].id === id) {
                        this.ringers[i].status = "OFF";
                    }
                }
                document.getElementById("ringer").textContent = "";
                document.getElementById("ringer").appendChild(document.createTextNode("OFF"));
                document.getElementById("switchn" + id).innerHTML = '<i class="fas fa-toggle-off fa-2x"></i>';
            }
            else {
                for (var i = 0; i < this.ringers.length; i++) {
                    if (this.ringers[i].id === id) {
                        this.ringers[i].status = "ON";
                    }
                }
                document.getElementById("ringer").textContent = "";
                document.getElementById("ringer").appendChild(document.createTextNode("ON"));
                document.getElementById("switchn" + id).innerHTML = '<i class="fas fa-toggle-on fa-2x"></i>';
            }
            axios
                .put('https://walid-ouchtiti.cleverapps.io/api/ringers/' + id + '/switch')
                .then((response) => {
                    // console.log(response.data)
                });
        },
        changeRingerLevel(ringerId) {
            var ringerLevel = document.getElementById("ringerLevel" + ringerId).value;
            const restApiBody = {
                level: parseInt(ringerLevel),
            };
            axios
                .put('https://walid-ouchtiti.cleverapps.io/api/ringers/' + ringerId + '/level', restApiBody)
                .then((response) => {
                    // console.log(response.data)
                });
        },
        deleteRinger(ringerId) {
            axios
                .delete('https://walid-ouchtiti.cleverapps.io/api/ringers/' + ringerId, {
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
                        window.location.href = 'ringer.html?room=' + roomId;
                    }, 3000),

                )
                .catch(error => {
                    console.log(error)
                    this.deleteMessage = "problem"
                })
        }
    }
})
