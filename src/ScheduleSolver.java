import entity.UniversityClass;

import java.util.List;

public abstract class ScheduleSolver {

	protected List<UniversityClass> variables;

	//solve the schedule
	public abstract void solve();

	//heuristics to assign the value to the variable
	protected abstract void abstractValue(UniversityClass variable);




}
