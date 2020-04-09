package sensors;

public class Sensors {

	private static double windUpdateInterval = 2.5; // 2.5 to 3
	public static void main(String[] args) {
		HumiditySensor hSense = new HumiditySensor();
		RainSensor rSense = new RainSensor();
		TemperatureSensor tSense = new TemperatureSensor();
		WindSensor wSense = new WindSensor(windUpdateInterval, 10);

	}
}
