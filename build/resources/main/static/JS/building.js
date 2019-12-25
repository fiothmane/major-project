var app = new Vue({
    el: '#app',
    data () {
        return {
            buildingId: null,
            rooms: null,
            ringer: null,
            loading: true,
            errored: false,
            deleteMessage: ""
        }
    },
    mounted () {
        this.getFunction();
    },
    created() {
        let uri = window.location.search.substring(1);
        let urlParams = new URLSearchParams(uri);
        this.buildingId = urlParams.get("building");
    },
    methods : {
        getFunction() {
            axios
                .get('http://localhost:8080/api/rooms')
                .then(response => {
                    this.rooms = response.data
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
        deleteRoom(roomId) {
            axios
                .delete('http://localhost:8080/api/rooms/' + roomId, {
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
                    buildingId = this.buildingId,
                    document.getElementById('loading').hidden = false,
                    this.deleteMessage = "success",

                    /* Timer before reloading page */
                    setTimeout(function(){
                        document.getElementById('deleteMessage').innerHTML = "The data was Successfully deleted, you will be redirected to the page after 2 seconds"
                    }, 1000),
                    setTimeout(function(){
                        document.getElementById('deleteMessage').innerHTML = "The data was Successfully deleted, you will be redirected to the page after 1 second"
                    }, 2000),

                    /* Reload the page to refresh info */
                    setTimeout(function(){
                        window.location.href = 'building.html?building=' + buildingId;
                    }, 3000),

                )
                .catch(error => {
                    console.log(error)
                    this.deleteMessage = "problem"
                })

        }
    }
})
