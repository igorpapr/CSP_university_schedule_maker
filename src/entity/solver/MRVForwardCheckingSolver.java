package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.*;

//Minimum remaining values solver with forward checking
public class MRVForwardCheckingSolver extends ForwardCheckingScheduleSolver{

	public MRVForwardCheckingSolver(List<UniversityClass> variables, List<AbstractConstraint> constraints) {
		super(variables, constraints);
	}

	@Override
	protected int findIndexOfNextUnassignedVariableToBeProcessed(){
		return findMRVVariableIndex();
	}

	/**
	 * Finds an unassigned variable with minimum remaining values available
	 * @return index of the found variable
	 */
	protected int findMRVVariableIndex(){
		 return this.variables.stream()
				 .filter(x -> !x.isAssigned())
				.min(Comparator.comparingInt(o -> o.getAvailableSlots().size()))
				.map(el -> variables.indexOf(el))
				.orElse(-1);
	}

}
