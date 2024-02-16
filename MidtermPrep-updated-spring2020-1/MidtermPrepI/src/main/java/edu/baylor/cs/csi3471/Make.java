package edu.baylor.cs.csi3471;

import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

public class Make {
	
	private static int idCounter = 0;
	
	private int id;
	private String makeName;
	private Set<ModelSettings> modelSettingSet;

	public Set<ModelSettings> getModelSettingSet() {
		return modelSettingSet;
	}

	public void setModelSettingSet(Set<ModelSettings> modelSettingSet) {
		this.modelSettingSet = modelSettingSet;
	}
	
	public void addModelSettings(ModelSettings newModel) {
		modelSettingSet.add(newModel);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, makeName, modelSettingSet);
	}

	@Override
	//TODO: Change toString() method to autogenerate in Eclipse for shorter output
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Make: ").append(makeName).append("\n");
		for(ModelSettings settings : modelSettingSet) {
			sb.append(settings.toString()).append("\n");
		}
		return sb.toString();
	}
	
	public String getMakeName() {
		return makeName;
	}

	public Make(String[] line) {
		this.id = idCounter++;
		this.makeName = line[6]; // FIX
		this.modelSettingSet = new HashSet<>();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Make other = (Make) obj;
		return id == other.id && Objects.equals(makeName, other.makeName)
				&& Objects.equals(modelSettingSet, other.modelSettingSet);
	}

	// there are 2 options, do this functionality here(its static),
	// or in your tester.java and call this method from the make object that a 
	// line is. I would suggest number 2. 
	/*
	public Collection<Make> creatorPattern(String[] line, Collection<Make> makes) {
		if (!modelSettingSet.contains(new ModelSettings(line))) {
			// if the make does not exist then create a new one
		} else {
			// if the make does exist, update its modelSettingSet
		}
		return makes;
	}
	*/
}
