package entity;

import java.util.*;

//means university class (a.k.a lesson)
public class UniversityClass {

	private Subject subject;

	private ScheduleSlot scheduleSlot;

	private List<ScheduleSlot> availableSlots;

	private boolean isLection;

	private Teacher teacher;

	private HashSet<Student> students = new HashSet<>();

	//The list of connected univ classes to the current by common teachers or students
	private List<UniversityClass> neighbors;

	public UniversityClass(Subject subject, boolean isLection, Teacher teacher, List<Student> students) {
		this.subject = subject;
		this.isLection = isLection;
		this.teacher = teacher;
		this.students.addAll(students);
		this.neighbors = new LinkedList<>();
		this.availableSlots = new ArrayList<>();
	}

	public List<ScheduleSlot> getAvailableSlots() {
		return availableSlots;
	}

	public void setAvailableSlots(List<ScheduleSlot> availableSlots) {
		this.availableSlots = availableSlots;
	}

	public List<UniversityClass> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<UniversityClass> neighbors) {
		this.neighbors = neighbors;
	}

	public ScheduleSlot getScheduleSlot() {
		return scheduleSlot;
	}

	public void setScheduleSlot(ScheduleSlot scheduleSlot) {
		this.scheduleSlot = scheduleSlot;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public ClassTime getClassTime() {
		if(this.scheduleSlot != null)
			return this.scheduleSlot.classTime;
		return null;
	}

	public DayOfTheWeek getDayOfTheWeek() {
		if (this.scheduleSlot != null)
			return this.scheduleSlot.dayOfTheWeek;
		return null;
	}

	public boolean isLection() {
		return isLection;
	}

	public void setLection(boolean lection) {
		isLection = lection;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Collection<Student> students) {
		this.students.addAll(students);
	}

	public Classroom getClassroom() {
		if(this.scheduleSlot != null){
			return this.scheduleSlot.classroom;
		}
		return null;
	}

	public boolean isAssigned(){
		return this.scheduleSlot != null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UniversityClass that = (UniversityClass) o;
		return isLection == that.isLection &&
				Objects.equals(subject, that.subject) &&
				Objects.equals(scheduleSlot, that.scheduleSlot) &&
				Objects.equals(availableSlots, that.availableSlots) &&
				Objects.equals(teacher, that.teacher) &&
				Objects.equals(students, that.students) &&
				Objects.equals(neighbors, that.neighbors);
	}

	@Override
	public int hashCode() {
		return 31 * Objects.hash(subject, scheduleSlot, availableSlots, isLection, teacher, neighbors, students);
	}

	@Override
	public String toString() {
		String res = "==================\n" +
				"Class[" +
				subject;
				if (this.scheduleSlot != null){

				res += " | ClassTime=" + this.scheduleSlot.classTime;
				res += " | DayOfTheWeek=" + this.scheduleSlot.dayOfTheWeek;
				res += " | Classroom=" + this.scheduleSlot.classroom;
				}else {
					res += " | {The value of time,day and classroom is not assigned yet}";
				}
				res += " | IsLection=" + isLection +
				"\nTeacher=" + teacher +
				"\nStudents: " + students +
				"]\n" +
				"=====================\n";
				return res;
	}
}
