package be.betty.gwtp.server.bdd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ActivityState {
	

	public ActivityState () {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	private String name; // pk ?
	private int day;
	private int period;
	private boolean isFixed; //jsai plus pk non plus..
	private boolean isManual; //jsais plus..
    //@ManyToOne private Project_instance ...
	@ManyToOne private ProjectInstance projectInstance;
	
	@ManyToOne
	private Teacher teacher;
	
	@ManyToOne
	private Activity activity;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean isFixed() {
		return isFixed;
	}
	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}
	public boolean isManual() {
		return isManual;
	}
	public void setManual(boolean isManual) {
		this.isManual = isManual;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public ProjectInstance getProjectInstance() {
		return projectInstance;
	}
	public void setProjectInstance(ProjectInstance projectInstance) {
		this.projectInstance = projectInstance;
	}
	
	
	
	

	
	
	

}
