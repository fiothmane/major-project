var addroom = new Vue({
  el: '#addroom',
  data () {
    return {
      roomid:'',
      lightlevel:0,
      noiselevel:0,
      lightstatus:'ON',
      noisestatus:'ON',
      error: false
    }
  },
  methods : {
  	addRoom(){

      const requestBody = {
        id: this.roomid,
        lightLevel:this.lightlevel,
        noiseLevel:this.noiselevel,
        lightStatus:this.lightstatus,
        noiseStatus:this.noisestatus,
      };
      axios.post('http://localhost:8080/rooms',
      requestBody, // the data to post
      { headers: {
        "Accept": "application/json",
        "Content-Type": "application/json",
        'Access-Control-Allow-Origin': 'http://localhost:8080',
        }
      }).then(response => {
        this.error = false
        window.location.href = "room.html"
      }).catch(error => {
        console.log(error)
        this.error = true
      });
    }
  }

})
