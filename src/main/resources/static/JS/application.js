var app = new Vue({
  el: '#app',
  data () {
    return {
      rooms: null,
      loading: true,
      errored: false
    }
  },
  mounted () {
      this.getFunction();
  },
  methods : {

  	getFunction(){
        axios
        .get('https://thawing-journey-78988.herokuapp.com/api/rooms/')
        .then(response => {
          this.rooms = response.data
        })
        .catch(error => {
          console.log(error)
          this.errored = true
        })
        .finally(() => this.loading = false);
    },
  	autorefresh(){
    	setInterval(() => {
      	this.getFunction();
      },2000);
    },
  	switchLight (id) {
          var elt = document.getElementById("bulb"+id);
          if(elt.className === "fas fa-lightbulb fa-2x on"){
            this.rooms[parseInt(id-1)].light.status = "OFF";
            document.getElementById("bulb"+id).className = "fas fa-lightbulb fa-2x off";
            document.getElementById("switchl"+id).innerHTML = '<i class="fas fa-toggle-off fa-2x"></i>';
          }else{
            this.rooms[parseInt(id-1)].light.status = "ON";
            document.getElementById("bulb"+id).className = "fas fa-lightbulb fa-2x on";
            document.getElementById("switchl"+id).innerHTML = '<i class="fas fa-toggle-on fa-2x"></i>';
          }

          //put request for updating the light status
          console.log(this.rooms[parseInt(id-1)].light.status);
          axios.put('https://thawing-journey-78988.herokuapp.com/api/rooms/'+id+'/switch-light-and-list',this.rooms)
          .then((response) => {console.log(response.data)});


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
