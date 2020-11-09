import entity.*;
import entity.constraints.*;
import entity.solver.*;

import java.time.LocalTime;
import java.util.*;

public class Main {

	//ClassTimes
	final static List<ClassTime> classTimes = Arrays.asList(
			new ClassTime("1st class", LocalTime.of(8,30),LocalTime.of(9,50)),
			new ClassTime("2nd class", LocalTime.of(10,0), LocalTime.of(11,20)),
			new ClassTime("3rd class", LocalTime.of(11,40), LocalTime.of(13,0)),
			new ClassTime("4th class", LocalTime.of(13,30), LocalTime.of(14,50)),
			new ClassTime("5th class", LocalTime.of(15,0), LocalTime.of(16,20)),
			new ClassTime("6th class", LocalTime.of(16,30), LocalTime.of(17,50))
	);
	//-------------------------------------
	//Classrooms
	final static List<Classroom> classrooms = Arrays.asList(
			new Classroom(5,"1-222"),
			new Classroom(10,"1-111"),
			new Classroom(5,"3-310"),
			new Classroom(10,"1-313"),
			new Classroom(8,"1-331"),
			new Classroom(5,"1-202"),
			new Classroom(6,"1-140"),
			new Classroom(6,"10-5"),
			new Classroom(6,"10-3")
	);

	public static void main(String[] args) {
		List<UniversityClass> variables = new ArrayList<>();

		initVariables(variables);


		List<AbstractConstraint> constraints = new LinkedList<>();
		constraints.add(new NotHaveCommonStudentsConstraint());
		constraints.add(new NotSameTeacherConstraint());

		ScheduleSolver solver = new CPWithForwardCheckingScheduleSolver(variables, constraints);
		List<UniversityClass> res = solver.solve();
		System.out.println("----------------------Algorithm ended successfully!--------------------");
		System.out.println("RESULT: ");
		printResult(res);
	}

	private static void printResult(List<UniversityClass> res){
		HashMap<DayOfTheWeek, LinkedHashMap<ClassTime, UniversityClass>> map = new HashMap<>();
		for(DayOfTheWeek day: DayOfTheWeek.values()){
			map.put(day, new LinkedHashMap<>());
		}
		for (UniversityClass class1: res){
			map.get(class1.getDayOfTheWeek()).put(class1.getClassTime(), class1);
		}
		for (DayOfTheWeek day: DayOfTheWeek.values()){
			System.out.println("=======================");
			System.out.println(day + ": ");
			for (ClassTime time: classTimes){
				if(map.get(day).containsKey(time)){
					System.out.println("-----------------------");
					System.out.println(time.getName() + "[" +time.getStart() + " - " + time.getEnd()+"]:");
					System.out.println(map.get(day).get(time));
				}
			}
			System.out.println("=======================");
		}
	}

