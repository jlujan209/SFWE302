package edu.baylor.cs.csi3471;

import java.util.Objects;

public class ModelSettings {

	public static class MPG {
		private int city;
		private int highway;
		private int comb;
		
		public MPG(int city, int highway, int comb) {
			this.city = city;
			this.highway = highway;
			this.comb = comb;
		}
		
		@Override
		public String toString() {
			return "city: " + city + "/ combined: " + comb + "/ highway: " + highway;
		}
	}

	private MPG mpg;
	private int cylinders;
	private Double displacement;
	private String fuelType;
	private String modelName;
	private String transmission;
	private String VClass;
	private int year;
	private String make;

	public ModelSettings(String[] line) {
		mpg = new MPG(Integer.parseInt(line[0] == ""? "0" : line[0]), Integer.parseInt(line[5]== ""? "0" : line[5]), Integer.parseInt(line[1]== ""? "0" : line[1]));
		cylinders = Integer.parseInt(line[2]== ""? "0" : line[2]);
		displacement = Double.parseDouble(line[3]== ""? "0" : line[3]);
		fuelType = line[4];
		modelName = line[7];
		transmission = line[8];
		VClass = line[9];
		year = Integer.parseInt(line[10]== ""? "0" : line[10]);
		make = line[6];
	}

	public String getMake(){
		return make;
	}

	public void setMake(String make){
		this.make = make;
	}
	
	public MPG getMpg() {
		return mpg;
	}

	public void setMpg(MPG mpg) {
		this.mpg = mpg;
	}
	

	public int getCylinders() {
		return cylinders;
	}


	public void setCylinders(int cylinders) {
		this.cylinders = cylinders;
	}


	public Double getDisplacement() {
		return displacement;
	}


	public void setDisplacement(Double displacement) {
		this.displacement = displacement;
	}


	public String getFuelType() {
		return fuelType;
	}


	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}


	public String getModelName() {
		return modelName;
	}


	public void setModelName(String modelName) {
		this.modelName = modelName;
	}


	public String getTransmission() {
		return transmission;
	}


	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}


	public String getVClass() {
		return VClass;
	}


	public void setVClass(String vClass) {
		VClass = vClass;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	@Override
	public int hashCode() {
		return Objects.hash(VClass, cylinders, displacement, fuelType, modelName, mpg, transmission, year);
	}

	@Override
	public String toString() {
		
		
		return modelName + ", " + year + ", cylinders: " + cylinders + ", displacement: " + displacement + ", " + fuelType + ", " + VClass + ", " +
				transmission + ", MPG: " + mpg.toString();
		}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelSettings other = (ModelSettings) obj;
		return Objects.equals(VClass, other.VClass) && cylinders == other.cylinders
				&& Objects.equals(displacement, other.displacement) && Objects.equals(fuelType, other.fuelType)
				&& Objects.equals(modelName, other.modelName) && Objects.equals(mpg, other.mpg)
				&& Objects.equals(transmission, other.transmission) && year == other.year;
	}


}
