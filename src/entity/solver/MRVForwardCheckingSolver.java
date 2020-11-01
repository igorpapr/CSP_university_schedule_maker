package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;

//Minimum remaining values solver with forward checking
public class MRVForwardCheckingSolver extends ScheduleSolver{

	@Override
	public void solve() {

	}

	@Override
	protected void assignValue(UniversityClass variable) {

	}

	//delete this slot from remaining variables for all the neighbors for each of them
	//TODO DO NOT FORGET TO MANAGE THIS SITUATION if we return to previous values IN THE FUTURE
	private void deleteSlotFromAllNeighbors(ScheduleSlot slot){

	}
}
