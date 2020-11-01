package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

//Minimum remaining values solver with forward checking
public class MRVForwardCheckingSolver extends ScheduleSolver{

	public MRVForwardCheckingSolver(List<UniversityClass> variables, List<AbstractConstraint> constraints) {
		this.variables = variables;
		this.constraints = constraints;
	}

	@Override
	public void solve() {
		for (int i = 0; i < variables.size(); i++){
			UniversityClass item = variables.get(i);
			assignValue(item);
		}
	}

	protected int findWithMinimumRemainingValues(){
		 return this.variables.stream()
				.min(Comparator.comparingInt(o -> o.getAvailableSlots().size()))
				.map(el -> variables.indexOf(el))
				.orElse(-1);
	}

	@Override
	protected void assignValue(UniversityClass variable) {

	}

	//delete this slot from remaining variables for all the neighbors for each of them
	//TODO DO NOT FORGET TO MANAGE THIS SITUATION if we return to previous values IN THE FUTURE
	private void deleteSlotFromAllNeighbors(ScheduleSlot slot){

	}
}
