package entity.solver;

import entity.ScheduleSlot;
import entity.UniversityClass;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ForwardCheckingScheduleSolver extends ScheduleSolver {
	/**
	 *  Initializes a neighbors list for every variable with the connected UniversityClasses.
	 *  "Connected" in this context means having common students of teachers.
	 */
	protected void initializeNeighbors(){
		if (variables != null){
			for (UniversityClass lesson : variables){
				//finding those lessons, which have at least one common student
				// or have common teacher with the current one
				List<UniversityClass> neighborsToAdd = variables.stream()
						.filter(v -> !lesson.equals(v) &&
								(v.getStudents().stream().anyMatch(lesson.getStudents()::contains)
										|| v.getTeacher().equals(lesson.getTeacher())))
						.collect(Collectors.toList());
				lesson.setNeighbors(neighborsToAdd);
			}
		}
	}

	//delete this slot from remaining variables for all the neighbors for each of them
	//TODO DO NOT FORGET TO MANAGE THIS SITUATION if we return to previous values IN THE FUTURE
	protected void deleteSlotFromAllNeighbors(UniversityClass variable, ScheduleSlot slot){
		variable.getNeighbors()
				.forEach(neighbor -> {
					List<ScheduleSlot> filtered = neighbor.getAvailableSlots()
							.stream().filter(availableSlot -> !availableSlot.equals(slot)).collect(Collectors.toList());
					neighbor.setAvailableSlots(filtered);
				});
	}
}
