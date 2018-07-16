package model.resultObject;

public class MaxDistanceResultObject implements ResultObject {

	private String prediction;

	private Double value;

	@Override
	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	@Override
	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public Double getValue() {
		return this.value;
	}

	@Override
	public String getPrediction() {
		return this.prediction;
	}

}
