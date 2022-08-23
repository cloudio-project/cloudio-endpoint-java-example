# cloudio-endpoint-java-example
This project is a simple demo of the use of the [cloudio-endpoint-java](https://github.com/cloudio-project/cloudio-endpoint-java)  library in a gradle project.
# How to use
- Add your github username and api key in gradle.properties.
- Replace your_UUID.p12 and authority.jks with your certificates
- Rename your_UUID.properties with your endpoint's UUID
- Complete the missing informations in your_UUID.properties
- Replace "your_UUID" by your endpoint's UUID in demoEndpoint.java
# About this example
## Endpoint structure
- demoEndpoint:Endpoint
	- myNode:Node
		- myObject:Object
			- myInt:Attribute, Measure Integer
			- myMeasure:Attribute, Measure Double
			- mySetpoint:Attribute, SetPoint Double
			- printText:Attribute, SetPoint String

## Monitoring
### Endpoint pushing data to cloud.iO
In Java, pushing data is made with the setValue method on an Attribute:
```Java
myNode.myObject.myMeasure.setValue(10); 
```
### User retrieving endpoint's data from cloud.iO
With the Rest API, the user can retrieve data from the Endpoint. A swagger UI is available on your cloud.iO instance following this route: ```cloudioServerURL/swagger-ui/#/```
The Endpoint Model Access part of the REST API will allow a user to retrieve the data model of an Endpoint down to attribute and its historical values.
## Controlling
## User action on endpoint  
With the REST API, it is possible to send a command to the endpoint, for example setting a SetPoint. This can be done with the Endpoint Model Access. 
## Endpoint reacting to User command
A listener can be attached to attributes to react to user command:
```Java
myNode.myObject.mySetPoint.addListener(new CloudioAttributeListener() {
	@Override
	public void attributeHasChanged(CloudioAttribute attribute) {
		i = (double)attribute.getValue();
		log.trace("New value is: " + i);
	}
});
```
Be aware that in this example project, ```log.trace()``` to send log to cloud.iO and also print them in the console. If your log level is set higher than TRACE, you won't see anything in the console. To manage your log level, check Endpoint Management of the REST API.