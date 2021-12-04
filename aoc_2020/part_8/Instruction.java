package hjul8;

public class Instruction {

	private String todo;
	private int steps;
	
	public Instruction(String toDo, int steps) {
		this.todo = toDo;
		this.steps = steps;
	}
	
	public String getTodo() {
		return this.todo;
	}
	
	public void setTodo(String newToDo) {
		this.todo = newToDo;
	}
	
	public int steps() {
		return this.steps;
	}
	
	@Override
	public String toString() {
		return this.todo + " " + this.steps;
	}
}
