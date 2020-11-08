import entity.UniversityClass;
import entity.constraints.AbstractConstraint;
import entity.constraints.NotHaveCommonStudentsConstraint;
import entity.constraints.NotSameTeacherConstraint;
import entity.solver.MRVForwardCheckingSolver;
import entity.solver.ScheduleSolver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {



	public static void main(String[] args) {
		List<UniversityClass> variables = new ArrayList<>();
		variables.add();

		List<AbstractConstraint> constraints = new LinkedList<>();
		constraints.add(new NotHaveCommonStudentsConstraint());
		constraints.add(new NotSameTeacherConstraint());

		ScheduleSolver solver = new MRVForwardCheckingSolver(variables, constraints);
		solver.solve();
	}
}
