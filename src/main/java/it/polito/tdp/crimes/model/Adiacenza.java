package it.polito.tdp.crimes.model;

public class Adiacenza {
	public String reato1;
	public String reato2;
	double peso;
	public Adiacenza(String reato1, String reato2, double peso) {
		super();
		this.reato1 = reato1;
		this.reato2 = reato2;
		this.peso = peso;
	}
	@Override
	public String toString() {
		return reato1 +" " + reato2 +" "+ peso;
	}
	
	
}
