var app = new Vue({
    el: '#app',
    data () {
        return {
            buildingId: null,
            rooms: null,
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
        this.buildingId = urlParams.get("building");
    },
    methods : {
        getFunction(){
            axios
                .get('https://walid-ouchtiti.cleverapps.io/api/rooms')
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
        switchNoise (id) {
            var elt = document.getElementById("noises"+id);
            if(elt.innerHTML === "ON"){
                document.getElementById("noises"+id).innerHTML = "OFF";
                document.getElementById("switchn"+id).innerHTML = '<i class="fas fa-toggle-off fa-2x"></i>';
            }else{
                document.getElementById("noises"+id).innerHTML = "ON";
                document.getElementById("switchn"+id).innerHTML = '<i class="fas fa-toggle-on fa-2x"></i>';
            }

            //put request for updating the ringer status
            this.rooms[parseInt(id-1)].noise.status = elt.innerHTML;
            console.log(this.rooms[parseInt(id-1)].noise.status);
            axios.put('https://thawing-journey-78988.herokuapp.com/api/rooms/'+id+'/switch-ringer-and-list',this.rooms)
                .then((response) => {console.log(response.data)});

        }
    }
})
