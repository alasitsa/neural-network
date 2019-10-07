package neuralnetwork.model;

public class BaseNeuron {
	private Double x;
	public BaseNeuron() {
		
	}
	public BaseNeuron(Double x) {
		this.x = x;
	}
	public BaseNeuron(double x) {
		this.x = new Double(x);
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public void setX(double x) {
		this.x = new Double(x);
	}
	@Override
	public BaseNeuron clone() {
		BaseNeuron neuron = new BaseNeuron();
		neuron.setX(this.x);
		return neuron;
	}
}
