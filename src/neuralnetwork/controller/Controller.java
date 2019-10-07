package neuralnetwork.controller;

import neuralnetwork.constants.Constants;
import neuralnetwork.model.BaseNeuron;
import neuralnetwork.model.Neuron;

public class Controller {
	private BaseNeuron[][] neuralNetwork;
	public double function(double x) {
		return 1/(1+Math.exp(-x));
	}
	public double diffFunction(double x) {
		double sigmoid = function(x);
		return sigmoid * ( 1 - sigmoid);
	}
	public void calculate() {
		for(int i = 1; i < neuralNetwork.length; i++) {
			for(int j = 0; j < neuralNetwork[i].length; j++) {
				double x = 0;
				Neuron neuron = (Neuron) neuralNetwork[i][j];
				for(int h = 0; h < neuralNetwork[i-1].length; h++) {
					x += neuron.getWeight(h) * neuralNetwork[i-1][h].getX();
				}
				neuron.setX(function(x));
			}
		}
	}
	public void learn(double[] input, double[] output) {
		setInput(input);
		calculate();
		int length = neuralNetwork.length;
		for(int i = length - 1; i > 0; i--) {
			Neuron[] layer = (Neuron[]) neuralNetwork[i];
			BaseNeuron[] prevLayer = neuralNetwork[i-1];
			for(int j = 0; j < layer.length; j++) {
				double x = layer[j].getX();
				double error;
				if(i == length - 1)
					error = x - output[j];
				else {
					error = 0;
					Neuron[] nextLayer = (Neuron[]) neuralNetwork[i+1];
					for(int h = 0; h < nextLayer.length; h++) {
						error += nextLayer[h].getDelta() * nextLayer[h].getWeight(j);
					}
				}
					
				double delta = error * diffFunction(x);
				for(int h = 0; h < prevLayer.length; h++) {
					double weight = layer[j].getWeight(h) - prevLayer[h].getX() * delta * Constants.LEARNING_RATE;
					layer[j].setWeight(weight, h);
				}
				layer[j].setDelta(delta);
				
			}
		}
	}
	public void initNN(int[] layerLength) {
		int layersCount = layerLength.length;
		if(layersCount < 2) {
			return;
		}
		int count = layerLength[0];
		BaseNeuron[] firstLayer = new BaseNeuron[count];
		initLayer(firstLayer);
		addLayer(firstLayer);
		for(int i = 1; i < layersCount; i++) {
			count = layerLength[i];
			BaseNeuron[] layer = new Neuron[count];
			initLayer(neuralNetwork[i-1], layer);
			addLayer(layer);
		}
		
		//randomize weights
		for(int i = 1; i < layersCount; i++) {
			for(int j = 0; j < layerLength[i]; j++) {
				Neuron neuron = (Neuron) neuralNetwork[i][j];
				int prevLength = layerLength[i-1];
				for(int h = 0; h < prevLength; h++) {
					double weight = Math.random() - 0.5;
					neuron.setWeight(weight, h);
				}
			}
		}
	}
	public void addLayer(BaseNeuron[] layer) {
		int count;
		if(neuralNetwork == null)
			count = 0;
		else
			count = neuralNetwork.length;
		BaseNeuron[][] newNeuralNetwork = new BaseNeuron[count+1][];
		for(int i = 0; i < count; i++) {
			newNeuralNetwork[i] = neuralNetwork[i];
		}
		newNeuralNetwork[count] = layer;
		neuralNetwork = newNeuralNetwork;
	}
	public void initLayer(BaseNeuron[] layer) {
		for(int i = 0; i < layer.length; i++) {
			Double x = new Double(0);
			BaseNeuron neuron = new BaseNeuron(x);
			layer[i] = neuron;
		}
	}
	public void initLayer(BaseNeuron[] prevLayer, BaseNeuron[] layer) {
		if(layer instanceof Neuron[]) {
			int prevCount = prevLayer.length;
			for(int i = 0; i < layer.length; i++) {
				Double[] weight = new Double[prevCount];
				Double delta = new Double(0);
				Double x = new Double(0);
				Neuron neuron = new Neuron(x, weight, delta);
				layer[i] = neuron;
			}
		}
		else {
			for(int i = 0; i < layer.length; i++) {
				Double x = new Double(0);
				BaseNeuron neuron = new BaseNeuron(x);
				layer[i] = neuron;
			}
		}
		
	}
	public void setInput(double[] x) {
		BaseNeuron[] layer = neuralNetwork[0];
		if(x.length != layer.length) {
			return;
		}
		for(int i = 0; i < layer.length; i++) {
			layer[i].setX(x[i]);
		}
	}
	public int getInputLayerLength() {
		return neuralNetwork[0].length;
	}
	public int getOutputLayerLength() {
		int n = neuralNetwork.length;
		return neuralNetwork[n-1].length;
	}
	public Double[] getOutput() {
		int layerCount = neuralNetwork.length;
		BaseNeuron[] layer = neuralNetwork[layerCount - 1];
		int count = layer.length;
		Double[] output = new Double[count];
		for(int i = 0; i < count; i++) {
			output[i] = layer[i].getX();
		}
		return output;
	}
}
