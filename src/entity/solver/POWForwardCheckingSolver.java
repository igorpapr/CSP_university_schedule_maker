package entity.solver;

import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.Comparator;
import java.util.List;

public class POWForwardCheckingSolver extends ForwardCheckingScheduleSolver{

	public POWForwardCheckingSolver(List<UniversityClass> variables, List<AbstractConstraint> constraints) {
		super(variables, constraints);
	}

	@Override
	protected int findIndexOfNextUnassignedVariableToBeProcessed() {
		return findIndexOfVariableWithTheMostNeighbors();
	}

	/**
	 * Finds index of a variable with unassigned value which has the biggest number of variables with common constraints
	 */
	protected int findIndexOfVariableWithTheMostNeighbors(){
		return this.variables.stream()
				.filter(x -> !x.isAssigned())
				.max(Comparator.comparingInt(o -> o.getNeighbors().size()))
				.map(el -> variables.indexOf(el))
				.orElse(-1);
	}
}
