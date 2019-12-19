var app = new Vue({
	el: '#rooms',
	data () {
		return {
			rooms: null,
			columns: [
				{ text: 'Room' },
				{ text: 'Light status' },
				{ text: 'Light level' },
				{ text: 'Noise Status' },
				{ text: 'Noise level' },
				{ text: 'On/Off light' },
				{ text: 'On/Off noise' }
			],
		}
	},
	mounted () {
		this.getFunction()
	},
	methods: {
		getFunction () {
			axios
			.get('https://thawing-journey-78988.herokuapp.com/api/rooms')
			.then(response => (this.rooms = response.data))
		},
		//function to auto resfresh
		autoRefresh () {
			setInterval(() => {
				this.getFunction();
			}, 2000);
		},
	    switchLightOnOff (roomId) {
			var icon = document.getElementById("lightStatus" + roomId);
			var icon1 = document.getElementById("lightOnOff" + roomId);
			if (icon.className === "far fa-lightbulb") {
				icon.className = "fas fa-lightbulb";
				icon1.className = "fas fa-toggle-on";
			}
			else {
				icon.className = "far fa-lightbulb";
				icon1.className = "fas fa-toggle-off";
			}

			//put request
			for (var i = 0; i < this.rooms.length; i++) {
				if (this.rooms[i].id === roomId) {
					if (this.rooms[i].light.status === "ON") {
						this.rooms[i].light.status = "ON";
					}
					else {
						this.rooms[i].light.status = "OFF";
					}
				}
			}

			axios
			.put('https://thawing-journey-78988.herokuapp.com/api/rooms/' + roomId + '/switch-light-and-list', this.rooms)
			.then(response => console.log(response.status))
		},

		switchNoiseOnOff (roomId) {
			var icon = document.getElementById("noiseStatus" + roomId);
			var icon1 = document.getElementById("noiseOnOff" + roomId);
			if (icon.className === "fas fa-volume-up") {
				icon.className = "fas fa-volume-off";
				icon1.className = "fas fa-toggle-off";
			}
			else {
				icon.className = "fas fa-volume-up";
				icon1.className = "fas fa-toggle-on";
			}

			//put request
			for (var i = 0; i < this.rooms.length; i++) {
				if (this.rooms[i].id === roomId) {
					if (this.rooms[i].noise.status === "ON") {
						this.rooms[i].noise.status = "ON";
					}
					else {
						this.rooms[i].noise.status = "OFF";
					}
				}
			}

			axios
			.put('https://thawing-journey-78988.herokuapp.com/api/rooms/' + roomId + '/switch-ringer-and-list', this.rooms)
			.then(response => console.log(response.status))
		}
	}
})