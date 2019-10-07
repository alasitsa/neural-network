package neuralnetwork.model;

public class Neuron extends BaseNeuron {
	private Double[] weight;
	private Double delta;
	public Neuron() {
		super();
	}
	public Neuron(Double x, Double[] weight, Double delta) {
		super(x);
		this.weight = weight;
		this.delta = delta;
	}
	public Neuron(double x, Double[] weight, Double delta) {
		super(x);
		this.weight = weight;
		this.delta = delta;
	}
	public Double getDelta() {
		return delta;
	}
	public Double getWeight(int position) {
		int length = weight.length;
		if(position < 0 || position > length) {
			return null;
		}
		return weight[position];
	}
	public void setDelta(Double delta) {
		this.delta = delta;
	}
	public void setDelta(double delta) {
		this.delta = new Double(delta);
	}
	
	public void setWeight(Double weight, int position) {
		int length = this.weight.length;
		if(position < 0 || position > length) {
			return;
		}
		this.weight[position] = weight;
	}
	public void setWeight(double weight, int position) {
		int length = this.weight.length;
		if(position < 0 || position > length) {
			return;
		}
		this.weight[position] = new Double(weight);
	}
	public void setWeight(Double[] weight) {
		this.weight = weight;
	}
	@Override
	public Neuron clone() {
		Neuron neuron = (Neuron)super.clone();
		neuron.setDelta(this.delta);
		neuron.setWeight(this.weight);
		return neuron;
	}
}
