# androidmark1
Mark1 is an important step : 

This app can send and receive requests from restAPI.
The restAPI will connect to a external database and retrieve the data from the database (this database resides in  the public IP)
The response is sent to the Apk as a json.

This is a milestone - because now our apk can communicate to my main server from any part of the world.
It also helps me to send and recive data through restAPI, so even if my DB is gonna change, I can handle the change (loose coupling) to my DB and apk.

Dependency : if you are running the restAPI on a local host - use 10.0.2.2 in place of local host in the android code
If you are running it on another phone
	1) run the restAPI in you local PC/laptop
	2) Use ngrok.exe to make an external IP to route the requests to the restAPI running on your local
	3) Use the Address mentioned by ngrok.exe in your Android code


 
