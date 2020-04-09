package console;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import controller.Controller;
import controller.Save;

// Display Information from Controller, through serialized connection.
// could include a GUI if we get to that.
public class Console {

	public void serialize() throws Exception {
		FileInputStream fis = new FileInputStream(Controller.f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Save obj1 = (Save) ois.readObject();
		System.out.println("object 1 serialized value is " + obj1.getI());
	}
	
	public String calculateMoonPhase() {
		return "Full Moon";
	}
}
