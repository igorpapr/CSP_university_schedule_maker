package entity;

public class ScheduleSlot {

	public final ClassTime classTime;

	public final DayOfTheWeek dayOfTheWeek;

	public final Classroom classroom;

	public ScheduleSlot(ClassTime classTime, DayOfTheWeek day, Classroom classroom) {
		this.classTime = classTime;
		this.dayOfTheWeek = day;
		this.classroom = classroom;
	}
}
