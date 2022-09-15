import ch.hevs.cloudio.endpoint.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class demoEndpoint implements Runnable, CloudioEndpointListener{

    //Redirect logs to the cloud
    static Logger log = LogManager.getLogger(demoEndpoint.class);

    CloudioEndpoint myEndpoint;
    MyNode myNode;
    private double i;

    demoEndpoint() throws CloudioEndpointInitializationException, InvalidPropertyException, InvalidUuidException, DuplicateItemException {
        myEndpoint = new CloudioEndpoint("example");

        // listen to endpoint is online and endponit is offline events
        myEndpoint.addEndpointListener(this);

        // add one node called "myNode" with type MyNode
        myEndpoint.addNode("myNode", MyNode.class);
        myNode = myEndpoint.getNode("myNode");

        i = 0;

        // set the counter value from cloud
        myNode.myObject.mySetPoint.addListener(new CloudioAttributeListener() {
            @Override
            public void attributeHasChanged(CloudioAttribute attribute) {
                i = (double)attribute.getValue();
                log.trace("New value is: " + i);
            }
        });

        // print the String received from cloud
        myNode.myObject.printText.addListener(new CloudioAttributeListener() {
            @Override
            public void attributeHasChanged(CloudioAttribute attribute) { log.trace(attribute.getValue()); }
        });
    }

    @Override
    public void run() {
        //counter increments every 1s and updates 2 attributes
        while (true){
            try {
                log.trace("Value is "+ i);

                myNode.myObject.myMeasure.setValue(i++);
                myNode.myObject.myInt.setValue((int)i);

                i %= 100;
                Thread.sleep(1000);
            } catch (InterruptedException | CloudioAttributeConstraintException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws DuplicateItemException, InvalidPropertyException, InvalidUuidException, CloudioEndpointInitializationException {
        demoEndpoint de = new demoEndpoint();
        de.run();
    }

    @Override
    public void endpointIsOnline(CloudioEndpoint endpoint) {
        System.out.println("Endpoint is online");
    }

    @Override
    public void endpointIsOffline(CloudioEndpoint endpoint) {
        System.out.println("Endpoint is offline");
    }
}
