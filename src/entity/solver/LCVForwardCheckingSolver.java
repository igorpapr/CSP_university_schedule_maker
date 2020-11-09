package entity.solver;

import entity.UniversityClass;
import entity.constraints.AbstractConstraint;

import java.util.List;

public class LCVForwardCheckingSolver extends ForwardCheckingScheduleSolver{

	public LCVForwardCheckingSolver(List<UniversityClass> variables, List<AbstractConstraint> constraints) {
		super(variables, constraints);
	}



}
