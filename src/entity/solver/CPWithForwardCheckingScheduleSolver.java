package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.ArrayList;
import java.util.List;

//CP - ConstraintPropagation
public class CPWithForwardCheckingScheduleSolver extends ForwardCheckingScheduleSolver{

	public CPWithForwardCheckingScheduleSolver(List<UniversityClass> variables, List<AbstractConstraint> constraints) {
		super(variables, constraints);
	}

	@Override
	public List<UniversityClass> solve() {
		doPreprocessing();
		boolean res = backtrack();
		if (!res){
			throw new RuntimeException("Couldn't solve the problem. Try fixing initial variables.");
		}
		return variables;
	}

	/**
	 * Do some preprocessing to clear all incompatible values using Constraint Propagation
	 */
	protected void doPreprocessing(){
		for (UniversityClass variable : variables) {
			for (UniversityClass neighbour : variable.getNeighbors()){
				List<ScheduleSlot> resSlots = new ArrayList<>(variable.getAvailableSlots());
				for (ScheduleSlot currSlot : resSlots) {
					if(!neighbour.checkPresenceOfRestrictingValues(currSlot)){
						variable.getAvailableSlots().remove(currSlot);
					}
				}
				resSlots = new ArrayList<>(neighbour.getAvailableSlots());
				for (ScheduleSlot neighbourSlot : resSlots){
					if(!variable.checkPresenceOfRestrictingValues(neighbourSlot)){
						neighbour.getAvailableSlots().remove(neighbourSlot);
					}
				}
			}
		}
	}
}
