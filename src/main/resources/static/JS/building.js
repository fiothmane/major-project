var app = new Vue({
    el: '#app',
    data () {
        return {
            buildingId: null,
            rooms: null,
            ringer: null,
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
        }
    }
})
