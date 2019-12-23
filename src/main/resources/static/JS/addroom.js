var addroom = new Vue({
  el: '#addroom',
  data () {
      return {
        buildings: null,
        buildingId: 0,
        roomName: "",
        error: false
      }
  },
  mounted () {
    this.getBuildings();
  },
  methods : {
      getBuildings() {
        axios
            .get('https://walid-ouchtiti.cleverapps.io/api/buildings')
            .then(response => (this.buildings = response.data))
      },
      // addRoom() {
      //     const requestBody = {
      //         id: this.roomid,
      //         lightLevel:this.lightlevel,
      //         noiseLevel:this.noiselevel,
      //         lightStatus:this.lightstatus,
      //         noiseStatus:this.noisestatus,
      //     };
      //     axios
      //         .post('http://localhost:8080/rooms', requestBody, {
      //           headers: {
      //               "Accept": "application/json",
      //               "Content-Type": "application/json",
      //               'Access-Control-Allow-Origin': 'http://localhost:8080',
      //           }
      //         }).then(response => {
      //         this.error = false
      //         window.location.href = "building.html"
      //     }).catch(error => {
      //         console.log(error)
      //         this.error = true
      //     });
      // }
  }
})
