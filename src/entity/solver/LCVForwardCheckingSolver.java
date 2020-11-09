package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.Comparator;
import java.util.List;

public class LCVForwardCheckingSolver extends ForwardCheckingScheduleSolver{

	public LCVForwardCheckingSolver(List<UniversityClass> variables, List<AbstractConstraint> constraints) {
		super(variables, constraints);
	}

	@Override
	protected ScheduleSlot getValueToBeAssigned(UniversityClass variable) {
		return variable.getAvailableSlots().stream()
				.min(Comparator.comparingInt(s -> {
					int counter = 0;
					for (UniversityClass neighbour : variable.getNeighbors()) {
						if (neighbour.getAvailableSlots().contains(s))
							counter++;
					}
					return counter;
				})).orElse(super.getValueToBeAssigned(variable));
	}

}