	private static void initVariables(List<UniversityClass> variables){
		//-------------------------------------
		//ScheduleSlots
		List<ScheduleSlot> slotsList = new ArrayList<>();
		for (DayOfTheWeek day: DayOfTheWeek.values()){
			for (ClassTime classTime : classTimes){
				for (Classroom classroom : classrooms){
					slotsList.add(new ScheduleSlot(classTime, day, classroom));
				}
			}
		}
		//-------------------------------------
		//Teachers
		Teacher t1 = new Teacher("Andrii Andriyov");
		Teacher t2 = new Teacher("Sergii Sergiiev");
		Teacher t3 = new Teacher("Kobza Kobzar");
		Teacher t4 = new Teacher("Oleg Olegov");
		Teacher t5 = new Teacher("Yurii Yuriyovich");
		Teacher t6 = new Teacher("Slava Ukraini");
		//-------------------------------------
		//Students
		Student st1 = new Student("Petro Petrov");
		Student st2 = new Student("Andrii Andriev");
		Student st3 = new Student("Ihor Igorischevich");
		Student st4 = new Student("Grisha Grigoriev");
		Student st5 = new Student("Vasil Vasiliov");
		Student st6 = new Student("Oleg Olgovich");
		Student st7 = new Student("Olga Olegovna");
		Student st8 = new Student("Petya Silovik");
		Student st9 = new Student("Pudge Myasnik");
		Student st10 = new Student("Svezhee Myaso");
		Student st11 = new Student("Dmytro Dmytrovich");
		Student st12 = new Student("Roman Romanov");
		Student st13 = new Student("Vasiliy Vasylovich");
		Student st14 = new Student("Lover of zarakh");
		Student st15 = new Student("Programmer goda po versii WBO 2020");
		Student st16 = new Student("Vitaliya Vitaliypvich");
		//-------------------------------------
		//Subjects
		Subject s1 = new Subject("English", t1, new Teacher[]{t1,t2}, new Student[] {st1, st2,st3,st4,st5,st6,st7,st8}, 2, 1);
		Subject s2 = new Subject("Programming: C#", t3, new Teacher[]{t3,t4}, new Student[] {st3, st4,st5,st6,st7,st8,st9,st10, st11, st12, st13, st14}, 2, 1);
		Subject s3 = new Subject("Programming: Haskell", t5, new Teacher[]{t5}, new Student[] {st1, st2,st3,st4,st5,st6,st7,st8,st9,st10, st11, st12, st13, st14, st15}, 3, 1);
		Subject s4 = new Subject("Math", t6, new Teacher[]{t6,t1}, new Student[] {st1, st2,st3,st4,st5,st6}, 1, 1);
		Subject s5 = new Subject("Basics of AI", t2, new Teacher[]{t2,t3,t4}, new Student[] {st1, st2,st3,st4,st5,st10,st11,st12,st13,st14,st15,st16}, 3, 1);
		//-------------------------------------
		//UniversityClasses
		UniversityClass uc1 = new UniversityClass(s1, true, s1.getLecturer(), Arrays.asList(s1.getStudents()));
		UniversityClass uc2 = new UniversityClass(s1, false,t1, Arrays.asList(st1, st2, st3, st4));
		UniversityClass uc3 = new UniversityClass(s1, false,t2, Arrays.asList(st5, st6, st7, st8));

		UniversityClass uc4 = new UniversityClass(s2, true, s2.getLecturer(), Arrays.asList(s2.getStudents()));
		UniversityClass uc5 = new UniversityClass(s2, false,t3, Arrays.asList(st3, st4, st5, st6, st7, st8));
		UniversityClass uc6 = new UniversityClass(s2, false,t4, Arrays.asList(st9, st10, st11, st12, st13, st14));

		UniversityClass uc7 = new UniversityClass(s3, true, s3.getLecturer(), Arrays.asList(s3.getStudents()));
		UniversityClass uc8 = new UniversityClass(s3, false,t5, Arrays.asList(st1, st2,st3,st4,st5));
		UniversityClass uc9 = new UniversityClass(s3, false,t5, Arrays.asList(st6,st7,st8,st9,st10));
		UniversityClass uc10 = new UniversityClass(s3, false,t5, Arrays.asList(st11, st12, st13, st14, st15));

		UniversityClass uc11 = new UniversityClass(s4, true, s4.getLecturer(), Arrays.asList(s4.getStudents()));
		UniversityClass uc12 = new UniversityClass(s4, false,t3, Arrays.asList(st1, st2,st3,st4,st5,st6));

		UniversityClass uc13 = new UniversityClass(s5, true, s5.getLecturer(), Arrays.asList(s5.getStudents()));
		UniversityClass uc14 = new UniversityClass(s5, false,t2, Arrays.asList(st1, st2,st3,st4,st5,st6));
		UniversityClass uc15 = new UniversityClass(s5, false,t3, Arrays.asList(st6,st7,st8,st9,st10,st11));
		UniversityClass uc16 = new UniversityClass(s5, false,t4, Arrays.asList(st12,st13,st14,st15,st16));
		//-------------------------------------
		variables.add(uc1);
		variables.add(uc2);
		variables.add(uc3);
		variables.add(uc4);
		variables.add(uc5);
		variables.add(uc6);
		variables.add(uc7);
		variables.add(uc8);
		variables.add(uc9);
		variables.add(uc10);
		variables.add(uc11);
		variables.add(uc12);
		variables.add(uc13);
		variables.add(uc14);
		variables.add(uc15);
		variables.add(uc16);

		initializeAvailableSlots(variables, slotsList);

	}

	private static void initializeAvailableSlots(List<UniversityClass> variables, List<ScheduleSlot> slots){
		for (UniversityClass variable : variables) {
			List<ScheduleSlot> copy = new ArrayList<>(slots);
			variable.setAvailableSlots(copy);
		}
	}
}
