package neuralnetwork;

import java.io.File;
import java.io.IOException;

import neuralnetwork.constants.Constants;
import neuralnetwork.controller.Controller;
import neuralnetwork.controller.ImageParseController;

public class NeuralNetwork {
	private Controller controller;
	private ImageParseController iController;
	public NeuralNetwork() {
		controller = new Controller();
		iController = new ImageParseController();
	}
	public NeuralNetwork(int[] layers) {
		controller = new Controller();
		iController = new ImageParseController();
		controller.initNN(layers);
	}
	public NeuralNetwork(Controller controller, ImageParseController iController, int[] layers) {
		this.controller = controller;
		this.iController = iController;
		controller.initNN(layers);
	}
	public Double[] calculate(File file) throws IOException {
		double[] input;
		input = iController.getImageValues(file);
		controller.setInput(input);
		controller.calculate();
		Double[] output = controller.getOutput();
		return output;
	}
	public void initLayers(int[] layers) {
		controller.initNN(layers);
	}
	public void learn() {
		System.out.println("Learning started.");
		double[] input;
		double[] output = new double[3];
		for(int i = 0; i < Constants.LEARN_STEPS; i++) {
			if(i % 500 == 0) {
				System.out.println(i + " steps.");
			}
			int random = (int) Math.round(Math.random() * 100);
			Integer result = new Integer(random % 3);
			try {
				Integer filenumber = result + 1;
				input = iController.selectImage(filenumber.toString());
				for(int j = 0; j < 3; j++) {
					if(result == j)
						output[j] = 1;
					else
						output[j] = 0;
				}
				controller.learn(input, output);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Learning finished.");
	}
	public boolean learn(File file, double[] output) throws IOException {
		double[] input = iController.getImageValues(file);
		if(input.length != controller.getInputLayerLength() || output.length != controller.getOutputLayerLength()) {
			return false;
		}
		controller.learn(input, output);
		return true;
	}
}
