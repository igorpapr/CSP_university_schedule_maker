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
						.filter(v -> !lesson.getUuid().equals(v.getUuid()) &&
								(v.getStudents().stream().anyMatch(lesson.getStudents()::contains)
										|| v.getTeacher().equals(lesson.getTeacher())))
						.collect(Collectors.toList());
				lesson.setNeighbors(neighborsToAdd);
			}
		}
	}

	/**
	 * delete this slot from remaining variables for all the neighbors for each of them
	 **/
	 protected void deleteSimilarSlotsFromAllNeighbors(UniversityClass variable, ScheduleSlot slot){
		variable.getNeighbors()
			.forEach(neighbor -> {
				List<ScheduleSlot> toBeRemoved = neighbor.getAvailableSlots()
						.stream().filter(
								availableSlot -> availableSlot.classTime.equals(slot.classTime)
										&& availableSlot.dayOfTheWeek.equals(slot.dayOfTheWeek))
						.collect(Collectors.toList());
				neighbor.getRemovedByForwardCheckingValuesMap().put(variable.getUuid(), toBeRemoved);
				neighbor.getAvailableSlots().removeAll(toBeRemoved);
			});
	}

	/**
	 * For all neighbors of given variable restores all values
	 * that have been removed with the last filtering of ForwardChecking
	 **/
	 protected void restoreLastRemovedByFCValuesFromAllNeighbors(UniversityClass variable){
		variable.getNeighbors()
			.forEach(neighbor -> {
				List<ScheduleSlot> toBeRestored = neighbor.getRemovedByForwardCheckingValuesMap().get(variable.getUuid());
				if (toBeRestored != null){
					neighbor.getAvailableSlots().addAll(toBeRestored);
					neighbor.getRemovedByForwardCheckingValuesMap().remove(variable.getUuid());
				}else{
					System.err.println("Couldn't restore values from " + neighbor + " by the specified key: " + variable);
				}
			});
	}


}
