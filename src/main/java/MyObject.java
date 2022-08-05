import ch.hevs.cloudio.endpoint.CloudioAttribute;
import ch.hevs.cloudio.endpoint.CloudioObject;
import ch.hevs.cloudio.endpoint.Measure;
import ch.hevs.cloudio.endpoint.SetPoint;

public class MyObject extends CloudioObject {

    // Add 2 attributes with Measure constraint (endpoint to cloud) in the object
    @Measure
    public CloudioAttribute<Integer> myInt;

    @Measure
    public CloudioAttribute<Double> myMeasure;

    // Add 2 attributes with SetPoint constraint (cloud to endpoint) in the object
    @SetPoint
    public CloudioAttribute<Double> mySetPoint;

    @SetPoint
    public CloudioAttribute<String> printText;

}
